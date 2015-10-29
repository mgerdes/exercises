(define (inc x) (+ x 1))
(define (dec x) (- x 1))

(define (cont-frac n d k)
  (define (cont-frac-recur i)
    (if (= i k)
      (/ (n i) (d i))
      (/ (n i) (+ (d i) (cont-frac-recur (inc i))))))
  (cont-frac-recur 0))

(define (cont-frac n d k)
  (define (cont-frac-iter result i)
    (if (= -1 i)
      result
      (cont-frac-iter (/ (n i) (+ (d i) result)) (dec i))))
  (cont-frac-iter 0 k))

; (cont-frac (lambda (x) 1.0) (lambda (x) 1.0) 100) => 0.61803
