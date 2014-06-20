# Higher Order Functions

Funktionen höherer Ordnung sind Funktionen, welche selbst Funktionen als Argumente annehmen (und/oder zurück geben) - sie agieren somit "eine Ebene höher als das, 'was gemacht wird'".
Die bekanntesten Beispiele sind HOF, welche es erlauben, "normale" Operation auf Daten leicht auch auf Collections dieser Daten ausführen. Dazu gehören u.A.:

- **map**: wendet eine Funktion auf jedes Element einer Collection an.

```Clojure
(defn map [f coll]            ; 2 Argumente: die Funktion und die Collection
  (cons (f (first coll))      ; wende f auf das erste Element an
        (map f (rest coll)))) ; dann hänge das Ergebnis des rekursiven Aufrufs an

(defn t2 [x]
  (* 2 x))

(map t2 '(1 2 3)) ; => (2 4 6)
```

- **filter**: erwartet ein Prädikat (eine Funktion, die entweder true oder false zurück gibt), sowie eine Collection. Gibt eine neue Collection zurück, welche nur diejenigen Werte enthält, welche das Prädikat erfüllen.

```Clojure
(defn even? [x]     ; ist die Zahl gerade?
  (= 0 (mod x 2)))  ; modulo 2 == 0 ?

(filter even? '(1 2 3 4)) ; => (2 4)
```

- **reduce**: "reduziert"/aggregiert eine Collection auf einen aggregierten Wert. Erwartet eine Funktion mit zwei Parametern und einem Rückgabewert, einen (optionalen) Initialwert des Aggregators, sowie die Collection. Führt im ersten Schritt die Funktion mit dem Initialwert (wenn nicht vorhanden, ohne Argumente - (+) => 0, (*) => 1, (str) => ""), dann iterativ die Funktion f mit dem Aggregator und dem nächsten Wert der Collection aus.

```Clojure
; abstrakte Expansion (wirft Fehler im REPL)
(reduce f i '(a b c)) ; => (f c (f b (f a i)))
(reduce f '(a b c)) ; => (f c (f b (f a (f))))

; Berechnet die Summe der Zahlen 1 bis 4
(reduce + '(1 2 3 4)) ; => 10

; klappt auch mit anderen Datentypen
(def strlist '("fred" "barney" "fred" "wilma")) ; Liste von Strings

; - ein minimalistischer "Webcrawler" - zählt, wie oft die einzelnen Strings vorkommen
(defn addtomap [hmap string]                  ; erwartet eine Map sowie einen String
  (assoc hmap string (inc (hmap string 0))))  ; ist bereits ein Eintrag 'string' vorhanden, inkrementieren
                                              ; ansonsten den Eintrag 'string' -> 0 hinzufügen

(reduce addtomap {} strlist)
; => {"wilma" 1, "barney" 2, "fred" 1}
```
