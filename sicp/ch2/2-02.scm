(define (make-point x y) (cons x y))
(define (x-point p) (car p))
(define (y-point p) (cdr p))

(define (make-segment p1 p2) (cons p1 p2))
(define (start-segment l) (car l))
(define (end-segment l) (cdr l))

(define (midpoint-segment l)
  (make-point
    (/ (+ (x-point (start-segment l)) 
          (x-point (end-segment l))) 
       2)
    (/ (+ (y-point (start-segment l)) 
          (y-point (end-segment l))) 
       2)))
     
(define (print-point p)
  (newline)
  (display "(")
  (display (x-point p))
  (display ",")
  (display (y-point p))
  (display ")"))

(define line (make-segment (make-point 1 1) (make-point -1 -1)))
(print-point (midpoint-segment line)) ; => (0,0)