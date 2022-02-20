(ns mmtk.util.setting
    (:require [clojure.java.io :as io]
              [clj-yaml.core :as yaml]
              [mmtk.util.coll :as coll]))

(def workspace (atom {:workspace "."}))

(defn update-workspace [ws]
    (swap! workspace update-in [:workspace] (constantly ws)))

(defn get-workspace []
    (get @workspace :workspace))

(def settings
  (if (.exists (java.io.File. (str @workspace)  "mmtk.yaml"))
    (atom (yaml/parse-string (slurp (io/as-file "mmtk.yaml"))))
    (atom {:database "https://github.com/metamath/set.mm/raw/develop/set.mm" :pa {:width 1024 :height 768}})))

(defn refresh-all []
    (swap! settings coll/deep-merge (yaml/parse-string (slurp (java.io.File. (get-workspace) "mmtk.yaml")))))

(defn get-val [& keys]
    (get-in @settings keys))

(defn update-val [keys val]
    (swap! settings update-in keys (constantly val)))

(defn save-all []
    (try
        (spit (java.io.File. (get-workspace) "mmtk.yaml") (yaml/generate-string @settings))
        (catch Exception e 0)))

(.addShutdownHook (Runtime/getRuntime) (Thread. save-all))



