(defn fact
  [x]
  (if (< x 1)
    1N
    (* x (fact (- x 1)))))

(println (apply + (map #(Character/getNumericValue %) (seq (String/valueOf (fact 100))))))
