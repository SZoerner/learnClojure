# Einstieg in Clojure

## edn

- Eine [kurze Einführung in edn](https://github.com/edn-format/edn)

Zusammenfassung:
1. Leerzeichen und Kommata sind "Whitespace" und somit optional.
2. Primitive Elemente: nil (oder null oder nothing), true/false, Strings, Characters, Symbole, Keywords (Enum Values), Integers, Floats.
3. Collections: Listen, Vektoren (Arrays), Maps, Sets
4. Notion of Equality.

## Clojure

Clojure ist .. (OMFG) .. edn. Ende. Du hast soeben sämtliche Syntax gelernt.

Darüber hinaus ist Clojure:

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
