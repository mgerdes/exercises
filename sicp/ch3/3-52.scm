(define sum 0)

(define (accum x)
  (set! sum (+ x sum))
  sum)

(define (display-stream s)
  (if (null? s)
    '()
    (begin
      (display (string (stream-car s) " "))
      (display-stream (stream-cdr s)))))

(define (stream-enumerate-interval x y)
  (if (= y x)
    (cons-stream x '())
    (cons-stream x (stream-enumerate-interval (+ x 1) y)))) 

; seq = (1 3 6 10 15 21 28 36 45 55 66 78 91 105 120 136 153 171 190 210)

(define seq (stream-map accum (stream-enumerate-interval 1 20))) 
; Only evaluates first item of stream
; Therefore sum = 1

(define y (stream-filter even? seq))
; Evaluates second item of stream, 3, but this is not even so it also evaluates the next item, 6, which is even.
; So nothing else gets evaluated and sum = 6

(define z (stream-filter 
            (lambda (x) (= (remainder x 5) 0)) 
            seq))
; Evaluates fourth item of stream, 10, which is divisble by 5, so nothing else is evaluated.
; Therefore sum = 10

(stream-ref y 7)
; Gets the 7th item in seq that is even.
; Therefore it evaluates 15, 21, 28, 36, 45, 55, 66, 78, 91, 105, 120, and 136.
; But stops at 136, as it's the 7th even item.
; Therefore sum = 136

(display-stream z)
; Will evaluate the rest of items in seq, as all items need to be
; evaluated to be displayed.
; Therefore sum = 210

; Yes the answers change if we are not memoizing the calculations.
; The whole analysis above hinged on the fact that each
; calculation was done only once. If this is not the case
; then things would be different.
; For instance after defining y, sum would be 7 instead of 6,
; because here 1 would be added twice instead of just once.
