# ZeroMQ Clojure Tutorial

This is a tutorial to using the ZeroMQ message queue with Clojure.

## Usage

Read the source and the tests to see examples of using ZeroMQ in Clojure.
If you create more examples, please contribute a patch!

## Installation

In order to use this software, ZeroMQ must be installed. 
Please refer to its website for details on how to install it: [ZeroMQ](http://www.zeromq.org)

You need to install both ZeroMQ and the Java bindings (jzmq).

For installation on Mac OSX I found the following guides to be helpful:

* Installation using the Homebrew package manager: [Setting up 0MQ for Clojure on OSX](http://blog.trydionel.com/2010/08/25/setting-up-0mq-for-clojure-on-osx/)
* Manual installation: [ZeroMQ and Clojure, a brief introduction](http://antoniogarrote.wordpress.com/2010/09/08/zeromq-and-clojure-a-brief-introduction/)

After you have installed these libraries run

    lein deps
    lein test

You may have to edit the project.clj file to point to the correct location of the ZeroMQ libraries:

	;; This sets the 'java.library.path' property
	;; so Java can find the ZeroMQ dylib
	:native-path "/usr/local/lib"

## License

Copyright (c) 2010 Martin Jul (www.mjul.com)

This tutorial is licensed under the MIT License. See the LICENSE file for
the license terms.

## Contact Information and References

* [Martin Jul](http://www.mjul.com)
* [Source code on GitHub](http://github.com/mjul/zeromq-tutorial)

Follow me:

* Twitter: @mjul [Martin Jul on Twitter](http://twitter.com/mjul)

