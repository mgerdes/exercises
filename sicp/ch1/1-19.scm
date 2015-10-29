; T[a,b]^2 = T[bq+aq+ap,bp+aq] = [(bp+aq)q + (bq+aq+ap)q + (bq+aq+ap)p,
;                                 (bp+aq)q + (bq + aq + ap)q]
;                              = [b(2pq+2qq) + a(pp+qq) + a(2pq+qq),
;                                 b(pp+qq) + a(2pq+qq)]
;
;                              => p' = p^2+q^2, q' = 2pq+q^2

(define (fib n)
  (fib-iter 1 0 0 1 n))

(define (fib-iter a b p q count)
  (cond ((= count 0) b)
        ((even? count) (fib-iter a
                                 b
                                 (+ (* p p) (* q q))
                                 (+ (* 2 (* p q)) (* q q))
                                 (/ count 2)))
        (else (fib-iter (+ (* b q) (* a q) (* a p))
                        (+ (* b p) (* a q))
                        p
                        q
                        (- count 1)))))
