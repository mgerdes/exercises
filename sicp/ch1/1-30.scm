(define (cube x) (* x x x))
(define (inc x) (+ x 1))

(define (sum term a next b)
  (define (iter a result)
    (if (> a b)
      result
      (iter (next a) (+ (term a) result))))
  (iter a 0))

(define (sum-cubes a b)
  (sum cube a inc b))
