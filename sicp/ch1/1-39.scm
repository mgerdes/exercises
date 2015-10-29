(define (inc x) (+ x 1))
(define (dec x) (- x 1))

(define (cont-frac n d k)
  (define (cont-frac-iter result i)
    (if (= -1 i)
      result
      (cont-frac-iter (/ (n i) (+ (d i) result)) (dec i))))
  (cont-frac-iter 0 k))

(define (tan-cf x k)
  (define (lambert-d i)
    (inc (* 2 i)))

  (define (lambert-n i)
    (if (= i 0)
      x
      (- (* x x))))

  (exact->inexact (cont-frac lambert-n lambert-d k)))

(tan-cf 5 100) ; => -3.3805, actual is -3.3805 :)
