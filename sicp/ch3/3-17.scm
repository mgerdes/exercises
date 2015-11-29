(define (count-pairs x)
  (let ((pairs-seen '()))
    (define (loop x)
      (if (not (pair? x))
        0
        (+ (loop (car x))
           (loop (cdr x))
           (if (member x pairs-seen)
             0
             (begin
               (set! pairs-seen (cons x pairs-seen))
               1)))))
    (loop x)))

(define z (cons 3 '()))
(define y (cons 2 z))
(define x (cons 1 y))

(count-pairs x) ; => 3

(set-car! y z)
(count-pairs x) ; => 3

(set-car! x y)
(count-pairs x) ; => 3
