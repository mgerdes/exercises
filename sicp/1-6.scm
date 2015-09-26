(define (new-if predicate then-clause else-clause)
  (cond (predicate then-clause)
        (else else-clause)))

(define (sqrt-iter guess x)
  (new-if (good-enough? guess x)
          guess
          (sqrt-iter (improve guess x)
                     x)))

; When sqrt-iter is run, it will run forever. This is because the evaluator is applicative. So the else clause will always be evaluated, even when it's not needed.
; So even if (good-enough? guess x) is true, the else clause will still be evaluated, thus causing an infinite loop.
