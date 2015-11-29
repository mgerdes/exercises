(define (make-queue)
  (let ((front-ptr '())
        (rear-ptr '()))
    (define (empty-queue?)
      (null? front-ptr))

    (define (insert-queue! item)
      (let ((new-pair (cons item '())))
        (if (empty-queue?)
          (begin
            (set! front-ptr new-pair)
            (set! rear-ptr new-pair)
            front-ptr)
          (begin
            (set-cdr! rear-ptr new-pair)
            (set! rear-ptr new-pair)
            front-ptr))))

    (define (delete-queue!)
      (if (empty-queue?)
        (display "Cannot delete from empty queue")
        (begin
          (set! front-ptr (cdr front-ptr))
          front-ptr)))

    (define (front-queue)
      (if (empty-queue?)
        (display "Cannot get front of empty queue")
        (car front-ptr)))

    (define (dispatch m) 
      (cond ((eq? m 'insert) 
             (lambda (item)
               (insert-queue! item)))
            ((eq? m 'delete)
             (delete-queue!))

            ((eq? m 'front)
             (front-queue))))
    dispatch))
