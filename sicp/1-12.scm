; 1
; 1 1
; 1 2 1
; 1 3 3 1
; 1 4 6 4 1

(define (pascal x y)
  (cond ((= x 0) 1)
        ((= x y) 1)
        ((or (< x 0) (< y 0) (> x y)) "Invalid input.")
        (else (+ (pascal x (- y 1)) 
                 (pascal (- x 1) (- y 1))))))
