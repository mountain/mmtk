(ns metamath.mmtk
    (:require [clojure.java.io :as io]
              [cli-matic.core :refer [run-cmd]])
    (:import [mmj.util BatchMMJ2]
             [java.io File])
    (:gen-class))

(defn init-workspace []
    (.mkdirs "database")
    (.mkdirs "proofs")
    (.mkdirs "macros")
    (.mkdirs "params")
    ;prepare database
    ;generate params/default.txt
    )

(defn invoke-pa []
    (.runIt (BatchMMJ2.) (into-array String ["params/default.txt"])))

(def CONFIGURATION
    {:command     "mmtk"
     :description "A command-line metamath toolkit"
     :version     "0.0.0"
     :opts        [{:as      "The number base for output"
                    :default 10
                    :option  "base"
                    :type    :int}]
     :subcommands [{:command     "init"
                    :description "initialize a workspace"
                    :examples    ["First example" "Second example"]
                    :opts        [{:as      "the reference database which is a local directory or a http url"
                                   :option  "database" :short-opt "d"
                                   :default "https://github.com/metamath/set.mm/raw/develop/set.mm"
                                   :type    :string}]
                    :args        [{:as      "workspace directory"
                                   :arg     "wsdir"
                                   :default "."
                                   :type    :string}]
                    :runs        init-workspace}
                   {:command     "pa"
                    :description "invoke the proof assistant"
                    :examples    ["First example" "Second example"]
                    :runs        invoke-pa}
                   {:command     "verify"
                    :description "verify a proof"
                    :examples    ["First example" "Second example"]
                    :runs        invoke-pa}]})


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

(defn -main [& args]
    (run-cmd args CONFIGURATION))
