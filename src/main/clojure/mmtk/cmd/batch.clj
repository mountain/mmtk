(ns mmtk.cmd.batch
    (:import [javax.swing JFrame]
             [java.awt.event WindowListener]
             [java.util.concurrent CountDownLatch]
             [mmj.util BatchMMJ2]))

(def countdown (CountDownLatch. 1))

(defn invoke-batch [& args]
    (.runIt (BatchMMJ2.) (into-array String (get (first args)  :_arguments)))
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
