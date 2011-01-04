(ns behaviors.background-nesting.t-outside
  (:use clojure.test)
  (:use [midje.sweet])
  (:use [midje.test-util])
  (:use clojure.contrib.pprint)
)

;; This is a separate file because we're making namespace-wide changes

(unfinished outermost middlemost innermost)

(against-background [ (middlemost) => 33 ]
  (deftest backgrounds-span-deftests
    (fact
      (against-background (innermost) => 8)
      (+ (middlemost) (innermost)) => 41)))


(against-background [ (middlemost) => 33 ]
  (fact
    (against-background (innermost) => 8)
    (+ (middlemost) (innermost)) => 41))
