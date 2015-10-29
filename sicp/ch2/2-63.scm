(define (entry tree)
  (car tree))

(define (left-branch tree) 
  (cadr tree))

(define (right-branch tree)
  (caddr tree))

(define (tree->list-1 tree)
  (if (null? tree)
    '()
    (append (tree->list-1 (left-branch tree))
            (cons (entry tree)
                  (tree->list-1 (right-branch tree))))))

(define (tree->list-2 tree)
  (define (copy-to-list tree result-list)
    (if (null? tree)
      result-list
      (copy-to-list (left-branch tree)
                    (cons (entry tree)
                          (copy-to-list (right-branch tree)
                                        result-list)))))
  (copy-to-list tree '()))

(define tree1 '(7 (3 (1 () ()) (5 () ())) (9 () (11 () ()))))
(define tree2 '(3 (1 () ()) (7 (5 () ()) (9 () (11 () ())))))
(define tree3 '(5 (3 (1 () ()) ()) (9 (7 () ()) (11 () ()))))

(newline)
(display (tree->list-1 tree1)) 
(newline)
(display (tree->list-1 tree2)) 
(newline)
(display (tree->list-1 tree3)) 
(newline)
(display (tree->list-2 tree1)) 
(newline)
(display (tree->list-2 tree2)) 
(newline)
(display (tree->list-2 tree3)) 
(newline)

; (a) Both functions create the same list from the tree. 

; (b) tree->list-1 has to append two lists for each item, append is O(n)
;       and it is done O(n) times, so this algorithm is O(n^2)
;     tree->list-2 only uses cons on each iteration, which is O(1), therefore
;       this algorithm is only O(n).
