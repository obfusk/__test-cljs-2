(ns test2.core
  (:require
;   [ crate.core    :as crate ]
    [ waltz.state   :as state ] )
  (:use
    [ jayq.core     :only [ $           ] ]
    [ waltz.state   :only [ transition  ] ] )
  (:use-macros
;   [ crate.macros  :only [ defpartial            ] ]
    [ waltz.macros  :only [ in defstate deftrans  ] ] ) )

; --

(def begin  0)
(def end    4)

; --

(def st_m (state/machine :state))
(def st_a (atom nil))  ; !!!!

; --

(defn do-inc-st_a []
  (swap! st_a inc) )

(defn do-set-st_a [n]
  (swap! st_a (constantly n)) )

; --

(defstate st_m :done
  (in []
    (.html $counter "done.") ))

(defstate st_m :counting
  (in [n]
    (.html $counter (str "#" n)) ))

; --

(defn do-trans-counting [n]
  (when (= n begin)
    (state/unset st_m :done) )
  (state/set st_m :counting n)
  (do-inc-st_a) )

(defn do-trans-done []
  (state/unset st_m :counting)
  (state/set st_m :done)
  (do-set-st_a begin) )

; --

(deftrans st_m :begin []
  (do-trans-counting begin) )

(deftrans st_m :next [n]
  (if (= n end)
    (do-trans-done)
    (do-trans-counting n) ))

; --

(defn do-init []
  (def $counter ($ :#counter))  ; !!!!
  (def $next    ($ :#next   ))  ; !!!!

  ; (state/assoc-sm st_m [:debug] false)  ; BUG !!!!

  (transition st_m :begin)
  (.click $next (fn [e] (transition st_m :next @st_a))) )

; --

($ do-init)

; --
