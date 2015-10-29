(define (dec x) (- x 1))

(define (euler-d i)
  (if (= (remainder (- i 1) 3) 0) 
    (* 2 (+ 1 (round (/ i 3))))
    1))

(define (euler-n i) 1.0)

(define (cont-frac n d k)
  (define (cont-frac-iter result i)
    (if (= -1 i)
      result
      (cont-frac-iter (/ (n i) (+ (d i) result)) (dec i))))
  (cont-frac-iter 0 k))

(+ 2 (cont-frac euler-n euler-d 100)) ; => 2.71828
