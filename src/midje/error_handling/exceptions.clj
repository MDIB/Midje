(ns midje.error-handling.exceptions
  (:use [clojure.string :only [join]]
        [clj-stacktrace.repl :only [pst+ pst-str]]
        [midje.util.colorize :only [colorizing?]]))


(defn user-error [& lines]
  (Error. (join (System/getProperty "line.separator") lines)))

;; Beautiful, ergonomic stacktraces

(defprotocol FriendlyStacktrace 
  (friendly-stacktrace [this]))

(extend-protocol FriendlyStacktrace
  Throwable
  (friendly-stacktrace [this] 
    (if (colorizing?)
      (with-out-str (pst+ this))
      (pst-str this))))  


;; When a fact throws an Exception or Error it gets wrapped
;; in this deftype

(defprotocol ICapturedThrowable
  (throwable [this]))
                       
(deftype CapturedThrowable [ex] 
  ICapturedThrowable 
  (throwable [this] ex)

  FriendlyStacktrace
  (friendly-stacktrace [this] (friendly-stacktrace (.throwable this))))

(defn captured-throwable [ex] 
  (CapturedThrowable. ex))

(defn captured-throwable? [x]
  (instance? CapturedThrowable x))