(ns com.mjul.zeromq-tutorial.test.core
  (:use [com.mjul.zeromq-tutorial.core] :reload-all)
  (:use [clojure.test]))

(deftest serialization-test
  (testing "Serialization"
    (is (= "foo" (deserialize (serialize "foo"))))
    (is (= {:msg "foo", :val 42} (deserialize (serialize {:msg "foo", :val 42}))))))

(deftest integration-point-to-point
  (testing "Request-reply messaging"
    (let [endpoint "tcp://127.0.0.1:5555"
	  ctx (make-context)
	  msg "Hello, world!"
	  server (future (echo-server ctx endpoint))
	  client (echo-client ctx endpoint msg)]
      (is (= msg @server))
      (is (= msg (:reply client))))))

(deftest integration-push-pull
  (testing "Push/pull messaging"
      (let [endpoint "tcp://127.0.0.1:5555"
	    ctx (make-context)
	    num-msg 10
	    msgs (for [i (range num-msg)]
		   (str "Message number " i))
	    sent (future (push-pull-producer ctx endpoint msgs))
	    recvd (push-pull-consumer ctx endpoint num-msg)]
	(is (= num-msg (count @sent)) "Expected correct number of messages sent.")
	(is (= msgs recvd)) "Expected to receive the messages.")))

