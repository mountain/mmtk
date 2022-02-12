(ns metamath.core
  (:import (mmj.util BatchMMJ2))
  (:gen-class))

(defn -main [& args]
  (.runIt (BatchMMJ2.) (into-array String args)))
