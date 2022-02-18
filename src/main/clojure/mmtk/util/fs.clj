(ns mmtk.util.fs
    (:require [clojure.java.io :as io])
    (:import [java.io File]))

(let [homedir (io/file (System/getProperty "user.home"))
      usersdir (.getParent homedir)]

    (defn home
        "With no arguments, returns the current value of the `user.home` system
         property. If a `user` is passed, returns that user's home directory. It
         is naively assumed to be a directory with the same name as the `user`
         located relative to the parent of the current value of `user.home`."
        ([] homedir)
        ([user] (if (empty? user) homedir (io/file usersdir user)))))

(defn expand-home
    "If `path` begins with a tilde (`~`), expand the tilde to the value
    of the `user.home` system property. If the `path` begins with a
    tilde immediately followed by some characters, they are assumed to
    be a username. This is expanded to the path to that user's home
    directory. This is (naively) assumed to be a directory with the same
    name as the user relative to the parent of the current value of
    `user.home`."
    [path]
    (let [path (str path)]
        (if (.startsWith path "~")
            (let [sep (.indexOf path File/separator)]
                (if (neg? sep)
                    (home (subs path 1))
                    (io/file (home (subs path 1 sep)) (subs path (inc sep)))))
            path)))

