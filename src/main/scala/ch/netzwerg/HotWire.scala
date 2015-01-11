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

  def main() {
    init()
    dom.onkeypress = { (e: KeyboardEvent) =>
      if (e.keyCode == KeyCode.enter) {
        reset()
      } else {
        fail()
      }
    }
  }

  def init() {
    reset()
    dom.setInterval(() => jQuery("#stopwatch").text(elapsedTimeFormatted()), 15)
  }

  def elapsedTimeFormatted() = {
    val elapsed: Long = (js.Date.now() - startTime).toLong
    val mins = (elapsed / (1000 * 60)) % 60
    val secs = (elapsed / 1000) % 60
    val millis = elapsed % 1000
    "%02d:%02d:%03d".format(mins, secs, millis)
  }

  def reset() {
    startTime = js.Date.now()
    setBgColor(DarkGreen)
  }

  def fail() {
    setBgColor(DarkRed)
  }

  def setBgColor(color: Color) {
    dom.document.body.style.background = color
  }

}