(define (make-interval x y) (cons x y))

(define (lower-bound x) (car x))

(define (upper-bound x) (cdr x))

(define (make-center-percent center percent)
    (make-interval (- center (* center percent)) 
                   (+ center (* center percent))))

(define (center x) (/ (+ (upper-bound x) (lower-bound x)) 2))

(define (percent x) (/ (- (upper-bound x) (center x))
                              (center x)))
