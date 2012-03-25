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

(def begin  0 )
(def end    10)

(def sm (state/machine :state))

(def $counter ($ :#counter))
(def $next    ($ :#next   ))

; --

(defstate sm :counting
  (in [n]
    (inner $counter (str n)) ))

(defstate sm :done
  (in []
    (inner $counter "done.") ))

; --

(defn do-set-next [n]
  (.click $next #( transition sm :next n )) )

; --

(defn do-trans-counting [n]
  (state/set sm :counting n)
  (do-set-next (inc n)) )

(defn do-trans-done []
  (state/set sm :done)
  (do-set-next begin) )

; --

(deftrans sm :begin
  (do-trans-counting begin) )

(deftrans sm :next [n]
  (if (= n end)
    (do-trans-done)
    (do-trans-counting n) ))

; --

(transition sm :begin)

; --
