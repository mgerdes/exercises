(define (cube x) (* x x x))

(define (sum term a next b)
  (if (> a b)
    0
    (+ (term a)
       (sum term (next a) next b))))

(define (integral f a b dx)
  (define (add-dx x) (+ x dx))
  (* (sum f (+ a (/ dx 2.0)) add-dx b)
     dx))


(define (integral-simpsons f a b n)
  (let ((h (/ (- b a) n)))
    (define (simpsons-term x) 
      (* (cond ((= x 0) 1) 
               ((= x n) 1)
               ((odd? x) 4)
               ((even? x) 2))
         (f (+ a (* x h)))))
    (define (inc x) (+ x 1))
    (* (/ h 3) (sum simpsons-term a inc n)))) 


; (integral cube 0 1 0.1) => 0.2487
; (integral cube 0 1 0.01) => 0.24998

; (integral-simpsons cube 0 1 100) => 1/4

; actual value = 1/4 
