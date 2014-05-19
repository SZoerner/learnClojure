# Functional Programming

aka. "Programming with functions".

- Immutable Data + First-Class Functions, supporting recursion
- Recursive iteration over side-effecting loops ("computation of values" over "computation of places")
- Dynamic Polymorphism (not covered here... yet)


## Funktionen

**Aufgabe**: Schreibe eine Funktion, welche alle Elemente einer Liste von Ganzzahlen jeweils um Eins erhöht.
```Clojure
;;Test
(add-one '(1 2 3 4)) ; => (2 3 4 5)

;; Prop-Test:
;; sum(orig) == sum(add-one(orig)) + length(orig)
(prop/for-all
    [list gen/vector gen/int]
    (= (reduce + (add-one list))
       (+ (reduce + list) (count list))))


```Clojure
(defn add-one [x] (+ 1 x)) ; eigenständige Funktion
(map add-one '(1 2 3 4))   ; => (2 3 4 5)

;; anonyme Funktion
(map (fn [x] (+ 1 x)) [1 2 3 4])

;; anonyme Funktion, Clojure Syntax
(map #(+ 1 %) '(1 2 3 4))

;; ersetzen von (+ 1 x) mit inc
(map #(inc %) '(1 2 3 4))

;; bei Funktionen mit einem Argument => inline
(map inc '(1 2 3 4))
```


- Prädikate (Funktionen, deren einzige Rückgabewerte  **true** und **false** sind) werden als Konvention mit einem nachgestellten ``?`` gekennzeichnet: z.B. ``even?``, ``prime?``, oder ``nil?``.

## Makros

Makros bieten Einstiegspunkte für syntaktische Abstraktionen.

Als Beispiel: das Makro, welches das logische **UND** einer beliebigen Zahl an Ausdrücken berechnet.

```Clojure
(defmacro and
  ([] true)
  ([x] x)
  ([x & rest]
    `(let [and# ~x]
       (if and# (and ~@rest) and#))))
```

Makros greifen, **bevor** der Clojure Reader die Evaluation durchführt.
