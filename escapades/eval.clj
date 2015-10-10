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
