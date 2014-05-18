# Functional Programming

- Immutable Data + First-Class Functions, supporting recursion
- Recursive iteration over side-effecting loops ("computation of values" over "computation of places")
- Dynamic Polymorphism (not covered her... yet)


## Funktionen

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
