# Starting with Clojure

## edn

- A [short introduction into edn](https://github.com/edn-format/edn)

Summary:
1. Blanks and Commas are whitespace and therefore optional.
2. Primitive Elements are: nil (or null or nothing), true/false, Strings, Characters, symbols, keywords (sort of like Java's enum values), integers, floats.
3. Collections: Lists, vectors (arrays), maps, sets
4. Notion of Equality.

## Clojure

Clojure is .. (OMFG) .. edn. Period. You just learned all of Clojure's syntax. Try some forms in the REPL:

```Clojure
user=> 1
1
user=> "one two three"
"one two three"

```
Yeaaaah.. so you got your own digital parrot now. Not that exiting. Let's try something more advanced:

```Clojure
user=> [1 2 3]
[1 2 3]
user=> #{1 2 3}
#{1 2 3}
user=> (1 3 4) ; java.lang.ClassCastException: java.lang.Long cannot be cast to clojure.lang.IFn
```

Wait.. what?? Ok, you got me. Lists are different. What happens here is, that Clojure fails as it *tries to evaluate 1 as a function*. Right, forms in brackets are treated as function calls - specifically: calling the first element as the function, passing it the rest of the elements as arguments.

```Clojure
user=> (+ 1 2)
3
```
If you DO however actually want a list datastructure, you can tell the reader (the Clojure reader program, that is) by ``quoting`` the list using the ``'``:

```Clojure
user=> '(1 2 3)
(1 2 3)
```

Ok, great - so now, in addition to the syntax you just learned 98% of Clojure's ``semantics`` as well.

## Overview

Clojure ist das edn Datenformat. Darüber hinaus ist Clojure:

- ein LISP
- für die JVM
- mit einem Fokus Datenunveränderbarkeit *Data Immutability*

Während der erste Punkt häufig für die größten Einstiegsschwierigkeiten sorgt, ist es der letzte Punkt, welcher die simple, aber mächtige Grundlage der interessantesten Merkmale - wie etwa das Nebenläufigkeitsmodell - liefert.

## Ein Syntax-Vergleich

In C-artigen Sprachen sieht ein Funktionsaufruf meist so aus:

```Java
do_something_with(value1, value2);
```

Der selbe Aufruf sähe in Clojure so aus:

```Clojure
(do-something-with value1 value2)
```

Clojure's Syntax unterscheidet sich also wie folgt:

1. die öffnende Klammer ist auf der linken Seite des Funktionsnamens.
2. es gibt keine Kommata, um Funktionsargumente voneinander abzugrenzen.
3. Via Konvention werden die Wörter des Funktionsnamens durch Bindestriche getrennt.

.. kein wirklicher Beinbruch.

Clojure Code liest man also nicht von *oben nach unten*, sondern von *innen nach außen*.
Ja genau - so wie man mathematische Ausdrücke berechnet:
```
(2 * 3 + 6) / 4
```
Als valider Clojue-Ausdruck:

```Clojure
(/ (+ (* 2 3) 6) 4)
```
Was hier der Unterschied ist: Binäre Operatoren, welche in den meisten Programmiersprachen zwischen ihre Argumente geschrieben werden (Infix-Notation), werden in LISP genau wie andere Funktionen an erste Stelle - sprich vor ihre Argumente - positioniert (Präfix-Notation). Aus ``2 * 3`` wird ``(* 2 3)``.

Und letztendlich ist das auch gar kein schlechter Vergleich: nachdem jahrelang im Matheunterricht nur mit Zahlen jongliert wurde, kommt irgendwann der Zeitpunkt, an dem erste Symbole eingeführt werden: z.B. ***Pi*** als Konstante für die Zahl 3.1415...,

1. Erste Ausdrücke, Präfix-Notation, "Taschenrechner"-Beispiel
2. Datenprimitive: nil, Numbers (Rationals), Symbols, Keywords,
3. Collections: Lists, Vectors, Maps, Sets
4. Funktionen und Variablen: def, defn

## Equality

The only false-y values in Clojure are `nil` and `false`.

## Functions

> stolen from David Nolen's [Cljs Tutorial for Light Table Users](https://github.com/swannodette/lt-cljs-tutorial/blob/master/lt-cljs-tutorial.cljs).

Functions are the essence of any significant Clojure program. They provide the instruction set for *transforming data*.

Here is a simple function that takes two arguments and adds them.

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

- Functions support any number of arguments via rest arguments.

```Clojure
;; any argument after b is stored in the vector d.
(defn foo3 [a b & d]
  [a b d])

(foo3 1 2)
(foo3 1 2 3 4)
```

Finally you can apply functions to a collection. This results in retrieving each element of the collection and providing them as arguments to the function.

```Clojure
(apply + [1 2 3 4 5])
```

## Syntax die 2.

Um aus Clojure "korrespondierenden" Java Code zu bekommen:

1. Alle Klammern eine Stelle nach rechts verschieben - und schon ist aus gruseligem, mit Klammern überhäuftem Gewisch ein simpler Funktionsaufruf geworden.
2. Den ersten Parameter vor die Funktion stellen - oder irgend einen anderen... einfach, damit es ein Objekt gibt, auf dem die Funktion aufgerufen wird.

Beispiel:

```Clojure
(+ 1 2 3)
+ (1 2 3)  ; Alle Klammern 1 Stelle nach rechts
1.+(2 3)   ; 1. Parameter nach vorne
1.+(2, 3)  ; Kommata (wer's braucht)
```

Die andere Seite - Java => Clojure:

- Das Objekt wird als erster Parameter der Funktion "**.**" übergeben.

Java's

```Java
System.out.println("Hello World");
```
wird zu Clojure's

```Clojure
(. System/out (println "Hello, world!"))
```
oder einfach:
```Clojure
(println "Hello, world!")
```

## Metadata

> stolen from: [The Weird and Wonderful Characters of Clojure](http://yobriefca.se/blog/2014/05/19/the-weird-and-wonderful-characters-of-clojure/)

Metadata (data "above of" the actual data) provides extra information and can be used for documentation, compilation warnings, typehints and other features.
In Clojure, metadata is represented as a map of values (with shorthand option) and can be attached to various forms.

```Clojure
(def ^{ :debug true } five 5) ; meta map with single boolean value
```

``^``is the metadata marker, followed by a map containing arbitrary forms. Most notably, metadata is not taken into account when you check forms for **equaliy**.
You can access the metadata by the meta method which should be executed against the declaration itself (rather than the returned value).

```Clojure
(meta #'five)
;; => {:ns #<Namespace user>, :name five, :column 1, :debug true, :line 1, :file "NO_SOURCE_PATH"}
```

``#'`` is the var quote and useful when you want to talk about the reference/declaration instead of the value it represents.

```Clojure
user=> (def nine 9)
#'user/nine
user=> nine
9
user=> #'nine
#'user/nine
```

Another use of ``^`` is for type hints. These are used to tell the compiler what type the value will be and allow it to perform type specific optimisations thus potentially making resultant code a bit faster.

```Clojure
user=> (def ^Integer five 5)
#'user/five
user=> (meta #'five)
{:ns #<Namespace user>, :name five, :column 1, :line 1, :file "NO_SOURCE_PATH", :tag java.lang.Integer}
```
