(ns mmtk.util.setting
    (:require [clojure.java.io :as io]
              [clj-yaml.core :as yaml]
              [mmtk.util.coll :as coll]))

(def settings
  (if (.exists (io/as-file "mmtk.yaml"))
    (atom (yaml/parse-string (slurp (io/as-file "mmtk.yaml"))))
    (atom {})))

(defn refresh-all []
    (swap! settings coll/deep-merge (yaml/parse-string (slurp (io/as-file "mmtk.yaml")))))

(defn get-val [keys]
    (get-in @settings keys))

(defn update-val [keys val]
    (swap! settings update-in keys (constantly val)))

(defn save-all []
    (spit (io/as-file "mmtk.yaml") (yaml/generate-string @settings)))

(.addShutdownHook (Runtime/getRuntime) (Thread. save-all))



