(ns room-cleaning-algo.core)

(defn get-input []
  (read-line))

(defn change-input-fn [the-fn]
  (defn get-input []
    (the-fn)))

(defn wait-on-user []
  (get-input))

(defn change-wait-on-user-fn [the-fn]
  (defn wait-on-user []
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
  (put-output "Ok. Lets do the dishes.")
  (loop [trips-made-to-kitchen 0]
    (put-output "Scan the room. Do you see dirty dishes?")
      (if (yes?)
        (do (handle-found-dishes)
            (get-input)
            (recur (+ 1 trips-made-to-kitchen)))
         (do
           (if (= trips-made-to-kitchen 0)
             (put-output "Odd. Did you not eat?")
             (put-output "Congrats, you are done doing dishes"))
           trips-made-to-kitchen))))

(defn make-bed []
  (let [result (atom {})]
    (put-output "Is your bed made?")
    (if (no?)
     (do (put-output "Are the sheets on correctly and smoothed?")
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
               (get-input)))))
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
  (put-output "OK. Now, if you would like, make new spots on the wall of things.")
  (get-input))

(defn do-the-dishes []
  (put-output "Now it is time to do the dishes. Have you done them yet?")
  (if (no?)
    (put-output "Ok. Go do them")
    (put-output "Nice.")))

(defn do-the-laundry []
  (put-output "Is there enough dirty cloths to make a full load of laundry?")
  (if (yes?)
    (do
      (put-output "Ok. First empty out one of the bins")
      (wait-on-user)
      (put-output "Next, start with the most important clothing first (underwear?) and begin hand selecting which cloths should be washed.")
      (wait-on-user)
      (put-output "Now take them to the washer and start the load.")
      1)
    (do
      (put-output "Ok. No laundry needs to be washed today.")
      0))
  (wait-on-user)
  (put-output "Is there a load of laundry that has recently finished drying?")
  (if (yes?)
    (do
      (put-output "Ok. Bring it up to your room")
      (wait-on-user)
      (put-output "Now fold or hang the laundry")
      (wait-on-user))))

(defn extra-cleaning []
  (put-output "Most likely, there are still things that need to be cleaned. Is this correct?")
  (if (yes?)
    (do
      (loop [times 0]
        (put-output "Ok. Put those things in their place and report back when you are finished.")
        (wait-on-user)
        (put-output "Do another check. Do you see more things that you wish to clean?")
        (if (yes?)
             (recur (+ 1 times)))))
    (put-output "Oh. Ok. Well, you are done cleaning.")))


(defn -main []
  {:loads-of-laundry-started (do-the-laundry)
   :trips-made-to-the-kitchen-for-dishes (collect-dishes)
   :things-had-to-be-done-to-make-bed (make-bed)
   :hang-things (hang-things)
   :do-the-dishes (do-the-dishes)})
