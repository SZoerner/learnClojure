# Lazy Evaluation

In Clojure (sowie Haskell, teilweise Scala u.A.) werden Datenstrukturen standardmäßig erst dann evaluiert, wenn sie tatsächlich benötigt werden.

Neben der reinen Zeitersparnis ist es außerdem möglich, Collections von zunächst unendlicher Größe (Infinite Data Structures) zu erstellen - so lange sie letztlich nicht alle realisiert werden.
Dies erlaubt so genanntes "Stream Processing", bei dem die Menge der verarbeiteten Daten (und somit konkrete technische Details wie Speichergröße) von dem grundlegenden Algorithmus losgekoppelt werden.

Eine Konsequenz der Lazy Evaluation ist, dass das Programm keinerlei Annahmen darüber machen kann, wann zur Laufzeit die Datenstrukturen evaluiert werden. Die verwendeten Funktionen müssen daher seiteneffektfrei sein.

```Clojure
;; die Reihe von 0 bis 10
(def r (range 10))
;; => #'user/r

;; Ausgabe als String -> murks :(
(str r)
;; => "clojure.lang.LazySeq@...."

;; gib mir die ersten 4 Elemente
(take 4 r)
;; => (0 1 2 3)

;; gib mir das 4. Element
(nth r 3)
;; => 3

;; gib die gesamte Sequenz aus
(println r)
;; => (0 1 2 3 4 5 6 7 8 9)
```

```Clojure
;; evaluieren einer Lazy Sequence
(iterate inc 1) ; => OutOfMemoryError

;; natürliche Zahlen
(def natNums (iterate inc 1)) ; - alle natürlichen Zahlen

(take 5 natNums)              ; - evaluiere die ersten 5
; => (1 2 3 4 5)

;; Fibonacci Sequenz
(defn fib [a b]         ; berechnet die nächste Fibonacci-Zahl
    [b (+' a b)])

(def fibNums
    (map first
        (iterate fib [0 1]))) ; - alle Fibonacci Zahlen

(take 10 fib)
; => (0 1 1 2 3 5 8 13 21 34)
```

Beispiel Random Ints ([hier](https://www.youtube.com/watch?v=ii-ajztxALM) geklaut):

```Clojure
;; What's "wrong" with this program?
(defn random-ints [limit]             ; limit: highest possible
  (lazy-seq                           ; int to be returned
    (println "realizing lazy number") ; side effect
    (cons (rand-int limit)            ; concatenation
          (random-ints limit))))      ; -> recursive call

;; commands
(def rands (take 10 (random-ints 100)))

(first rands)

(nth rands 3)

(count rands)

(count rands)

rands
```
