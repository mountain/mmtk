(ns mmtk.util.coll)

;; latest comment under above gist provides another
;; simpler and more consistent version of deep-merge
;; Source: https://gist.github.com/danielpcox/c70a8aa2c36766200a95#gistcomment-2759497
(defn deep-merge [a & maps]
    (if (map? a)
        (apply merge-with deep-merge a maps)
        (apply merge-with deep-merge maps)))

