(ns mmtk.util.system
    (:import [java.util.concurrent CountDownLatch]))

(def countdown (CountDownLatch. 1))

(defn wait []
    (.await countdown))

(defn shutdown! []
    (.countDown countdown))

(def delayed-shutdown (delay (shutdown!) 3000))
