(ns room-cleaning-algo.core)

(defn get-input []
  (read-line))

(defn put-output [output]
  (println output))

(defn handle-found-dishes []
  (println "Take it(them) to the sink or dishwasher. Waiting for input to continue.")
  ;;The depth of the stack would be enormous if the command was translated all the way down to base units (muscle movements?).
  (println "If you put them in the dishwasher, make sure they are rinsed first")
  (println "If you see dishes on the way out, pick them up"))

(defn collect-dishes
  []
  (loop [trips-made-to-kitchen 0]
    (println "Scan the room. Do you see a dishes? Y/N")
    (let [input (get-input)]
      (if (or (= input "y") (= input "Y"))
        (do (handle-found-dishes)
            (get-input)
            (recur (+ 1 trips-made-to-kitchen)))
        (do
          (println "Congrats, you are done doing dishes")
          trips-made-to-kitchen)))))


(defn -main []
  {:trips-made-to-the-kitchen-for-dishes (collect-dishes)})
