# Functional Programming

aka. "Programming with functions".

- Immutable Data + First-Class Functions, supporting recursion
- Recursive iteration over side-effecting loops ("computation of values" over "computation of places")
- Dynamic Polymorphism (not covered here... yet)

## Thoughts on Functional Principles

> Don't confuse readable with familiar.

### Simple vs. Easy

[Rich Hickey's Talk on Simple made Easy].

### Scala

hybrid, more OO than Java. The is half a dozen ways to write anything. Which is great when you write it - but painful when you read it.

When you first start writing Scala, you can litterally cut and paste Java into a .scala file, and IntelliJ will do the conversion for you.
So the positive is: You can write Java in Scala and you can feel like you know something.
But the negative is: You're wrong, you don't know something!
Scala does not force you to write functional code, but gives you the opportunity to.

The actor model is what OO was intedend to be back in the days (as opposed to Plymoprphism and Inheritance - those are tools): Independent Entities (with single responsibilities) communicating only via message passing.
*Tell, don't ask!*. Asking is dangerous, asking postpones processing.

In Erlang, processes are that lightweight, that you can store them in cache.

### Property-based Testing

*How do I know my code works?*


One way is via mathematical proof - which is impractical.
Testing on the other hand is the empirical process of assertion by example: Given specific parameters **X** and **Y**, the outcome is expected to be **Z**.

Property-based Testing is the same emprical way to check that your program does as intended - *in all cases*.

*Here is all the possible input for this function* - the Generator, which is randomized.
*After running the function under test, here is a property all outcomes must fulfill* - the property function.

Patterns of how you write properties are the hard part, as they require you to specify the most general set of input data. *Avoid duplicating your code!*

But there are several ways to come up with useful properties.

1. Generate the output you want to receive, calculate the input from that and pass that into the function and see if you got what you originally started with.

## Git as an immutable database

*Your repository is a persistant data structure!*

- Git objects are immutable.
- Out of that you get *content adressable storage* - condensing the *identity* of an object into a small amount of information (the sha-1 hash of the blob contents).
- As the location for storage is derived from the hash, you get automatic reduction of duplication - *you phisically can't store duplicate information*.
- the directed acyclic graph concept is widely applicable. A lot of things we make into a tree - a taxonomy that has branches only going out, but never cross. But the platypus brings together things with furr and things with eggs. Just like tagging is a much more useful way to label files than only having a hierarchical folder structure that can only go in one direction.

### How to introduce FP into Java projects

- use your discipline to make up for what the environment dones not force you to.
- use scala.check for property-based testing.
- avoid mutable local state for referential transparancy
    - Value Objects: Treat your Objects like data
    - Make functions class level (static) and final (immutable): they receive everything that they need as a parameter instead of using the *invisible* parameter of this and unspecified fields on it. Functions instead of methods are specific about what they take in.
    - Event sourcing
- make mutation visible: convention (e.g. in Ruby's) the **!** suffix for functions mutating your data.
- Google's Guava Library: Immutable data structures, HOF for Collection processing (map, reduce, filter).

As mutable state is like poison: *How did my memory get in this particular state? I don't know, anybody could have changed it anywhere!* -it's the modern GOTO.


measuring is the first step of optimization.

### Encapsulation vs. Isolation

Encapsulation (Information *hiding*) aka. *You can't see my insights*: Classes keep their data to themselves.

Isolation (Referential transparancy) aka *I can't see outside myself*: A referentially transparent (or pure) function can not see the world outside of it. Given the same parameters, it always produces the same output, no matter what else is going on in the world - making it isolated and therefore predictable.

## Funktionen

**Aufgabe**: Schreibe eine Funktion, welche alle Elemente einer Liste von Ganzzahlen jeweils um Eins erhöht.  => [Lösung](exercises/Exercises.md)
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
