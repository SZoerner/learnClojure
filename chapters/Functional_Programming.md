# Functional Programming

aka. "Programming with functions".

- Immutable Data + First-Class Functions, supporting recursion
- Recursive iteration over side-effecting loops ("computation of values" over "computation of places")
- Dynamic Polymorphism (not covered here... yet)


## Funktionen

**Aufgabe**: Schreibe eine Funktion, welche alle Elemente einer Liste von Ganzzahlen jeweils um Eins erhöht.  => [Lösung](exercises/Exercises.md)
```Clojure
;;Test
(add-one '(1 2 3 4)) ; => (2 3 4 5)

;; Prop-Test:
;; sum(orig) == sum(add-one(orig)) + length(orig)
(prop/for-all
    [list gen/vector gen/int]
    (= (reduce + (add-one list))
       (+ (reduce + list) (count list))))
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
