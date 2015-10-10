#!/usr/bin/env lein-exec

;;; ===========================================================================
;;; == A gentle introduction into Clojure =====================================
;;' ===========================================================================

; Hello World

"Hello World"


;;; ===========================================================================
;;; == edn ==
;;; ===========================================================================

;; edn is a concise [e]xtensible [d]ata [n]otation
;; think of it (for now!) as 'json on steroids'
;; https://github.com/edn-format/edn

;; It's got basic elements such as

42            ; numbers

"A String"    ; strings

3.14159       ; floating point numbers

17/4          ; Rationals which keep precision


;;; ===========================================================================
;;; == () [] {} #{} ===========================================================
;;; ===========================================================================

;; There are collection types, such as

["the" "vector"]   ; vectors

{:the "map" :b 2}  ; maps

#{1 "the" :set}    ; sets

'(1 2 3)           ; .. and last but not least: lists

;; and they all are all nestable

{:commata, ["they" :are] '(:treated "as") "whitespace"}


;;; ===========================================================================
;;; == Evaluation and Homoiconicity ===========================================
;;; ===========================================================================

;; why '(1 2 3), and not (1 2 3) you ask?


(1 2 3)
;; when you evaluate a list, Clojure treats the first item as a function and calls it

;; a function call with two parameters => (fn arg1 arg2)
(str 2 3)

;; so you have to quote the list
(quote (1 2 3))

;; shorthand: '
;; a list of three elements
'(str 2 3)

(def fun '(str 2 3))

;; treating code as data
(first fun) ;; => str

;; manipulating data
(def more-fun (conj (rest fun) '+))

;; tirning it back into code
(eval more-fun)

;; Note: Usually, eval is rarely used. => (launch the missiles)


;;; ===========================================================================
;;; == Immutability ===========================================================
;;; ===========================================================================

;; All data and collections in Clojure are immutable

(def col {:a "a" :b "b"})

(conj col {:c "c"})

col

;; => like Java Strings

;; why would you want that?
;; Well - did u ever had a race condition on string manipulation?

;; if you do not modify anything, there are no 'read-after-write' problems

;; it also makes reasoning a lot easier



;; Ok, we need to have mutable state SOMEWHERE in the program
;; otherwise it would not do anything useful


;;; == Functional core, imperative shell ======================================

;; We try keep the amount of mutable state as low as the bare minimum


;; ?? this sounds like a performance nightmare!

;; persistent data structures to the rescue!!


;;; ===========================================================================
;;; == Anonymous functions ====================================================
;;; ===========================================================================

((fn [a b] (+ a b)) 3 4)

;;; == Prefix notation ========================================================
(= (+ 3 4) 7 (+ 2 5))

;; Prefix notation allows us to treat 'special operators' like normal functions

;; foo(bar(3,4),5) => (foo (bar 3 4) 5

;; no need for operator precedence defined in the language compiler
(* 4 (+ 4 6 (* 3 2)))

;;; ===========================================================================
;;; == Higher Order Functions =================================================
;;; ===========================================================================


; Note: use Marble diagrams from Odersky's MOOC

;; - predicates: p :: a -> Bool
;; - transformer t :: a -> b
;; - reducing fn r :: a -> b -> a


;; filter:
;; map:
;; reduce:

(reduce #(conj %1 %2) ["D"] ["a"])


;;; ===========================================================================
;;; == Monads =================================================================
;;; ===========================================================================

;; - Applicative
;; - Applicative Functor
;; - Monad


;;; ===========================================================================
;;; == Simplicity =============================================================
;;; ===========================================================================

;; no ambiguity
;; all you need for parsing is
;; block separator     => '(' and ')'
;; and token delimiter => ' '


;; the precedence always is:
;; - the first element is the function
;; - all other elements are the arguments to be applied to the function

;; Example: Dealing Texas Hold'em Cards

;; ranks are represented as numbers from 1 to 13
(def ranks (range 1 14))

;; suits are represented as keywords
(def suits [:spades :hearts :diamonds :clubs])

;; combine each suit with each rank
(defn combine [s] (map vector rank (repeat s)))

;; a deck is the combination of each suit and rank
(def deck
  (mapcat #(map vector ranks (repeat %)) suits))

deck

;; take two cards from the shuffled deck
(take 2 (shuffle deck))


;; or - everything in shorthand:
(->> (mapcat #(map vector (range 13) (repeat %))
             [:spades :hearts :diamonds :clubs])
     shuffle
     (take 2))

;; imagine the corresponding Java Code:
;; 1. Creating Classes for Suit and Rank
;; 2. Defining getters and setters
;; 3. Defining methods!! for combination

;;; Point free notation ;;;

;; some aliases
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

;; Threading macro
(defn bla2 [n]
  (->> n
       (* 4)
       (+ 3)))

(bla2 7)

;; Let macro
(let [test 4]
  test)

((fn [test]
   test)4)

;;; ===========================================================================
;;; == Java Interop ===========================================================
;;; ===========================================================================

;; creating a new object
(def someval (new java.lang.String "Oi!"))

someval

;; method invocation
;; -> the first parameter is the object the method gets called on
(.length someval)

;; Hashmaps as configuration files

; TODO see LightTable user keymaps

;;; ===========================================================================
;;; == Transducers ============================================================
;;; ===========================================================================

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


;;; ===========================================================================
;;; == core.async =============================================================
;;; ===========================================================================


;;; ===========================================================================
;;; == Quil ===================================================================
;;; ===========================================================================

(require '[quil.core :as q]
         '[quil.middleware :as m]) ;; yes, no namespace declaration

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Official demo - https://github.com/quil/quil

(defn setup []
  (q/smooth)                          ;; Turn on anti-aliasing
  (q/frame-rate 1)                    ;; Set framerate to 1 FPS
  (q/background 200))                 ;; Set the background colour to
                                      ;; a nice shade of grey.
(defn draw []
  (q/stroke (q/random 255))           ;; Set the stroke colour to a random grey
  (q/stroke-weight (q/random 10))     ;; Set the stroke thickness randomly
  (q/fill (q/random 255))             ;; Set the fill colour to a random grey

  (let [diam (q/random 100)           ;; Set the diameter to a value between 0 and 100
        x    (q/random (q/width))     ;; Set the x coord randomly within the sketch
        y    (q/random (q/height))]   ;; Set the y coord randomly within the sketch
    (q/ellipse x y diam diam)))       ;; Draw a circle at x y with the correct diameter

(q/defsketch example                  ;; Define a new sketch named example
  :title "Oh so many grey circles"    ;; Set the title of the sketch
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [323 200])                    ;; You struggle to beat the golden ratio

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color (mod (+ (:color state) 0.7) 255)
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      ; Draw the circle.
      (q/ellipse x y 100 100))))

(q/defsketch quil-workflow
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])


;; Tree algorithm
;; http://quil.info/sketches/show/example_tree


;; https://github.com/clojure/core.async/blob/master/examples/walkthrough.clj

;;; ===========================================================================
;;; == HoneySQL ===============================================================
;;; ===========================================================================

;; SQL as Clojure data structures.
;; https://github.com/jkk/honeysql
