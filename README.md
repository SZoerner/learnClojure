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
2. Taking Things Apart: Protocols und Deftype, Datomic
6. Homoiconicity: Macros, Pedestal
7. Functional Programming: Higher-Order Functions

### Setup

1. Leiningen
2. Ein neues Clojure Projekt erzeugen


### Homoiconicity (Code as Data)

Der Code
```Clojure
(+ (* 2 3) (- 3 1)
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
