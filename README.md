What is this?
-------------
Status indicator for some [MaKey MaKey](http://makeymakey.com) Hot Wire fun:

![Hot Wire](http://netzwerg.ch/images/hotwire.jpg)

Why is this here?
-----------------
Because kids want to play Hot Wire, and mums wants to hack [Scala.js](http://www.scala-js.org) :-)


Can I play it?
--------------
This indicator is deployed [here](http://netzwerg.ch/hotwire). The [MaKey MaKey](http://makeymakey.com) part you need to create yourself:

![Hot Wire HowTo](http://netzwerg.ch/images/hotwire-howto.png)

Pressing **Enter** resets the stop watch. Touching the **left** post starts the clock: While it's running, any touching of the hot wire will trigger a fail, touching the **right** post will complete successfully. Go!

As an alternative to metal posts with isolation, you could also use wood or plastic posts and connect the start/stop wires with tin foil.

How to build?
-------------
Use `sbt ~fastOptJS` and open `index-fastopt.html` (see underlying [bootstrapping skeleton]
(https://github.com/sjrd/scala-js-example-app) for more details).
