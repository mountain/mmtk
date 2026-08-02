(defproject mmtk "0.0.0"
  :description "metamath toolkit on top of mmj2"
  :url "https://github.com/mountain/mmtk"
  :license {:name "GPL-2.0"}
  :dependencies [[org.openjdk.nashorn/nashorn-core "15.7"]
                 [org.clojure/clojure "1.12.5"]
                 [org.json/json "20260719"]
                 [clj-commons/clj-yaml "1.0.29"]
                 [cli-matic "0.5.4"]
                 [clj-http "3.13.1"]
                 [hbs "1.1.0"]
                 [http-kit "2.8.1"]
                 [compojure "1.7.2"]
                 [ring/ring-core "1.15.5"]
                 [org.clojure/tools.logging "1.3.1"]
                 [org.slf4j/slf4j-nop "2.0.18"]]
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java" "vendors/mmj2/src/main/java"]
  :resource-paths ["src/main/resources"]

  :test-paths ["src/tests/java/" "src/tests/clojure/"]
  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :all (constantly true)}

  :compile-path "target/classes"
  :target-path "target/"
  :javac-options ["--release" "11"]
  :omit-source true
  :jvm-opts ["-Xmx1g"]

  :uberjar-name "mmtk.jar"

  :aot :all
  :main mmtk.main)
