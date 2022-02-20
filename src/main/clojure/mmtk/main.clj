(ns mmtk.main
    (:require [cli-matic.core :refer [run-cmd]]
              [mmtk.cmd.init :refer [init-workspace]]
              [mmtk.cmd.pa :refer [invoke-pa]]
              [mmtk.cmd.batch :refer [invoke-batch]]
              [mmtk.cmd.mpe :refer [start-mpe stop-mpe update-mpe]]
              [clojure.tools.cli])
    (:gen-class))

(def CONFIGURATION
    {:command     "mmtk"
     :description "A command-line metamath toolkit"
     :version     "0.0.0"
     :opts        []
     :subcommands [{:id          :init
                    :command     "init"
                    :description "initialize a workspace"
                    :examples    ["mmtk init ." "mmtk init dirc" "mmtk init dirc -d http://a.bc.domian/f.mm"]
                    :opts        [{:as      "a database which is a local file or a http url with extension *.mm"
                                   :id      :database
                                   :option  "database"
                                   :short   "d"
                                   :required "DATABASE"
                                   :default "https://github.com/metamath/set.mm/raw/develop/set.mm"
                                   :default-desc "set.mm database"
                                   :type     :string}]
                    :runs        init-workspace}
                   {:id          :pa
                    :command     "pa"
                    :description "invoke the proof assistant"
                    :examples    ["mmtk pa"]
                    :runs        invoke-pa}
                   {:id          :mpe
                    :command     "mpe"
                    :description "mpe related commands"
                    :subcommands [{:command     "start"
                                   :description "start mpe web server"
                                   :examples    ["mmtk mpe start"]
                                   :runs        start-mpe}
                                  {:command     "stop"
                                   :description "stop mpe web server"
                                   :examples    ["mmtk mpe stop"]
                                   :runs        stop-mpe}
                                  {:command     "update"
                                   :description "update mpeuni.zip from metamath site"
                                   :examples    ["mmtk mpe update"]
                                   :runs        update-mpe}
                                  ]}
                   {:id          :batch
                    :command     "batch"
                    :description "batch execution on a params file"
                    :examples    ["mmtk batch params/default.txt"]
                    :args        [{:as      "params file"
                                   :arg     "params"
                                   :default "params/default.txt"
                                   :type    :string}]
                    :runs        invoke-batch}]})

(defn -main [& args]
    (run-cmd args CONFIGURATION))
