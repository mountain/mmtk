(ns mmtk.cmd.pa
    (:import [javax.swing JFrame]
             [java.awt.event ComponentListener WindowListener]
             [mmj.util BatchMMJ2])
    (:require [mmtk.util.setting :as setting]
              [mmtk.util.system :as system]))

(defn invoke-pa [& args]
    (.runIt (BatchMMJ2.) (into-array String ["params/default.txt"]))
    (let [frames (JFrame/getFrames)
          frmcnt (alength frames)]
        (if (> frmcnt 0)
            (let [frm (aget frames 0)
                  width (setting/get-val :pa :width)
                  height (setting/get-val :pa :height)]
                (.setSize frm width height)
                (.setLocationRelativeTo frm nil)
                (.addComponentListener frm
                    (proxy [ComponentListener] []
                        (componentResized [e]
                            (setting/update-val [:pa :width] (.getWidth frm))
                            (setting/update-val [:pa :height] (.getHeight frm)))
                        (componentMoved [e])
                        (componentShown [e])
                        (componentHidden [e])))
                (.addWindowListener frm
                    (proxy [WindowListener] []
                        (windowOpened [e])
                        (windowClosing [e])
                        (windowClosed [e]
                            (system/shutdown!))
                        (windowIconified [e])
                        (windowDeiconified [e])
                        (windowActivated [e])
                        (windowDeactivated [e]))))))
    (system/wait))
