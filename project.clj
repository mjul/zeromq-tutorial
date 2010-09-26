(defproject zeromq-tutorial "1.0.0-SNAPSHOT"
  :description "ZeroMQ tutorial"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojars.mikejs/clojure-zmq "2.0.7-SNAPSHOT"]
                 [org.zmq/jzmq "2.0.6-SNAPSHOT"]]
  :native-deps [[org.clojars.mikejs/jzmq-native-deps "2.0.7-SNAPSHOT"]
		[org.clojars.mikejs/jzmq-macosx-native-deps "2.0.7-SNAPSHOT"]]
  ;; This sets the 'java.library.path' property
  ;; so Java can find the ZeroMQ dylib
  :native-path "/usr/local/lib"
  :dev-dependencies [[swank-clojure "1.3.0-SNAPSHOT"]
		     [lein-difftest "1.3.0"]]
  )