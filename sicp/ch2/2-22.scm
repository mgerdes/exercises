(define (square x) (* x x))

(define (square-list-backwards items)
  (define (iter things answer)
    (if (null? things)
      answer
      (iter (cdr things)
            (cons (square (car things))
                  answer))))
  (iter items '()))

; This returns list backwards because the following cons:
;   (cons (square (car things))
;         answer) 
; Puts the square of the new item (aka one later in the list) BEFORE the square
; of the older items (aka the ones first in the list).

(define (square-list items)
  (define (iter things answer)
    (if (null? things)
      answer
      (iter (cdr things)
            (cons answer
                  (square (car things))))))
  (iter items '()))

; cons takes two arguments and returns a data structure where the car is the first
; argument, and the cdr is the second argument.
; Thus (cons '() (square 1)) => ('() 1)
; So you end up with alot of inner nesting of lists in the end.
