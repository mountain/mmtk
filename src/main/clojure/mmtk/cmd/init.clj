(ns mmtk.cmd.init
    (:require [clojure.java.io :as io]
              [clj-yaml.core :as yaml]
              [clj-http.client :as client]
              [hbs.core :as hbs]))

(defn check-url-or-fs
    "checks if it is a well-formed url or a directory on local file system"
    [test]
    (try (io/as-url test)
         :url
         (catch Exception e (try (io/as-file test)
                                 :file
                                 (catch Exception e :error)))))

(defn get-db-file
    "get db file"
    [wsdir test]
    (io/file wsdir "database"
             (let [urlorfile (keyword (check-url-or-fs test))]
                 (if (.equals urlorfile :file)
                     ; when test is :file
                     (.getName (io/as-file test))
                     ; when test is :url
                     ; we use the last part of the url as db file name
                     ; need more check on url format
                     (let [parts (.split (.getPath (io/as-url test)) "/")]
                         (aget parts (dec (alength parts))))))))

(defn url-handler
    "handling url"
    [wsdir test]
    (do (io/copy
            (:body (client/get test {:as :stream}))
            (get-db-file wsdir test))))

(defn file-handler
    "handling file"
    [wsdir test]
    (do (io/copy
            (slurp (io/as-file test))
            (io/file wsdir "database" (.getName (io/file test))))))

(defn err-handler
    "handling error"
    [wsdir test]
    (println (String/format "error when mmtk seek %s" test)))

(def handlers
    {:url url-handler
     :file file-handler
     :error err-handler})

(defn init-workspace
    "initialize a workspace"
    [{:keys [database _arguments]}]
    (.mkdirs (io/file (get _arguments 0) "database"))
    (.mkdirs (io/file (get _arguments 0) "proofs"))
    (.mkdirs (io/file (get _arguments 0) "macros"))
    (.mkdirs (io/file (get _arguments 0) "params"))

    ;generate mmtk.yaml
    (spit (io/file (get _arguments 0) "mmtk.yaml")
          (yaml/generate-string
              {:database database
               :pa {:width 768 :height 432}}))

    ;prepare database
    (let [test database]
        ((get handlers (check-url-or-fs test)) (get _arguments 0) test))

    ;generate params/default.txt
    (spit (io/file (get _arguments 0) "params" "default.txt")
          (hbs/render (slurp (io/resource "default.txt")) {:database (get-db-file "." database)})))

