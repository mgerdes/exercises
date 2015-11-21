#lang scheme

(define cur-value 0)

(define (rand m)
  (cond ((eq? m 'reset)
         (lambda (val)
           (set! cur-value val))) 
        ((eq? m 'generate)
         (let ((a 91) (b 81) (m 10123) (return cur-value))
           (begin
             (set! cur-value (remainder (+ (* a cur-value) b) m))
             return)))))

(rand 'generate)
(rand 'generate)
(rand 'generate)
(rand 'generate)
(rand 'generate)
((rand 'reset) 10)
(rand 'generate)
(rand 'generate)
(rand 'generate)
(rand 'generate)
(rand 'generate)
