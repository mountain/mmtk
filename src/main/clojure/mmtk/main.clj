(ns mmtk.main
    (:require [cli-matic.core :refer [run-cmd]]
              [mmtk.cmd.init :refer [init-workspace]]
              [mmtk.cmd.pa :refer [invoke-pa]]
              [mmtk.cmd.batch :refer [invoke-batch]])
    (:gen-class))

(def CONFIGURATION
    {:command     "mmtk"
     :description "A command-line metamath toolkit"
     :version     "0.0.0"
     :subcommands [{:command     "init"
                    :description "initialize a workspace"
                    :examples    ["mmtk init ." "mmtk init dir"]
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
                    :examples    ["mmtk pa"]
                    :runs        invoke-pa}
                   {:command     "batch"
                    :description "batch execution on a params file"
                    :examples    ["mmtk batch params/default.txt"]
                    :args        [{:as      "params file"
                                   :arg     "params"
                                   :default "params/default.txt"
                                   :type    :string}]
                    :runs        invoke-batch}]})

(defn -main [& args]
    (run-cmd args CONFIGURATION))
