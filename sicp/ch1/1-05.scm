(define (p) (p))

(define (test x y)
  (if (= x 0)
    0
    y))

(test 0 (p))

; applicative order evaluation will run forever because the evaluator would have to evaluate (p) right away.
; normal order evaluation will return 0, because the evaluator won't actually evaluate (p) right away, because it doesn't actually need (p) right away. it actually never needs (p), so it will never be evaluated.
