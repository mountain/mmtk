(defproject mmtk "0.0.0"
  :description "metamath toolkit on top of mmj2"
  :url "https://github.com/mountain/mmtk"
  :license {:name "GPL-2.0"}
  :dependencies [[org.openjdk.nashorn/nashorn-core "15.3"]
                 [org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.3"]
                 [cli-matic "0.4.3"]]
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java" "vendors/mmj2/src" "vendors/JSON-java/"]
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
  :main metamath.mmtk)
