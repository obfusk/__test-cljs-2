; --                                                            ; {{{1
;
; File        : obfusk/cljs/trans.cljs
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

(ns obfusk.cljs.trans)

; --

(defn do-trans [st f f-upd]                                     ; {{{1
  (let [ debug (:DEBUG @st) ]
    (when debug
      (.log js/console " <-- " (pr-str @st)) )
    (let [ st+ (swap! st f) ]
      (when debug
        (.log js/console " --> " (pr-str st+)) )
      (f-upd st+) )))
                                                                ; }}}1

; --

; vim: set tw=70 sw=2 sts=2 et fdm=marker :
