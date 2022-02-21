(ns mmtk.test-init
  (:require [mmtk.cmd.init :refer [init-workspace]]
            [mmtk.cmd.batch :refer [invoke-batch]]
            [clojure.java.io :as io]
            [mmtk.util.fs :refer [rmr]]
            [mmtk.util.setting :refer [get-workspace update-workspace get-val]]
            [clojure.test :refer :all]))

(deftest test-init
  (testing "workspace init with a file db"
    (let [testfoldr (io/as-file "test-workspace")]
      (if (.exists testfoldr) (rmr testfoldr)))
    (init-workspace {:database "src/tests/resources/test.mm" :_arguments ["test-workspace"]})
    (is (.exists (java.io.File. "test-workspace")))
    (is (.exists (java.io.File. "test-workspace" "mmtk.yaml")))
    (is (.exists (java.io.File. "test-workspace" "database")))
    (is (.exists (java.io.File. "test-workspace" "database/test.mm")))
    (is (.exists (java.io.File. "test-workspace" "macros")))
    (is (.exists (java.io.File. "test-workspace" "params")))
    (is (.exists (java.io.File. "test-workspace" "params/default.txt")))
    (is (.exists (java.io.File. "test-workspace" "proofs")))
    (is (get-val :database) "database/test.mm"))
    (let [testfoldr (io/as-file "test-workspace")]
        (if (.exists testfoldr) (rmr testfoldr)))
  (testing "workspace init with http url"
      (let [testfoldr (io/as-file "test-workspace")]
          (if (.exists testfoldr) (rmr testfoldr)))
      (init-workspace {:database "https://github.com/metamath/set.mm/raw/develop/demo0.mm" :_arguments ["test-workspace"]})
      (is (.exists (java.io.File. "test-workspace")))
      (is (.exists (java.io.File. "test-workspace" "mmtk.yaml")))
      (is (.exists (java.io.File. "test-workspace" "database")))
      (is (.exists (java.io.File. "test-workspace" "database/demo0.mm")))
      (is (.exists (java.io.File. "test-workspace" "macros")))
      (is (.exists (java.io.File. "test-workspace" "params")))
      (is (.exists (java.io.File. "test-workspace" "params/default.txt")))
      (is (.exists (java.io.File. "test-workspace" "proofs")))
      (is (get-val :database) "https://github.com/metamath/set.mm/raw/develop/demo0.mm")
      (let [testfoldr (io/as-file "test-workspace")]
          (if (.exists testfoldr) (rmr testfoldr)))))
