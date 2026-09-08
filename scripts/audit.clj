(require '[clojure.edn :as edn]
         '[clojure.java.io :as io]
         '[kotoba.lang.text :as str])

(def root (.getCanonicalFile (io/file ".")))
(def tracked (->> (file-seq root)
                  (remove #(.isDirectory %))
                  (remove #(str/includes? (.getPath %) "/.git/"))))
(def rel #(.toString (.relativize (.toPath root) (.toPath %))))

(doseq [f (filter #(str/ends-with? (.getName %) ".edn") tracked)]
  (try (edn/read-string (slurp f))
       (catch Exception e
         (throw (ex-info "invalid canonical EDN" {:file (rel f)} e)))))

(let [forbidden (filter #(re-find #"(?:^|/)(?:go\.mod|go\.sum|run_tests\.sh|[^/]+\.(?:go|py))$" (rel %)) tracked)
      misplaced (filter #(and (re-find #"\.(?:json|jsonld|jsonl|wit)$" (rel %))
                              (not (str/starts-with? (rel %) "wire/"))
                              (not= (rel %) ".well-known/did.json")) tracked)]
  (when (seq forbidden)
    (throw (ex-info "deprecated implementation artifacts remain" {:files (mapv rel forbidden)})))
  (when (seq misplaced)
    (throw (ex-info "wire artifacts must live under wire/" {:files (mapv rel misplaced)}))))

(println "audit: ok")
