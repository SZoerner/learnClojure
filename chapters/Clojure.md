# Einstieg in Clojure

1. [kurze Einführung in edn](https://github.com/edn-format/edn)


Clojure ist:

- ein LISP
- für die JVM
- mit einem Fokus auf Unveränderbarkeit (*Data Immutability*)

Während der erste Punkt häufig für die größten Einstiegsschwierigkeiten sorgt, ist es der letzte Punkt, welcher die simple, aber mächtige Grundlage der interessantesten Merkmale - wie etwa das Nebenläufigkeitsmodell - liefert.

## Ein kurzer Syntax-Vergleich


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
(7 - 4) / (2 + 4) + 5
```

Und letztendlich ist das auch gar kein schlechter Vergleich: nachdem du jahrelang im Matheunterricht nur mit Zahlen jongliert hast, kommt irgendwann der Zeitpunkt, an dem erste Symbole eingeführt werden: z.B. *Pi* als Konstante für die Zahl 3.1415...

1. Erste Ausdrücke, Präfix-Notation, "Taschenrechner"-Beispiel
2. Datenprimitive: nil, Numbers (Rationals), Symbols, Keywords,
3. Collections: Lists, Vectors, Maps, Sets
4. Funktionen und Variablen: def, defn


## Funktionen

- Prädikate (Funktionen, deren einzige Rückgabewerte  **true** und **false** sind) werden als Konvention mit einem nachgestellten ``?`` gekennzeichnet: z.B. ``even?``, ``prime?``, oder ``nil?``.
