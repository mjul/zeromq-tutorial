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


;; echo-server and echo-client are the rrserver and rrclient examples from the zeromq examples.
;; Each exchange is a pair of messages between the client and server: a request and a reply.

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

;; push-pull-producer and push-pull-consumer use one-way messaging. No reply is sent.
;; This pattern is useful for async communication or for distributing commands to worker processes.

(defn push-pull-producer [ctx endpoint msgs]
  (with-open [socket (make-socket ctx :push)]
    (zmq/connect socket endpoint)
    (doseq [m msgs]
      (send-msg! socket m))
    msgs))

(defn push-pull-consumer [ctx endpoint n]
  (with-open [socket (make-socket ctx :pull)]
    (zmq/bind socket endpoint)
    (doall (for [i (range n)]
	     (recv-msg! socket)))))
