(defproject mmj2e "0.0.0"
  :description "mmj2e"
  :url "https://github.com/mountain/mmj2e"
  :license {:name "GPL-2.0"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.3"]
                 [org.openjdk.nashorn/nashorn-core "15.3"]]
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]
  :resource-paths ["src/main/resources"]

  :test-paths ["src/tests/java/"]
  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :all (constantly true)}
  :compile-path "target/classes"
  :target-path "target/"
  :javac-options ["--release" "11"]
  :omit-source true
  :jvm-opts ["-Xmx1g"]

  :uberjar-name "mmj2e-standalone.jar"

  :aot :all
  :main metamath.mmj2e)
