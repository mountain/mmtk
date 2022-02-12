(ns metamath.core
  (:import (mmj.util BatchMMJ2))
  (:gen-class))

(defn -main [& args]
  (let [retcode (.runIt (BatchMMJ2.) args)]
    (System/exit retcode)))