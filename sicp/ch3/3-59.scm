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
  (cons-stream 0 (integrate-series cosine-series)))
