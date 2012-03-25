; --                                                            ; {{{1
;
; File        : test2/core.cljs
; Maintainer  : Felix C. Stegerman <flx@obfusk.net>
; Date        : 2012-03-25
;
; Copyright   : Copyright (C) 2012  Felix C. Stegerman
; Licence     : GPLv2 or EPLv1
;
; Depends     : ...
; Description : ...
;
; TODO        : ...
;
; --                                                            ; }}}1

(ns test2.core
  (:use [ jayq.core         :only [ $         ] ]
        [ obfusk.cljs.trans :only [ do-trans  ] ]) )

; --

(def begin  0)
(def end    3)

(def _st_ (atom { :DEBUG false, :n 0 }))                        ; !!!!

; --

(defn tr-next [{ :keys [ n ] :as st }]                          ; {{{1
  (merge st
    (if (= n end)
      { :n begin  , :counter-text "done."     }
      { :n (inc n), :counter-text (str "#" n) } )))
                                                                ; }}}1

; --

(defn do-update [{ :keys [ counter-text ] }]
  (.text $counter counter-text) )
  ; $counter is defined in do-init


(defn do-next []
  (do-trans _st_ tr-next do-update) )


(defn do-init []                                                ; {{{1
  (def $counter ($ :#counter))
  (def $next    ($ :#next   ))

  (do-next)
  (.click $next (fn [e] (do-next))) )
                                                                ; }}}1

; --

($ do-init)

; --

; vim: set tw=70 sw=2 sts=2 et fdm=marker :
