(ns com.mjul.zeromq-tutorial.core
  (:use [clojure.contrib.json])
  (:require [org.zeromq.clojure :as zmq]))

(defn serialize [x]
  (-> x json-str .getBytes))

(defn deserialize [x]
  (-> x String. read-json))

(defn make-context []
  (zmq/make-context 1))

(defn make-socket [ctx socket-type]
  (zmq/make-socket ctx
		   (socket-type {:reply zmq/+rep+, :request zmq/+req+,
				 :pull zmq/+upstream+, :push zmq/+downstream+})))

(defn close-socket [s]
  (.close s))

(defn send-msg! [socket msg]
  (zmq/send- socket (serialize msg)))

(defn recv-msg! [socket]
  (deserialize (zmq/recv socket)))

(defn echo-server [ctx endpoint]
  (with-open [socket (make-socket ctx :reply)]
    (zmq/bind socket endpoint)
    (let [msg (recv-msg! socket)]
      (send-msg! socket msg)
      msg)))

(defn echo-client [ctx endpoint msg]
  (with-open [socket (make-socket ctx :request)]
    (zmq/connect socket endpoint)
    (send-msg! socket msg)
    (let [reply (recv-msg! socket)
	  result {:request msg, :reply reply}]
      result)))


(defn pusher [ctx endpoint num]
  (let [socket (make-socket ctx :push)]
    (zmq/bind socket endpoint)
    (doseq [x num]
      (send-msg! socket (str "Message " x)))
    (close-socket socket)))

(defn puller [ctx endpoint num]
  (let [socket (make-socket ctx :pull)]
    (zmq/connect socket endpoint)
    (doseq [x num]
      (println (recv-msg! socket)))
    (close-socket socket)))
