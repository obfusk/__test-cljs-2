(defproject cljs-test-1 "1.0.0-SNAPSHOT"
  :description        "..."
  :source-path        "src-clj"
  :dependencies       [[ org.clojure/clojure  "1.3.0" ]]
  :plugins            [[ lein-cljsbuild       "0.1.3" ]]
  :cljsbuild { :builds [{
    :source-path      "src-cljs"
    :compiler {
      :output-to      "js/test1.js"
      :optimizations  :simple     ; TODO
      :pretty-print   false
  } }] } )
