package ch.netzwerg

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.extensions.{Color, KeyCode}
import org.scalajs.jquery.jQuery

import scala.scalajs.js

object HotWire extends js.JSApp {

  val DarkGreen = Color("#006400")
  val DarkRed = Color("#8B0000")

  var startTime = js.Date.now()
  var stopTime: Option[Double] = None

  def main() {
    init()
    dom.onkeypress = (e: KeyboardEvent) => e.keyCode match {
      case KeyCode.enter => reset()
      case _ => if (stopTime.isDefined) fail()
    }
  }

  def init() {
    reset()
    dom.setInterval(() => jQuery("#stopwatch").text(elapsedTimeFormatted()), 15)
  }

  def elapsedTimeFormatted() = {
    val stopTimeOrNow: Double = stopTime.getOrElse(js.Date.now())
    val elapsed = (stopTimeOrNow - startTime).toLong
    val mins = (elapsed / (1000 * 60)) % 60
    val secs = (elapsed / 1000) % 60
    val millis = elapsed % 1000
    "%02d:%02d:%03d".format(mins, secs, millis)
  }

  def reset() {
    startTime = js.Date.now()
    stopTime = None
    setBgColor(DarkGreen)
  }

  def fail() {
    stopTime = Some(js.Date.now())
    setBgColor(DarkRed)
  }

  def setBgColor(color: Color) {
    dom.document.body.style.background = color
  }

}