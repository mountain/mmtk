(ns mmtk.cmd.pa
    (:import [javax.swing JFrame]
             [java.awt.event WindowListener]
             [java.util.concurrent CountDownLatch]
             [mmj.util BatchMMJ2]))

(def countdown (CountDownLatch. 1))

(defn invoke-pa [& args]
    (.runIt (BatchMMJ2.) (into-array String ["params/default.txt"]))
    (let [frames (JFrame/getFrames)
          frmcnt (alength frames)]
        (if (> frmcnt 0)
            (.addWindowListener (aget frames 0)
                                (proxy [WindowListener] []
                                    (windowOpened [e])
                                    (windowClosing [e] (.countDown countdown))
                                    (windowClosed [e] (.countDown countdown))
                                    (windowIconified [e])
                                    (windowDeiconified [e])
                                    (windowActivated [e])
                                    (windowDeactivated [e])))
            (.countDown countdown)))
    (.await countdown))
