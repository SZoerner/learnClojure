# Functional Programming

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
