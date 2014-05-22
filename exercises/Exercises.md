# Exercises

## Incrementer

**Task**: Write a function that - given a list of numbers - increments each element in the list by one.

### Concepts practised

- Higer-Order Functions
- Anonymous Functions
- Clojure Syntactic Sugar

### Tests

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

### Solution

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

## Mutants

**Task**: Creating "objects" and manipulating data.

### Concepts practised

- Maps
- Anonymous functions
- infinite lazy sequences
- HOF: filter

## Code

```Clojure
;; A person - represented as a map of key-value pairs
{:height 140 :weight 70}

;; generate a random person
(defn random-person []
  {:height (rand-int 200) :weight (rand-int 90)})
```
```Clojure
;; but there are mutants!
;; predicate checking if person is a mutant
(defn mutant? [person]
  (when (:mutant person) person))

;; or shorter
(defn mutant? [person]
  (:mutant person))

;; no power to discrimination
(defn flip-coin []
  (= 1 (rand-int 2)))

;; random-person v2
(defn random-person []
  {:height (rand-int 200) :weight (rand-int 90)
   :mutant (flip-coin)})
```
```Clojure
;; generate multiple people
(defn random-people
  "Returns an infinite lazy sequence of maps representing random people. Some may be mutants.
  For example: {:height 168 :weight 77 :mutant true}"
  []
  (lazy-seq
    (cons (random-person) (random-people)))) ; try THAT in Ruby...

;; find all the mutants..
;; ..creating an abstract class, drawing a UML diagramm... nah, just kidding ;)
(defn find-mutants [people]
  (filter mutant? people))
```
