package squid.squidgrid.util;

public class Dijkstra
{
    /*
     (defn find-cells [^doubles a cell-kind]
    (persistent! (areduce ^doubles a i ret (transient {})
                          (if (= (hiphip/aget ^doubles a i) cell-kind) (assoc! ret i cell-kind) ret))))

(defn find-goals [^doubles a]
  (find-cells a GOAL))

(defn find-walls [^doubles a]
    (persistent! (areduce ^doubles a i ret (transient {})
                          (if (>= (hiphip/aget ^doubles a i) (double wall)) (assoc! ret i wall) ret))))

(defn find-floors [^doubles a]
  (find-cells a floor))

(defn find-lowest [^doubles a]
  (let [low-val (hiphip/amin a)]
    (find-cells a low-val)))

(defn find-monsters [m]
    (into {} (for [mp (map #(:pos @%) m)] [mp 1.0])))

(defn dijkstra
  ([a]
     (dijkstra a (find-walls a) (find-lowest a)))
  ([dun _]
     (dijkstra (:dungeon dun) (merge (find-walls (:dungeon dun)) (find-monsters @(:monsters dun))) (find-lowest (:dungeon dun))))
  ([a closed open-cells]
     (loop [open open-cells]
       (when (seq open)
         (recur (reduce (fn [newly-open [^long i ^double v]]
                          (reduce (fn [acc [ dir mid ]]
                                    (if (or (closed dir) (open dir) ; (closed mid)
                                            (>= (+ 1.0 v) (hiphip/aget ^doubles a dir)))
                                      acc
                                      (do (hiphip/aset ^doubles a dir (+ 1.0 v))
                                          (assoc acc dir (+ 1.0 v)))))
                                  newly-open, [[(- i wide)(- i wide2)]
                                               [(+ i wide)(+ i wide2)]
                                               [(- i 1)(- i 2)]
                                               [(+ i 1)(+ i 2)]]))
                        {}, open))))
     a))
     */
}
