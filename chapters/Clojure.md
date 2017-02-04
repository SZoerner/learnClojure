# Getting started with Clojure

## edn

* A [short introduction into edn](https://github.com/edn-format/edn): basically read through the whole document.

### Summary:

1. _Blanks_ and _Commata_ are whitespace and therefore optional.  
2. _Primitive elements_ are: `nil` \(or null or nothing\), `true`/`false`, `Strings`, `Characters`, `symbols`, `keywords` \(sort of like Java's enum values\), `integers`, `floats`.  
3. Collections: `lists`, `vectors` \(arrays\), `maps` and `sets`  .
4. The notion of Equality. \(sort of like `deepEquals` all the way down - or or equality on future Java's Value Types\).

## Clojure

In essence: Clojure is .. \(dramatic silence\) .. edn. Period. You just learned all of Clojure's syntax. Try some edn forms in the REPL:

```eval-clojure
1
```

```eval-clojure
"one two three"
```

Yeaaaah.. so you got your own digital parrot now. Not that exiting. Let's try something more advanced - collections:

```eval-clojure
[1 2 3]
```

```eval-clojure
#{1 2 3}
```

```eval-clojure
(1 3 4)
```

Wait.. what?? Ok, you got me. Lists are different than the other collection types. What happens here is that Clojure fails as it _tries to evaluate the first element \(1 in this case\) as a function_. Right, forms in brackets are treated as function calls - specifically: calling the first element as the function, passing it the rest of the elements as arguments.

```eval-clojure
(+ 1 2)
```

If you DO however actually want a list data structure, you can tell the reader \(the Clojure reader program, that is\) by `quoting` the list using a `'` \(called _quote_\):

```eval-clojure
'(1 2 3)
```

Ok, great - so now, in addition to the syntax you just learned 98% of Clojure's _semantics_ as well.

## Overview

More than the edn data format, Clojure is:

* a LISP
* for the JVM \(but also for the web, as [ClojureScript](https://clojurescript.org/ "ClojureScript")\)
* with focus on _data immutability_ \(basically meaning: once a variable has been assigned to a value, it does not change\)

While the first property \(syntax\) seems to be the main entrance barrier, it is the third property that lets Clojure shine. It is after all the foundation for the Clojure concurrency model.

## A syntax comparison

In C-like programming languages a function call mainly looks like this:

```Java
object.doSomethingWith(value1, value2);
```

The same function call in Clojure would be:

```Clojure
(do-something-with object value1 value2)
```

These are the differences:

1. The open parenthesis appears **left** to the name of the called function - `print("hi")` becomes `(print "hi")`.
2. There are no commata separating arguments \(there can, but they are considered whitespace and thus being ignored\), nor semicolons at the end of statements \(`;` in fact, starts a comment line\).
3. Via convention the function names are styled with hyphens instead of CamelCase.

.. not that hard, is it?

---

Let's do a fictional "translation" to turn Clojure into Java code

* Step 1: Move all opening parenthesis one position to the right - now the scary paren hell becomes plain nested function calls.
* Step 2. Place the first parameter in front of the function so you have an object to call the function on.
* Step 3: add commata and semicolon.

Example:

```Clojure
(+ 1 2 3)
+ (1 2 3)  ; all parans to the right
1.+(2 3)   ; 1st parameter in front of the function
1.+(2, 3); ; commata and semicolon
```

Smart as you are you have noticed: calling `+` as a method on the object 1 is not valid Java. 

The other way round - Java =&gt; Clojure:

* Step 1: Pass the object as the first parameter to the function "**.**".

Java's

```Java
System.out.println("Hello World");
```

becomes Clojure's

```Clojure
(. System/out (println "Hello, world!"))
```

or just:

```Clojure
(println "Hello, world!")
```

## Parentheses

What still scares people when first getting in touch with a LISP is the at first glance _huge amount of parantheses_. It is hard to argue with that, as the parenthesis is in essence _all of the syntax_.

You see: Java got its `()`, `{}`, `,` and `;` to mark the structure of code - LISP got: correct.. `()`. In addition, LISP code is quite likely to be more nested than C-like languages with their procedural heritage.  
It is therefore not read from _left to right_, but instead from _the inside out_ - quite like you would read mathematical calculations:

```
(2 * 3 + 6) / 4
```

As a valid Clojure expression:

```Clojure
(/ (+ (* 2 3) 6) 4)
```

The difference is: In C-like languages, binary operators such as `+` \(being called binary as they accept two arguments\) are placed _between_ the function arguments \(called infix-notation\). In LISP however, they are treated just like normal functions and are therefore placed _before_ the arguments \(called prefix-notation\). `2 * 3` becomes `(* 2 3)`. In fact: they are not binary operators -  `(+ 2 3 4 5)` is a valid expression, and as you would expect and returns the sum of all arguments - in this case `14`.

## Equality

The only false-y values in Clojure are `nil` and `false`.

## Functions

> stolen from David Nolen's [Cljs Tutorial for Light Table Users](https://github.com/swannodette/lt-cljs-tutorial/blob/master/lt-cljs-tutorial.cljs).

Functions are the essence of any significant LISP program. They provide the instruction set for _transforming data_.

Here is the definition of a simple function that takes two arguments and returns their sum.

```Clojure
(defn add [a b]
  (+ a b))

(add 1 2)  ; => 3
```

Functions can have multiple arities.

```Clojure
(defn foo2
  ([a b] (+ a b))
  ([a b c] (* a b c)))

(foo2 3 4)
(foo2 3 4 5)
```

Which can be used to supply default values.

```Clojure
(defn defaults
  ([x] (defaults x :default))
  ([x y] [x y]))

(defaults :explicit)
(defaults :explicit1 :explicit2)
```

* Functions support any number of arguments via rest arguments.

```Clojure
; any argument after b is stored in the vector d.
(defn foo3 [a b & d]
  [a b d])

(foo3 1 2)
(foo3 1 2 3 4)
```

Finally you can apply functions to a collection. This results in retrieving each element of the collection and providing them as arguments to the function.

```Clojure
(apply + [1 2 3 4 5]) ; is the same as (+ 1 2 3 4 5)
```

## Metadata

> stolen from: [The Weird and Wonderful Characters of Clojure](http://yobriefca.se/blog/2014/05/19/the-weird-and-wonderful-characters-of-clojure/)

Metadata \(data "above of" the actual data\) provides extra information and can be used for documentation, compilation warnings, typehints and other features.  
In Clojure, metadata is represented as a map of values \(with shorthand option\) and can be attached to various forms.

```Clojure
(def ^{ :debug true } five 5) ; meta map with single boolean value
```

`^`is the metadata marker, followed by a map containing arbitrary forms. Most notably, metadata is not taken into account when you check forms for **equaliy**.  
You can access the metadata by the meta method which should be executed against the declaration itself \(rather than the returned value\).

```Clojure
(meta #'five)
; => {:ns #<Namespace user>, :name five, :column 1, :debug true, :line 1, :file "NO_SOURCE_PATH"}
```

`#'` is the var quote and useful when you want to talk about the reference/declaration instead of the value it represents.

```Clojure
user=> (def nine 9)
#'user/nine
user=> nine
9
user=> #'nine
#'user/nine
```

Another use of `^` is for type hints. These are used to tell the compiler what type the value will be and allow it to perform type specific optimisations thus potentially making resultant code a bit faster.

```Clojure
user=> (def ^Integer five 5)
#'user/five
user=> (meta #'five)
{:ns #<Namespace user>, :name five, :column 1, :line 1, :file "NO_SOURCE_PATH", :tag java.lang.Integer}
```



