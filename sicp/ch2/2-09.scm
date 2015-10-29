(define I1 (make-interval x1 y1))
(define I2 (make-interval x2 y2))
(define I3 (add-interval I1 I2))

; I3 = (x1+x2, y1+y2)
; (width I1) = (y1 - x1) / 2
; (width I2) = (y2 - x2) / 2
; (width I3) = (y1 + y2 - x1 - x2) / 2
;            = (y1 - x1) / 2 + (y2 - x2) / 2 
;            = (width I1) + (width I2)
; Similar argument for sub-interval

(define I1 (make-interval 2 4))
(define I2 (make-interval 1 3))
(define I3 (mul-interval I1 I2)) ;= (2,12)

; (width I1) = 1
; (width I2) = 1
; (width I3) = 5
