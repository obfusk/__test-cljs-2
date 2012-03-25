(ns test1.core
  (:require
;   [ crate.core    :as crate ]
    [ waltz.state   :as state ] )
  (:use
    [ jayq.core     :only [ $ inner     ] ]
    [ waltz.state   :only [ transition  ] ] )
  (:use-macros
;   [ crate.macros  :only [ defpartial                ] ]
    [ waltz.macros  :only [ in out defstate deftrans  ] ] ) )

; --

(def begin  0)
(def end    4)

; --

(def st_m (state/machine :state))
(def st_a (atom nil))  ; !!!!

; --

(defstate st_m :done
  (in []
    (inner $counter "done.") ))

(defstate st_m :counting
  (in [n]
    (inner $counter (str n)) ))

; --

(defn do-inc-value []
  (swap! st_a inc) )

(defn do-set-value [n]
  (swap! st_a (constantly n)) )

; --

(defn do-trans-counting [n]
  (when (= n begin)
    (state/unset st_m :done) )
  (state/set st_m :counting n)
  (do-inc-value) )

(defn do-trans-done []
  (state/unset st_m :counting)
  (state/set st_m :done)
  (do-set-value begin) )

; --

(deftrans st_m :begin []
  (do-trans-counting begin) )

(deftrans st_m :next [n]
  (if (= n end)
    (do-trans-done)
    (do-trans-counting) ))

; --

(defn do-init []
  (def $counter ($ :#counter))  ; !!!!
  (def $next    ($ :#next   ))  ; !!!!

  (transition st_m :begin)
  (.click $next (fn [e] (transition st_m :next @st_a))) )

; --

($ do-init)

; --
