(ns mmtk.cmd.init
    (:require [clojure.java.io :as io]
              [clj-yaml.core :as yaml]
              [clj-http.client :as client]
              [hbs.core :as hbs]
              [mmtk.util.setting :refer [update-workspace get-workspace refresh-all]]))

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
    [test]
    (do (io/copy
            (:body (client/get test {:as :stream}))
            (get-db-file (get-workspace) test))))

(defn file-handler
    "handling file"
    [test]
    (do (io/copy
            (slurp (io/as-file test))
            (io/file (get-workspace) "database" (.getName (io/file test))))))

(defn err-handler
    "handling error"
    [test]
    (println (String/format "error when mmtk seek %s" test)))

(def handlers
    {:url url-handler
     :file file-handler
     :error err-handler})

(defn init-workspace
    "initialize a workspace"
    [{:keys [database _arguments]}]

    (update-workspace (get _arguments 0))

    (.mkdirs (io/file (get-workspace) "database"))
    (.mkdirs (io/file (get-workspace) "proofs"))
    (.mkdirs (io/file (get-workspace) "macros"))
    (.mkdirs (io/file (get-workspace) "params"))

    ;generate mmtk.yaml
    (spit (io/file (get-workspace) "mmtk.yaml")
          (yaml/generate-string
              ; since runtime and installation time is different
              ; the ws setting here is '.'
              {:database (str (io/file (get-db-file "." database)))
               :pa {:width 768 :height 432}}))
    (refresh-all)

    ;prepare database
    (let [test database]
        ((get handlers (check-url-or-fs test)) test))

    ;generate params/default.txt
    (spit (io/file (get-workspace) "params" "default.txt")
          (hbs/render (slurp (io/resource "default.txt"))
                      ; since runtime and installation time is different
                      ; the ws setting here is '.'
                      {:database (get-db-file "." database)})))

