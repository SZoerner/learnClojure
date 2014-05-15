# Test-Driven Development (TDD)

Clojure.test ist das Unit Testing Framework, welches standardmäßig mit Clojure ausgeliefert wird.
Für detaillierte Informationen siehe [clojure.test API](http://richhickey.github.io/clojure/clojure.test-api.html).

## Erste Tests in der REPL

Zuerst muss der clojure.test Namespace mittels ``use`` einbinden:

```Clojure
user=> (use 'clojure.test)
nil
```
Nun kann das Macro ``is`` verwendet werden, welches auf Equality prüft.

```Clojure
user=> (is (= 4 (+ 2 2)))
true
```
Bei Erfolg des Tests ist der Rückgabewert ``true``.
Schlägt der Vergleich fehl, werden zusätzliche Informationen angezeigt.

```Clojure
user=> (is (= 5 (+ 2 2)))

FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
expected: (= 5 (+ 2 2))
  actual: (not (= 5 4))
false
```
Die ``expected:`` Zeile zeigt den originalen Ausdruck, die ``actual:`` Zeile die tatsächliche Ausgabe.

Tests können optional mit dem Makro ``testing`` und einem String als erstes Argument dokumentiert werden:

```Clojure
user=> (testing "Crazy arithmetic" (is (= 5 (+ 2 2))))

FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
Crazy arithmetic
expected: (= 5 (+ 2 2))
  actual: (not (= 5 4))
false
```

Darüber hinaus können Tests mittels ``deftest`` in Gruppen zusammengefasst werden, deren Beschreibungen konkateniert in den Fehlerreports ausgegeben werden.

```Clojure
user=> (deftest test-arithmetic
         (testing "Arithmetic"
           (testing "with positive integers"
             (is (= 4 (+ 2 2)))
             (is (= 7 (+ 3 4))))
           (testing "with negative integers"
             (is (= -4 (+ -2 -3)))
             (is (= -1 (+ 3 -4))))))

user=> (test-arithmetic)

FAIL in (test-arithmetic) (NO_SOURCE_FILE:7)
Arithmetic with negative integers
expected: (= -4 (+ -2 -3))
  actual: (not (= -4 -5))
nil
```
