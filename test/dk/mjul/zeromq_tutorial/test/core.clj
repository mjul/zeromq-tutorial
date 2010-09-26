(ns dk.mjul.zeromq-tutorial.test.core
  (:use [dk.mjul.zeromq-tutorial.core] :reload-all)
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
  