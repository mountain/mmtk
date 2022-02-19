(ns mmtk.cmd.mpe
    (:import [java.util.concurrent CountDownLatch]
             [java.nio.file Files OpenOption StandardOpenOption]
             [java.nio.file FileSystems]
             [java.net URI])
    (:require [clojure.java.io :as io]
              [clojure.java.browse :refer [browse-url]]
              [clj-http.client :as client]
              [mmtk.cmd.init :as init])
    (:use [compojure.route :only [files not-found]]
          [compojure.core :only [defroutes GET]]
          [ring.middleware.content-type :only [wrap-content-type]]
          org.httpkit.server))

(def countdown (CountDownLatch. 1))
(def delayed-countdown (delay (.countDown countdown) 3000))

(defn start-mpe
    "start a web server for mpe"
    [& args]

    ;prepare zipped archival
    (if (not (.exists (io/file "database" "mpeuni.zip")))
        (do (init/url-handler "http://us.metamath.org/downloads/mpeuni.zip")
            (println "... mpeuni.zip downlaoded!"))
        (println "... mpeuni.zip already downlaoded!"))

    (println "... ready for serve mpe server on 7979!")
    (let [cur (System/getProperty "user.dir")
          uri (URI. (format "jar:file:%s/database/mpeuni.zip" (.toString cur)))
          fsys (FileSystems/newFileSystem uri {})]
        ;static http server
        (defroutes all-routes
                   (GET "/terminate" [] @delayed-countdown {:status  200
                                                            :body    "done!"})
                   (GET "/mpeuni/:file" [file]
                       {:status  200
                        :body    (Files/newInputStream (.getPath fsys "mpeuni" (into-array String [file])) (into-array OpenOption [StandardOpenOption/READ]))})
                   (not-found "<p>Page not found.</p>"))
        (run-server (wrap-content-type all-routes) {:port 7979}))
    (println "... mpe server started on 7979!")
    (browse-url "http://localhost:7979/mpeuni/mmset.html")
    (.await countdown))

(defn stop-mpe
    "start a web server for mpe"
    [& args]
    (try
        (client/get "http://localhost:7979/terminate")
        (catch Exception e 0)))

(defn update-mpe
    "update mpeuni.zip from metamath site"
    [& args]
    (init/url-handler "http://us.metamath.org/downloads/mpeuni.zip"))
