learnClojure
============

Der Versuch, Alex einen Blick auf das zu geben, was er bisher nie vermisst hat. Diese Anleitung ist als [Gitbook](http://www.gitbook.io/) geschrieben.

### Fragen, die es zu beantworten gilt

1. Warum all die Klammern (und **ist** das wirklich so ;)?
2. Wie liest man Clojure Code (Hint: von innen nach außen, nicht links nach rechts)
3. Wie kann man ohne Klassen eine Anwendung strukturieren?


### Outline

#### Tag 1 - Up and Running

1. Clojure & Leiningen [Setup](chapters/Setup.md)
2. Der Umgang mit der [REPL](chapter/REPL.md)
3. [LightTable](http://www.lighttable.com/) als Editor, Instarepl
5. TDD mit [Midje](https://github.com/marick/Midje)
6. Property-Based Testing: [test.ckeck](https://github.com/clojure/test.check)
7. TDD Kata: [String Calculator](http://osherove.com/tdd-kata-1/), [Code](https://github.com/nchapon/string-calculator)

#### Tag 2 - Design Principles

1. Data Immutability: Structural Tree Sharing
2. Functional Programming: Higher-Order Functions
3. Taking Things Apart: IDKAIDWK, Protocols und Deftype, Datomic
4. Homoiconicity: Macros, Pedestal
5. Lazy Evaluation & Infinite Data Structures

### Protocols und Deftype

- [Artikel: Solving the Expression Problem with Clojure 1.2](http://www.ibm.com/developerworks/library/j-clojure-protocols/)

### Lazy Evaluation & Infinite Data Structures

In Clojure (sowie Haskell, teilweise Scala u.A.) werden Datenstrukturen standardmäßig erst dann evaluiert, wenn sie tatsächlich benötigt werden.

Neben der reinen Zeitersparnis ist es außerdem möglich, Collections von zunächst unendlicher Größe (Infinite Data Structures) zu erstellen - so lange sie letztlich nicht alle realisiert werden.
Dies erlaubt so genanntes "Stream Processing", bei dem die Menge der verarbeiteten Daten (und somit konkrete technische Details wie Speichergröße) von dem grundlegenden Algorithmus losgekoppelt werden.

Eine Konsequenz der Lazy Evaluation ist, dass das Programm keinerlei Annahmen darüber machen kann, wann zur Laufzeit die Datenstrukturen evaluiert werden. Die verwendeten Funktionen müssen daher seiteneffektfrei sein.

```Clojure
;; evaluieren einer Lazy Sequence
(iterate inc 1) ; => OutOfMemoryError

;; natürliche Zahlen
(def natNums (iterate inc 1)) ; - alle natürlichen Zahlen

(take 5 natNums)              ; - evaluiere die ersten 5
; => (1 2 3 4 5)

;; Fibonacci Sequenz
(defn fib [a b]         ; berechnet die nächste Fibonacci-Zahl
    [b (+' a b)])

(def fibNums
    (map first
        (iterate fib [0 1]))) ; - alle Fibonacci Zahlen

(take 10 fib)
; => (0 1 1 2 3 5 8 13 21 34)
```

### Property-Based Testing

> "Don't write tests. Generate them." - John Hughes

Unit Tests zu schreiben eine mühsame Angelegenheit. Darüber hinaus steigt mit der Verzahnung von Funktionen die Anzahl der möglichen Kombinationen (auch wenn dies streng genommen keine Unit Tests mehr sind). Will man etwa eine Sortier-Funktion validieren, so wäre die klassische Herangehensweise:

1. Erstelle eine Liste - ``(def lst '(1 3 4 2))`` -> der Generator
2. Sortiere die Liste - ``(def sorted (sort lst))`` -> anwenden der Funktion unter Test
3. Vergleiche die Ausgabe mit dem erwarteten Wert - ``(= sorted '(1 2 3 4))`` -> überprüfen der Eigenschaft

Diese allgemeine Vorgehensweise wiederholt man dann mit weiteren, möglichst variablen Eingabe/Erwartungswert-Paaren (hohe Anzahl von Elementen, positive wie negative Zahlen, Dezimalzahlen, ..). Nichtsdestotrotz bleibt die Menge der Beispiele statisch und ist nicht unbedingt dazu geeignet, Randbedingungen zu ertasten.

Beim Property-Based Testing beschreiben Testfälle eine Ebene abstrakter eben genau nur jene generellen funktionalen Eigenschaften (z.B. "jedes Element einer sortierten Liste ist kleiner oder gleich seinen Nachfolgern"). Die Auswahl der Eingabewerte sowie die Berechnung korrespondierender Erwartungswerte werden dann über Generatoren randomisiert erzeugt (für primitive Typen sind bereits Generatoren vorhanden).

```Clojure
(prop/for-all [v (such-that not-empty (gen/vector gen/int))]   ; Generator[Int]
           (= (apply min v)                                    ; Assertion
              (first (sorted v))))
```

Bringt eine Eingabekombination den Testfall zum Scheitern, beginnt das so genannte "Shrinking" - es wird sukkessiv nach simpleren Eingabewerten mit gleichem Resultat gesucht, so dass letztlich der minimale Testfall gefunden werden kann, welcher den Test nicht erfüllt.

Property-Based Testing verlangt eine etwas andere Herangehensweise an das Erstellen von Testfällen. Einige Heuristiken zur Anlage von Properties:

- Roundtrip: Das Resultat entspricht den Eingangsdaten - ``g(f(x)) -> x``.
       - Beispiel 1: Eine zwei Mal umgedrehte Liste entspricht der ursprünglichen Liste - ``reverse(reverse(list)) -> list``.
       - Beispiel 2: Das Sortieren einer bereits sortierten Liste entspricht der ursprünglichen Liste - ``sort(sort(list)) -> sort(list)``.
