(define (count-pairs x)
  (if (not (pair? x))
    0
    (+ (count-pairs (car x))
       (count-pairs (cdr x))
       1)))

(define z (cons 3 '()))
(define y (cons 2 z))
(define x (cons 1 y))

(count-pairs x) ; => 3

(set-car! y z)
(count-pairs x) ; => 4

(set-car! x y)
(count-pairs x) ; => 7
