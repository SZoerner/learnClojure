# Clojure

Clojure ist:

- ein LISP
- für die JVM
- mit einem Fokus auf Unveränderbarkeit (*Data Immutability*)

Während der erste Punkt häufig für die größten Einstiegsschwierigkeiten sorgt, ist es der letzte Punkt, welcher die simple, aber mächtige Grundlage der interessantesten Merkmale - wie etwa das Nebenläufigkeitsmodell - liefert.

Zunächst einmal: LISP Code liest man nicht von *links nach rechts*, sondern von *innen nach außen*.
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
