learnClojure
============

Der Versuch, Alex einen Blick auf das zu geben, was er bisher nie vermisst hat.

### Fragen, die es zu beantworten gilt

1. Warum all die Klammern (und IST das wirklich so ;)?
2. Wie kann ich ohne Klassen meine Anwendung strukturieren?
3. Wie lese ich Clojure Code (Hint: von innen nach außen, nicht links nach rechts)

### Outline

#### Tag 1 - Up and Running

1. Clojure & Leiningen [Setup](chapters/Setup.md)
2. Der Umgang mit der [REPL](chapter/REPL.md)
3. [LightTable](http://www.lighttable.com/) als Editor
4. Instarepl
5. TDD mit [Midje](https://github.com/marick/Midje)
6. TDD Kata: [String Calculator](http://osherove.com/tdd-kata-1/), [Code](https://github.com/nchapon/string-calculator)
7. Property-Based Testing: [test.ckeck](https://github.com/clojure/test.check)

#### Tag 2 - Design Principles

1. Immutability: Structural Tree Sharing
2. Taking Things Apart: IDKAIDWK, Protocols und Deftype, Datomic
3. Homoiconicity: Macros, Pedestal
4. Functional Programming: Higher-Order Functions
5. Lazy Evaluation & Infinite Data Structures

### Protocols und Deftype

- [Artikel: Solving the Expression Problem with Clojure 1.2](http://www.ibm.com/developerworks/library/j-clojure-protocols/)

### Functional Programming

- Clojure => Java

1. Alle Klammern eine Stelle nach rechts verschieben.
2. Den ersten Parameter vor die Funktion Stellen

Beispiel:

```Clojure
(+ 1 2 3)
+ (1 2 3)  ; Alle Klammern 1 Stelle nach rechts
1.+(2 3)   ; 1. Parameter nach vorne
1.+(2, 3)  ; Kommata (wer's braucht)
```


- Java => Clojure

Das Objekt wird als erster Parameter übergeben.
Java's

```Java
System.out.println("Hello World");
```
wird zu

```Clojure
(. System/out (println "Hello, world!"))
```
oder einfach:
```Clojure
(println "Hello, world!")
```

### Higher-Order-Functions

Funktionen höherer Ordnung sind Funktionen, welche selbst Funktionen als Argumente annehmen (und/oder zurück geben) - sie agieren somit "eine Ebene höher als das, 'was gemacht wird'".
Die bekanntesten Beispiele sind HOF, welche es erlauben, "normale" Operation auf Daten leicht auch auf Collections dieser Daten ausführen. Dazu gehören u.A.:

- map: wendet eine Funktion auf jedes Element einer Collection an.

```Clojure
(defn map [f coll]            ; 2 Argumente: die Funktion und die Collection
  (cons (f (first coll))      ; wende f auf das erste Element an
        (map f (rest coll)))) ; dann hänge das Ergebnis des rekursiven Aufrufs an

(defn t2 [x]
  (* 2 x))

(map t2 '(1 2 3)) ;; => (2 4 6)
```

- filter: erwartet ein Prädikat (eine Funktion, die entweder true oder false zurück gibt), sowie eine Collection. Gibt eine neue Collection zurück, welche nur diejenigen Werte enthält, welche das Prädikat erfüllen.

```Clojure
(defn even? [x]     ; ist die Zahl gerade?
  (= 0 (mod x 2)))  ; modulo 2 == 0 ?

(filter even? '(1 2 3 4)) ; => (2 4)
```

- reduce: "reduziert"/aggregiert eine Collection auf einen aggregierten Wert. Erwartet eine Funktion mit zwei Parametern und einem Rückgabewert, einen (optionalen) Initialwert des Aggregators, sowie die Collection. Führt im ersten Schritt die Funktion mit dem Initialwert (wenn nicht vorhanden, ohne Argumente - (+) => 0, (*) => 1, (str) => ""), dann iterativ die Funktion f mit dem Aggregator und dem nächsten Wert der Collection aus.

```Clojure
;; abstrakte Expansion (wirft Fehler im REPL)
(reduce f i '(a b c)) ; => (f c (f b (f a i)))
(reduce f '(a b c)) ; => (f c (f b (f a (f))))

;; Berechnet die Summe der Zahlen 1 bis 4
(reduce + '(1 2 3 4)) ; => 10

;; klappt auch mit anderen Datentypen
(def strlist '("fred" "barney" "fred" "wilma")) ; Liste von Strings

;; - ein minimalistischer "Webcrawler" - zählt, wie oft die einzelnen Strings vorkommen
(defn addtomap [hmap string]                  ; erwartet eine Map sowie einen String
  (assoc hmap string (inc (hmap string 0))))  ; ist bereits ein Eintrag 'string' vorhanden, inkrementieren
                                              ; ansonsten den Eintrag 'string' -> 0 hinzufügen

(reduce addtomap {} strlist)
; => {"wilma" 1, "barney" 2, "fred" 1}
```

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
