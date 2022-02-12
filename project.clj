(defproject metamath "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]]
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

  :uberjar-name "metamath-standalone.jar"

  :aot :all
  :main metamath.core)
