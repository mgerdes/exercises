(define (element-of-set? x set)
    (cond ((null? set) #f) 
          ((= (car set) x) #t)
          (else (element-of-set? x (cdr set)))))

(define (adjoin-set x set)
  (cons x set))

(define (union-set set1 set2)
  (append set1 set2))

(define (intersection-set set1 set2)
  (cond ((or (null? set1) (null? set2)) '()) 
        ((element-of-set? (car set1) set2)
         (adjoin-set (car set1)
                     (intersection-set (cdr set1) set2)))
        (else (intersection-set (cdr set1) set2))))

; element-of-set is still O(n), however n could potentially be larger now.
; adjoin-set goes from O(n) to O(1)
; union-set is also now O(1)
; intersection-set stays at O(n^2) because we still have to call element-of-set
; for each item.
