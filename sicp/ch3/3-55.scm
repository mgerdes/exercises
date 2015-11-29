(define (add-streams s1 s2)
  (stream-map + s1 s2)) 

(define (infinite x)
  (cons-stream x (infinite x))) 

(define (partial-sum s)
  (cons-stream (stream-car s)
               (add-streams (partial-sum (stream-cdr s))
                            (infinite (stream-car s)))))

(define integers
  (cons-stream 1
               (stream-map (lambda (x) (+ 1 x)) integers)))
