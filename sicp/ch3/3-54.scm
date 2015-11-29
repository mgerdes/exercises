(define (mul-streams s1 s2)
  (stream-map * s1 s2))

(define integers
  (cons-stream 1
               (stream-map (lambda (x) (+ 1 x)) integers)))

(define factorials (cons-stream 1 
                                (mul-streams integers factorials)))
