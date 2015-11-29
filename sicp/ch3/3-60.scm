(define (scale-stream stream constant)
  (stream-map (lambda (x) (* constant x)) stream)) 

(define (integrate-series stream)
  (define (do-it s power)
    (cons-stream (/ (stream-car s) (+ power 1))
                 (do-it (stream-cdr s) (+ power 1))))
  (do-it stream 0))

(define exp-series
  (cons-stream 1 (integrate-series exp-series)))

(define cosine-series
  (cons-stream 1 (integrate-series sine-series)))

(define sine-series
  (cons-stream 0 (scale-stream (integrate-series cosine-series) -1)))

(define (add-series s1 s2)
  (stream-map + s1 s2))

(define (mul-series s1 s2)
  (cons-stream (* (stream-car s1) (stream-car s2)) (add-series 
                                                     (scale-stream (stream-cdr s2) (stream-car s1))
                                                     (mul-series (stream-cdr s1) s2))))

(define one (add-series (mul-series sine-series sine-series) 
                        (mul-series cosine-series cosine-series)))
