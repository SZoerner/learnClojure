# Setup

## Leiningen

[Leiningen](http://leiningen.org/) is a command line taskrunner to manage Clojure artifacts and the nitty-gritty of fast development.
Think of it as the fusion of Maven, Ant and a fusion reactor. It covers tasks such as build management and dependency resolution. Moreover, it provides an interactive shell called a REPL, which we will use later on. Last but not least it can be further extended via plugins.

If you are using Windows, there is the [leiningen-win-installer](http://leiningen-win-installer.djpowell.net/) for a graphical installation process. For Linux there surely is an equivalent rpm / deb /xx package. OSX users can get it via homebrew.

After the installation is completed, open a shell and call ``lein`` - you should see some output starting with "*Leiningen is a tool for working with Clojure projects.*"
If you do, call ``lein repl`` to start the interactive shell.

## REPL

A ***R***ead-***E***val-***P***rint-***L***oop is just like a command shell but to interact with your programm. The origin is from the steps of interaction that happen each time you enter a command:

1. *Read* the console input as text.
2. *Eval* the input as instructions.
3. *Print* the response value back to the console screen.
4. *Loop* back o the beginning.
