(define (make-mobile left right)
  (list left right))

(define (make-branch length structure)
  (list length structure))

(define (left-branch mobile) (car mobile))
(define (right-branch mobile) (car (cdr mobile)))

(define (branch-length branch) (car branch))
(define (branch-structure branch) (car (cdr branch)))

(define (total-weight mobile)
  (define (branch-weight branch)
    (if (number? (branch-structure branch))
      (branch-structure branch)
      (total-weight (branch-structure branch))))

  (+ (branch-weight (left-branch mobile)) 
     (branch-weight (right-branch mobile))))

(define (balanced? mobile)
  (define (torque b)
    (* (branch-length b) 
       (if (number? (branch-structure b))
         (branch-structure b)
         (total-weight (branch-structure b))))) 

  (define (balanced-branch? b)
    (balanced? (branch-structure b)))

  (if (number? mobile)
    #t
    (and (balanced-branch? (left-branch mobile))
         (balanced-branch? (right-branch mobile))
         (= (torque (left-branch mobile))
            (torque (right-branch mobile))))))

(define m (make-mobile (make-branch 1 20) 
                       (make-branch 2 (make-mobile 
                                        (make-branch 1 5)
                                        (make-branch 1 5)))))

(balanced? m) ;=> #t

; If constructors are changed to use cons, the only selectors will have to change.
