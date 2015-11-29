(define (add-series s1 s2)
  (stream-map + s1 s2))

(define (scale-stream stream constant)
  (stream-map (lambda (x) (* constant x)) stream)) 

(define (mul-series s1 s2)
  (cons-stream (* (stream-car s1) (stream-car s2)) (add-series 
                                                     (scale-stream (stream-cdr s2) (stream-car s1))
                                                     (mul-series (stream-cdr s1) s2))))
(define (invert-unit-series s)
  (cons-stream 1 (scale-stream (mul-series (stream-cdr s) (invert-unit-series s)) -1)))
