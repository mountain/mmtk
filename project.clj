(defproject nmmj2 "0.0.0"
  :description "new mmj2 for metamath"
  :url "http://example.com/FIXME"
  :license {:name "GPL-2.0 WITH Classpath-exception-2.0"}
  :dependencies [[org.clojure/clojure "1.10.3"]
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
  :javac-options ["--release" "15"]
  :omit-source true
  :jvm-opts ["-Xmx1g"]

  :uberjar-name "nmmj2.jar"

  :aot :all
  :main metamath.core)
