# Property-Based Testing

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

