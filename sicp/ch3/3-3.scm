(define (make-account amount password)
  (lambda (password-guess request) 
    (lambda (request-amount)
      (cond ((not (eq? password-guess password))
             "Incorrect Password")
            ((eq? request 'withdraw)
             (begin
               (set! amount (- amount request-amount))
               amount))
            ((eq? request 'deposit)
             (begin
               (set! amount (+ amount request-amount))
               amount))
            (else "Invalid Request")))))

(define acc (make-account 100 'password))

((acc 'password 'withdraw) 40) ; => 60
((acc 'wrong 'deposit) 40) ; => "Incorrect Password"
