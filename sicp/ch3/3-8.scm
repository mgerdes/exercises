(define count 0)

(define (f x)
  (begin
    (set! count (+ 1 count))
    (cond ((and (= 1 count) (= 0 x)) 0)
          ((and (= 1 count) (= 1 x)) 1)
          (else 0))))

(+ (f 0) (f 1))
