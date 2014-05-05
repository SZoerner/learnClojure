# Functional Programming

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
