(ns mmtk.test-init
  (:require [mmtk.main :refer [-main]]
            [clojure.java.io :as io]
            [mmtk.util.fs :refer [rmr]]
            [clojure.test :refer :all]))

(deftest test-init
  (testing "workspace init"
    (let [testfoldr (io/as-file "test-workspace")]
      (if (.exists testfoldr) (rmr testfoldr)))
    (-main "init" "-d" "https://github.com/metamath/set.mm/blob/develop/iset.mm" "test-workspace")
    (is (.exists (io/as-file ["test-workspace"])))
    (is (.exists (io/as-file ["test-workspace" "database"])))
    (is (.exists (io/as-file ["test-workspace" "database" "iset.mm"])))
    (is (.exists (io/as-file ["test-workspace" "macros"])))
    (is (.exists (io/as-file ["test-workspace" "params"])))
    (is (.exists (io/as-file ["test-workspace" "proofs"])))
    (let [testfoldr (io/as-file "test-workspace")]
      (if (.exists testfoldr) (rmr testfoldr)))))
