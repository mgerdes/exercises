(define (make-point x y) (cons x y))
(define (x-point p) (car p))
(define (y-point p) (cdr p))

(define (make-segment p1 p2) (cons p1 p2))
(define (start-segment l) (car l))
(define (end-segment l) (cdr l))

; creates a rectangle using a line segment, which is a diagonal on the rectangle.
(define (make-rectangle l) l)
; creates a rectangle using two points, coming from 2 points on a diagonal. 
(define (make-rectangle p1 p2) (make-segment p1 p2))

(define (rectangle-width r)
  (abs (- (x-point (start-segment r)) 
          (x-point (end-segment r)))))

(define (rectangle-height r)
  (abs (- (y-point (start-segment r)) 
          (y-point (end-segment r)))))

(define (perimeter r)
  (* 2 (+ (rectangle-width r) (rectangle-height r))))

(define (area r)
  (* (rectangle-width r) (rectangle-height r)))

(perimeter (make-rectangle (make-point -1 -1) (make-point 1 1))) ; => 8
(area (make-rectangle (make-point -1 -1) (make-point 1 1))) ; => 4
