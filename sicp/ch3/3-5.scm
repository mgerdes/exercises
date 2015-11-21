(define (random-in-range low high)
  (let ((range (- high low)))
    (+ low (random range))))

(define (rectangle-area x1 x2 y1 y2)
  (abs (* (- x1 x2) (- y1 y2)))) 

(define (monte-carlo trials experiment)
  (define (iter trials-remaining trials-passed)
    (cond ((= trials-remaining 0)
           (exact->inexact (/ trials-passed trials)))
          ((experiment)
           (iter (- trials-remaining 1) (+ trials-passed 1)))
          (else
            (iter (- trials-remaining 1) trials-passed))))
  (iter trials 0))

(define (estimate-integral P x1 x2 y1 y2 trials)
  (* (rectangle-area x1 x2 y1 y2)
     (monte-carlo 
       trials 
       (lambda ()
         (P (random-in-range x1 x2) (random-in-range y1 y2))))))

(define (unit-circle x y)
  (<= (+ (* x x) (* y y)) 1)) 

(define pi (estimate-integral unit-circle -1.0 1.0 -1.0 1.0 10000))
