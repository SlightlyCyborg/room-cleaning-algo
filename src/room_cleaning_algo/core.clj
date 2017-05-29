(ns room-cleaning-algo.core)

(defn get-input []
  (read-line))

(defn change-input-fn [the-fn]
  (defn get-input []
    (the-fn)))

(defn put-output [output]
  (println output))

(defn change-output-fn [the-fn]
  (defn put-output [output]
    (the-fn output)))

(defn handle-found-dishes []
  (put-output "Take it(them) to the sink or dishwasher. Waiting for input to continue.")
  ;;The depth of the stack would be enormous if the command was translated all the way down to base units (muscle movements?).
  (put-output "If you put them in the dishwasher, make sure they are rinsed first")
  (put-output "If you see dishes on the way out, pick them up"))

(defn yes? []
  (let [input (get-input)]
    (if (or (= input "y") (= input "Y"))
      true
      false)))

(defn no? []
  (not (yes?)))

(defn collect-dishes
  []
  (loop [trips-made-to-kitchen 0]
    (put-output "Scan the room. Do you see dirty dishes?")
      (if (yes?)
        (do (handle-found-dishes)
            (get-input)
            (recur (+ 1 trips-made-to-kitchen)))
        (do
          (put-output "Congrats, you are done doing dishes")
          trips-made-to-kitchen))))

(defn make-bed []
  (let [result (atom {})]
    (put-output "Are the sheets on correctly and smoothed?")
    (if (no?)
      (do (put-output "Remove the blanket")
          (put-output "Smooth the sheets.")
          (swap! result assoc :smoothed-sheets true)
          (get-input)))
    (put-output "Is the pillow in place?")
    (if (no?)
      (do (put-output "Put the pillow in place.")
          (swap! result assoc :placed-pillow true)
          (get-input)))
    (put-output "Is the blanket on smooth?")
    (if (no?)
      (do (put-output "Smooth out the blanket")
          (swap! result assoc :smoothed-blanket true)
          (get-input)))
    @result))

(defn hang-things
  []
  (put-output "Now it is time to do some organizing.")
  (loop [missing-items 0]
    (put-output "Do you see any spot on the wall of things that is normally occupied but is not currently?")
    (if (yes?)
      (do
        (put-output "Can you remember what goes there?")
        (if (yes?)
          (do (put-output "Find it and put it in its place.")
              (get-input))
          (put-output "Ok. Well, maybe you will remember later"))
        (recur (+ 1 missing-items)))))
  (put-output "OK. Now, if you would like, make new spots on the wall of things."))



(defn -main []
  {:trips-made-to-the-kitchen-for-dishes (collect-dishes)
   :things-had-to-be-done-to-make-bed (make-bed)})
