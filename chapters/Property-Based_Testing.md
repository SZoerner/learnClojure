# Property-Based Testing

> "Don't write tests. Generate them." - John Hughes

## Unit Testing by Examples

Consider this: You want to write function, which given an Integer, returns a list containing the unique prime factors of the input.
Besides the actual implementation, how do you verify your function actuall behaves correctly? Well, you write tests.

```Clojure
(defn prime-factors [n]
; ... implementation
)

; Tests
(= '(2) (prime-factors 2))
(= '(3) (prime-factors 3))
(= '(5) (prime-factors 25)
(= '(11 3) (prime-factors 9801))
...
```
You may already see the pattern: You are testing ``by example``: "*Given this input to my function under test, I expect that result..*"
As you go along and describe more and more examples, this can become quite cumbersome. Moreover,


Unit Tests zu schreiben eine mühsame Angelegenheit. Darüber hinaus steigt mit der Verzahnung von Funktionen die Anzahl der möglichen Kombinationen (auch wenn dies streng genommen keine Unit Tests mehr sind). Will man etwa eine Sortier-Funktion validieren, so wäre die klassische Herangehensweise:

1. Erstelle eine Liste - ``(def lst '(1 3 4 2))`` -> der Generator
2. Sortiere die Liste - ``(def sorted (sort lst))`` -> anwenden der Funktion unter Test
3. Vergleiche die Ausgabe mit dem erwarteten Wert - ``(= sorted '(1 2 3 4))`` -> überprüfen der Eigenschaft

Diese allgemeine Vorgehensweise wiederholt man dann mit weiteren, möglichst variablen Eingabe/Erwartungswert-Paaren (hohe Anzahl von Elementen, positive wie negative Zahlen, Dezimalzahlen, ..). Nichtsdestotrotz bleibt die Menge der Beispiele statisch und ist nicht unbedingt dazu geeignet, Randbedingungen zu ertasten.

## Property-Based Testing

Beim Property-Based Testing beschreiben Testfälle eine Ebene abstrakter eben genau nur jene generellen funktionalen Eigenschaften (z.B. "jedes Element einer sortierten Liste ist kleiner oder gleich seinen Nachfolgern"). Die Auswahl der Eingabewerte sowie die Berechnung korrespondierender Erwartungswerte werden dann über Generatoren randomisiert erzeugt.

Für Clojure existiert die Bibliothekt [test.ckeck](https://github.com/clojure/test.check), welche bereits Generatoren für primitive Typen enthält.

```Clojure
(prop/for-all [v (such-that not-empty (gen/vector gen/int))]   ; Generator[Int]
           (= (apply min v)                                    ; Assertion
              (first (sorted v))))
```

Bringt eine Eingabekombination den Testfall zum Scheitern, beginnt das so genannte "Shrinking" - es wird sukkessiv nach simpleren Eingabewerten mit gleichem Resultat gesucht, so dass letztlich der minimale Testfall gefunden werden kann, welcher den Test nicht erfüllt.

Property-Based Testing verlangt eine etwas andere Herangehensweise an das Erstellen von Testfällen. Hier einige Heuristiken zur Anlage von Properties (aus Reid Draper's talk *[Powerful Testing with test.check](https://www.youtube.com/watch?v=JMhNINPo__g)*) - Roundtrip, Trusted-Implementation und Input/Output relation:

### Roundtrip

Das Resultat entspricht den Eingangsdaten - ``g(f(x)) -> x``. Oft gibt es dabei zwei Funktionen, welche sich konträr zueinander verhalten, z.B. serialisieren/deserialisiern von Daten, partition/flatten, etc.

- Beispiel 1: Eine zwei Mal umgedrehte Liste entspricht der ursprünglichen Liste.

```Clojure
(prop/for-all
    [list gen/vector gen/int]
    (= list
    (reverse(reverse(list)))))
```
- Beispiel 2: Das erneute Sortieren einer bereits sortierten Liste entspricht der sortierten Liste (Funktionen, welche das gleiche Resultat liefern, egal wie oft man sie aufruft, nennt man *idempotent*).

```Clojure
(prop/for-all
    [list gen/vector gen/int]
    (= (sort list)
    (sort (sort list))))
```

### Trusted-Implementation

Existiert bereits eine Funktion, von der man weiß, dass sie das gewünschte Verhalten korrekt implementiert, kann man diese als Referenz benutzen. Will man etwa einen Algorithmus auf Performanz optimieren, so kann man auf den Standard-Algorithmus zurückgreifen, um die Korrektheit des neuen Ansatzes kontinuierlich zu überprüfen.

```Clojure
(prop/for-all
    [a (gen/vector gen/int)]
    (= (map my-prime? a)
       (map #(.isProbablePrime (BigInteger/valueOf %) 5) a) ; Java's prime predicate
```

### Input/Output relation

Beispiel: Konkateniert (verbindet) man zwei Listen, so ist die Länge der konkatenierten Liste gleich der Summe der Längen beider Listen.

```Clojure
(prop/for-all
    [a (gen/vector gen/any)
     b (gen/vector gen/any)]
    (= (count (concat a b))
       (+ (count a) (count b))))
```

