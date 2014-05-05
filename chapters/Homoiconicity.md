# Homoiconicity

Homoikonizität (oder oder *Selbst-Repräsentierbarkeit*) bezeichnet die Eigenschaft einiger Programmiersprachen, dass Programme gleichzeitig Datenstrukturen derselben Sprache sind.

Jedes Clojure-Programm ist eine valide Datenstuktur. Der Ausdruck
```Clojure
(+ (* 2 3) (- 3 1))
```
entspricht dem abstrakten Syntax-Baum:

```
       +
     /   \
    *     -
   / \   / \
  2   3 3   1
```
