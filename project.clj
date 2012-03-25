(defproject cljs-test-2 "1.0.0-SNAPSHOT"
  :description        "..."
  :source-path        "src-clj"
  :dependencies       [ [ org.clojure/clojure "1.3.0"           ]
                        [ jayq                "0.1.0-alpha3"    ] ]
  :plugins            [ [ lein-cljsbuild      "0.1.3"           ] ]
  :cljsbuild { :builds [{
    :source-path      "src-cljs"
    :compiler {
      :output-to      "www/js/test2.js"
      :optimizations  :whitespace                               ; TODO
      :pretty-print   true
  } }] } )
