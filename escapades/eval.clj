;; Map implemented from scratch
;; http://www.lispcast.com/tdd-clojure-core-map

(defn my-map [f coll]
  (if (empty? coll) '()
    (cons (f (first coll)) (my-map f (rest coll)))))


;; Reduce implemented using recursion
;; http://www.lispcast.com/lets-tdd-clojure-core-reduce

(defn my-reduce [f init coll]
  (if (empty? coll) init
    (my-reduce f (f init (first coll)) (rest coll))))

;; comp implemented from scratch
(defn my-flatten [x]
  (mapcat #(if (coll? %) (my-flatten %) [%]) x))

;; comp implemented from scratch

(defn comp [& fs]         ; takes a variable number of functions as input
  (let [fs (reverse fs)]  ; and applies them from right to left
    (fn [& args]
      (loop [ret (apply (first fs) args) fs (next fs)]
        (if fs
          (recur ((first fs) ret) (next fs))
          ret)))))

;; A Lisp interpreter
;; http://www.lispcast.com/the-most-important-idea-in-computer-science

(defn my-eval [env exp]
  (condp apply [exp]
    symbol? (env exp)
    list? (let [[f & args] exp]
            (condp = f
              'if (let [[test then else] args]
                    (my-eval env (if (my-eval env test) then else)))
              'do (let [args' (map (partial my-eval env) args)]
                    (last args'))
              'let (let [[[var val] & body] args]
                     (my-eval (assoc env var val) (cons 'do body)))
              (let [[f' & args'] (map (partial my-eval env) exp)]
                (apply f' args'))))
    exp))

;; Implementing core functions - Paper of John McCarthy
;; http://www-formal.stanford.edu/jmc/recursive.pdf



;; Quicksort using quasiquoting: inspired by the
;; [Haskell Introduction](https://wiki.haskell.org/Introduction)

(defn qsort [[pvt & rs]]
  (if pvt
    `(~@(qsort (filter #(<  % pvt) rs))
      ~pvt
      ~@(qsort (filter #(>= % pvt) rs)))))

;; == Interesting uses of reduce

;; path-walking nested maps
(reduce get {:a {:b {:c 42}}} [:a :b :c]) ;42

;; composing comparators
;; fun fact: the accumulator does not need to be a collection, but can be anything - including a function
(defn compose-comparators [comparators]
  (reduce (fn [composed-comparator comparator]
            (fn [a b]
              (let [n (composed-comparator a b)]
                (if (zero? n)
                  (comparator a b)
                  n))))
          (constantly 0)
                     comparators))
