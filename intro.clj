#!/usr/bin/env lein-exec

;;; == A gentle introduction into Clojure =====================================

; Hello World

"Hello World"

;;; == edn ====================================================================

;;; edn is a concise [e]xtensible [d]ata [n]otation
;;; think of it (for now!) as 'json on steroids'
;;; https://github.com/edn-format/edn

;;; It's got basic elements such as


42            ; numbers

"A String"    ; strings

3.14159       ; floating point numbers

17/4          ; Rationals which keep precision


;;; == () [] {} #{} ===========================================================


;;; There are collection types, such as

["the" "vector"]   ; vectors

{:the "map" :b 2}  ; maps

#{1 "the" :set}    ; sets

'(1 2 3)           ; .. and last but not least: lists

;;; and they all are all nestable

{:commata, ["they" :are] '(:treated "as") "whitespace"}


;;; == Evaluation =============================================================

;;; why '(1 2 3), and not (1 2 3) you ask?

(1 2 3)

;;; a function call with two parameters => (fn arg1 arg2)

(str 2 3)

;;; a list of three elements
'(str 2 3)

; (launch the missiles)

;;; == Immutability ===========================================================

;;; All data and collections in Clojure are immutable

(def col {:a "a" :b "b"})

(conj col {:c "c"})

col

; => like Java Strings

; why would you want that?
; Well - did u ever had a race condition on string manipulation?


;;; if you do not modify anything, there are no 'read-after-write' problems

;;; it also makes reasoning a lot easier



;;; Ok, we need to have mutable state SOMEWHERE in the program
;;; otherwise it would not do anything useful


;;; == Functional core, imperative shell ======================================

;;; We try keep the amount of mutable state as low as the bare minimum


;;; ?? this sounds like a performance nightmare!

;;; persistent data structures to the rescue!!



;;; == Anonymous functions ====================================================

((fn [a b] (+ a b)) 3 4)

;;; == Prefix notation ========================================================
(= (+ 3 4) 7 (+ 2 5))

;; Prefix notation allows us to treat 'special operators' like normal functions

; foo(bar(3,4),5) => (foo (bar 3 4) 5

; no need for operator precedence defined in the language compiler
(* 4 (+ 4 6 (* 3 2)))

;;; == Simplicity =============================================================


; no ambiguity
; all you need for parsing is
; block indicator     => '(' and ')'
; and token delimiter => ' '


; the precedence always is:
; - the first element is the function
; - all other elements are the arguments to be applied to the function


;;; Point free notation ;;;

; some aliases
(def & comp)
(def p partial)

(partial + 3)

(def add3 (partial + 3))

(add3 5)


(def square-and-sum
  (comp (p +)
     (p *)))

(square-and-sum 3 4 2)

(def bla
  (& (p + 3)
     (p * 4)))

(bla 7)

;; Threading macro ;;;
(defn bla2 [n]
  (->> n
       (* 4)
       (+ 3)))

(bla2 7)

;;; Let macro ;;;
(let [test 4]
  test)

((fn [test]
   test)4)

;;; == Java Interop ===========================================================

;;; creating a new object
(def someval (new java.lang.String "Oi!"))

someval

;;; method invocation
;; -> the first parameter is the object the method gets called on
(.length someval)

;;; Hashmaps as configuration files

; TODO see LightTable user keymaps

;;; == Transducer =============================================================

;;      +
;;     / \
;;    1   +
;;       / \
;;      2   +
;;         / \
;;        3   4



;; TODO Conway's Game of Life
;; https://www.reddit.com/r/Clojure/comments/3f2yat/game_of_life/
;; http://clj-me.cgrand.net/2011/08/19/conways-game-of-life/
;; http://programmablelife.blogspot.de/2012/08/conways-game-of-life-in-clojure.html


;;; == core.async =============================================================


;; https://github.com/clojure/core.async/blob/master/examples/walkthrough.clj
