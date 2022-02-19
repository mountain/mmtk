(ns mmtk.cmd.mpe
    (:import [java.util.concurrent CountDownLatch]
             [java.nio.file FileSystems]
             [java.net URI])
    (:require [ mmtk.cmd.init :as init])
    (:use [compojure.route :only [files not-found]]
          [compojure.core :only [defroutes]]
          org.httpkit.server))

(def countdown (CountDownLatch. 1))

(defn start-mpe
    "start a web server for mpe"
    [& args]

    ;prepare zipped archival
    (init/url-handler "http://us.metamath.org/downloads/mpeuni.zip")
    (println "data downlaoded!")
    (let [cur (System/getProperty "user.dir")
          uri (format "jar:file:%s/database/mpeuni.zip" (.toString cur))
          fsys (FileSystems/newFileSystem uri {})
          root (.getPath fsys "/" (into-array String []))]
        ;static http server
        (defroutes all-routes
                   (files "/" {:root (.toString root)}) ; static file url prefix /static, in `public` folder
                   (not-found "<p>Page not found.</p>")) ; all other, return 404
        (run-server all-routes {:port 8080}))
    (.await countdown))
