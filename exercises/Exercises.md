# Exercises

## Incrementer

**Task**: Write a function that - given a list of numbers - increments each element in the list by one.

### Concepts practised

- Higer-Order Functions
- Anonymous Functions
- Clojure Syntactic Sugar

### Tests

```Clojure
;Test
(add-one '(1 2 3 4)) ; => (2 3 4 5)

; Prop-Test:
; sum(orig) == sum(add-one(orig)) + length(orig)
(prop/for-all
    [list gen/vector gen/int]
    (= (reduce + (add-one list))
       (+ (reduce + list) (count list))))
```

### Solution

```Clojure
(defn add-one [x] (+ 1 x)) ; eigenständige Funktion
(map add-one '(1 2 3 4))   ; => (2 3 4 5)

; anonyme Funktion
(map (fn [x] (+ 1 x)) [1 2 3 4])

; anonyme Funktion, Clojure Syntax
(map #(+ 1 %) '(1 2 3 4))

; ersetzen von (+ 1 x) mit inc
(map #(inc %) '(1 2 3 4))

; bei Funktionen mit einem Argument => inline
(map inc '(1 2 3 4))
```

## Mutants

**Task**: Creating "objects" and manipulating data.

### Concepts practised

- Maps
- Anonymous functions
- infinite lazy sequences
- HOF: filter

### Code

```Clojure
; A person - represented as a map of key-value pairs
{:height 140 :weight 70}

; generate a random person
(defn random-person []
  {:height (rand-int 200) :weight (rand-int 90)})
```
```Clojure
; but there are mutants!
; predicate checking if person is a mutant
(defn mutant? [person]
  (when (:mutant person) person))

; or shorter
(defn mutant? [person]
  (:mutant person))

; no power to discrimination
(defn flip-coin []
  (= 1 (rand-int 2)))

; random-person v2
(defn random-person []
  {:height (rand-int 200) :weight (rand-int 90)
   :mutant (flip-coin)})
```
```Clojure
; generate multiple people
(defn random-people
  "Returns an infinite lazy sequence of maps representing random people. Some may be mutants.
  For example: {:height 168 :weight 77 :mutant true}"
  []
  (lazy-seq
    (cons (random-person) (random-people)))) ; try THAT in Ruby...

; find all the mutants..
; ..creating an abstract class, drawing a UML diagramm... nah, just kidding ;)
(defn find-mutants [people]
  (filter mutant? people))
```

## Apple Pie

**Task**: Make apple pies. But before that, you first have to pick the apples for your pie.

You get a basket full of apples of different colors, all containing stickers.
Some apples are already rotten, some are still good. The red ones are sweet, the green ones are a bit sour.
You therefore make two apple pies, one with red, one with green apples.

Now to pick the apples you have to
1. Remove the stickers
2. Put the bad apples into the trash
3. Sort the remaining apples by putting it into the basket with the corresponding color.

### Data

We will represent our baskets and apples as nested map data structure:
The basket itself will be a map with:
-  a color (String) - to sort the apples by color.
-  a number of apples (Vector).

Each apple will be a map with the following attributes:
- an id (String) - actually only for us to distinguish them in the REPL
- a color (String)
- whether it is good (Boolean)
- whether it has a sticker (Boolean)

```Clojure
(def brown-basket
  {:color "brown"
   :apples
   [{:id "good-red"
     :color "red"
     :good true
     :sticker true}

    {:id "bad-red"
     :color "red"
     :good false
     :sticker true}

    {:id "good-green"
     :color "green"
     :good true
     :sticker true}

    {:id "bad-green"
     :color "green"
     :good false
     :sticker true}

    {:id "good-green2"
     :color "green"
     :good true
     :sticker true}
    ]})

(def green-basket
  {:color "green"
   :apples []})
(def red-basket
  {:color "green"
   :apples []})
```

### Functionality

We now define some functions to accomplish the individual tasks:

- remove-sticker: set the sticker attribute to false

```Clojure
(defn remove-sticker [apple]
  (assoc apple :sticker false))
```

- good?: a predicate checking if an apple is a good one by inspecting the 'good' attribute

```Clojure
(defn good? [apple]
  (apple :good)) maps can act as functions
```

- put-in-basket: update the the asket with the corresponding color

```Clojure
(defn put-in-basket [apple baskets] ; Every input gets passed as a parameter - even baskets.
  (assoc ))
```

Now here comes one slightly odd part: To "put an apple into a basket" you think would mean: Take the basket data structure and reference the apple to it - aka "update the map".
In a functional language however, data structures (by default, to be clear) are not mutated. There are only new data structures being created.

As strange as it may sound: "Drinking coffee" the functional way would not set the state of your coffee cup from "full" to "half-empty",
but leave your cup untouched and instead **create new, half-empty cup**.

So what we are going to do instead is ``partition`` the of apples into separate lists by their ``:color`` attribute.

```Clojure
(partition-by :color apples)
```

### Composition

There are now several ways to execute these functions:
The straightforward one is to use our higher-order functions ``map`` and ``filter``:

```Clojure
(partition-by :color (map remove-sticker (filter good? (brown-basket :apples))))
```
This can also be expressed by using the thread-last macro:

```Clojure
(->>
  (brown-basket :apples)
  (filter good?)
  (map remove-sticker)
  (partition-by :color))
```

It does still do the same, it is only expressed in a different (rather iterative-looking) way - all there is to it is some *syntactic sugar*


What these do however is to iterate over all the apples 3 times:
first to sort out the bad apples, then again to remove the stickers (of the remaining good apples, but still..), and at last once again to sort by color.

If you think about it, this is too much looking at apples. Wouldn't it be nicer (and really: more efficient) this way:

1. Pick an apple and look at it.
2. If it is bad, throw it rightaway in the trash
3. If it is good, remove the sticker and put it in the basket matching the color.

That being said: Instead of streaming the list of apples through the functions,
we would rather like to compose the functions into a "all-at-once" function. Then we could map this composition over the list.

