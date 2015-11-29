(define (front-ptr queue) (car queue))
(define (rear-ptr queue) (cdr queue))
(define (set-front-ptr! queue item) (set-car! queue item))
(define (set-rear-ptr! queue item) (set-cdr! queue item))
(define (empty-queue? queue) (null? (front-ptr queue)))
(define (make-queue) (cons '() '()))

(define (front-queue queue)
  (if (empty-queue? queue)
    (display "FRONT called with an empty queue")
    (car (front-ptr queue))))

(define (insert-queue! queue item)
  (let ((new-pair (cons item '())))
    (cond ((empty-queue? queue)
           (set-front-ptr! queue new-pair)
           (set-rear-ptr! queue new-pair)
           queue)
          (else
            (set-cdr! (rear-ptr queue) new-pair)
            (set-rear-ptr! queue new-pair)
            queue))))

(define (delete-queue! queue)
  (cond ((empty-queue? queue)
         (display "DELETE called with empty queue"))
         (else
           (set-front-ptr! queue (cdr (front-ptr queue)))
           queue)))

(define (print-queue queue)
  (display (front-ptr queue))
  (newline))

(define q1 (make-queue))
(insert-queue! q1 'a) ; => ((a) a)
(insert-queue! q1 'b) ; => ((a b) b)
(delete-queue! q1)    ; => ((b) b)
(delete-queue! q1)    ; => (() b)
                      ; The returned list has it's car as the front of the queue and it's
                      ; cdr as the back of the queue.
                      ; Thus on insert it makes sense that the cdr would change to the inserted value.
                      ; On delete however, the cdr does not change. Only the car changes.
