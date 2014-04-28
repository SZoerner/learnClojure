learnClojure
============

Der Versuch, Alex das zu zeigen, was er bisher nie vermisst hat.

### Fragen, die es zu beantworten gilt

1. Wie kann ich ohne Klassen meine Anwendung strukturieren?
2. Warum all die Klammern (und IST das wirklich so ;)?

### Outline

#### Tag 1

1. Clojure & Leiningen installieren  
2. REPL starten
3. Erste Ausdrücke und Funktionen
4. Datentypen: Lists, Vectors, Maps, Sets
4. Funktionen und Variablen: def, defn
5. [LightTable](http://www.lighttable.com/) als Editor, TDD mit [Midje](https://github.com/marick/Midje)
5. TDD Kata: [String Calculator](http://osherove.com/tdd-kata-1/), [Code](https://github.com/nchapon/string-calculator)

#### Tag 2 - Design Principles

1. Immutability: Structural Tree Sharing
2. Taking Things Apart: Protocols und Deftype, Datomic
6. Homoiconicity: Macros, Pedestal
7. Functional Programming: Higher-Order Functions

### Setup

1. Leiningen
2. Ein neues Clojure Projekt erzeugen


### Homoiconicity (Code as Data)

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

