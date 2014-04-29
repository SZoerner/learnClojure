learnClojure
============

Der Versuch, Alex einen Blick auf das zu geben, was er bisher nie vermisst hat.

### Fragen, die es zu beantworten gilt

1. Warum all die Klammern (und IST das wirklich so ;)?
2. Wie kann ich ohne Klassen meine Anwendung strukturieren?
3. Wie lese ich Clojure Code (Hint: von innen nach außen, nicht links nach rechts)

### Outline

#### Tag 1 - Up and Running

1. Clojure & Leiningen installieren  
2. REPL starten
3. Erste Ausdrücke, Präfix-Notation, "Taschenrechner"-Beispiel
4. Datenprimitive: nil, Numbers (Rationals), Symbols, Keywords,
5. Collections: Lists, Vectors, Maps, Sets
6. Funktionen und Variablen: def, defn
7. [LightTable](http://www.lighttable.com/) als Editor
8. TDD mit [Midje](https://github.com/marick/Midje)
9. TDD Kata: [String Calculator](http://osherove.com/tdd-kata-1/), [Code](https://github.com/nchapon/string-calculator)

#### Tag 2 - Design Principles

1. Immutability: Structural Tree Sharing
2. Taking Things Apart: IDKAIDWK, Protocols und Deftype, Datomic
3. Homoiconicity: Macros, Pedestal
4. Functional Programming: Higher-Order Functions
5. Lazy Evaluation & Infinite Data Structures

### Setup

1. Leiningen
2. Ein neues Clojure Projekt erzeugen


### Homoiconicity (Code as Data)

Clojure-Programme sind valide Datenstukturen. Der Ausdruck
```Clojure
(+ (* 2 3) (- 3 1))
```
wird in den abstrakten Syntax-Baum

```
       +
     /   \
    *     -
   / \   / \
  2   3 3   1
```
übersetzt.


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

(map #(* 2 %) '(1 2 3)) ;; => (2 4 6)
```

- filter: erwartet ein Prädikat (eine Funktion, die entweder true oder false zurück gibt), sowie eine Collection. Gibt eine neue Collection zurück, welche nur diejenigen Werte enthält, welche das Prädikat erfüllen.

```Clojure
(defn even? [x]     ; ist die Zahl gerade?
  (= 0 (mod x 2)))  ; modulo 2 == 0 ?

(filter even? '(1 2 3 4)) ; => (2 4)
```

- reduce: "reduziert"/aggregiert eine Collection auf einen aggregierten Wert. Erwartet eine Funktion mit zwei Parametern und einem Rückgabewert, einen (optionalen) Initialwert des Aggregators, sowie die Collection. Führt dann iterativ die Funktion f mit dem Aggregator und dem nächsten Wert der Collection aus.

```Clojure
;; abstrakte Expansion (wirft Fehler im REPL)
(reduce f i '(a b c)) ; => (f c (f b (f a i)))

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