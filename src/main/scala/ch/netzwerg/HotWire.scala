package ch.netzwerg

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.extensions.{Color, KeyCode}
import org.scalajs.jquery.jQuery

import scala.scalajs.js

object HotWire extends js.JSApp {

  val DarkGreen = Color("#006400")
  val DarkRed = Color("#8B0000")

  var startTime: Option[Double] = None
  var stopTime: Option[Double] = None

  def main() {
    transitionState(reset())
    dom.setInterval(() => jQuery("#stopwatch").text(elapsedTimeFormatted()), 15)
  }

  def transitionState(state: State) {
    dom.onkeypress = (e: KeyboardEvent) => transitionState(state.onKeyPress(e))
  }

  def start() = {
    startTime = Some(js.Date.now())
    State.Running
  }

  def reset(): State = {
    startTime = None
    stopTime = None
    setBgColor(DarkGreen)
    State.Initialized
  }

  def fail() = {
    setBgColor(DarkRed)
    stopTime = Some(js.Date.now())
    State.Completed
  }

  def setBgColor(color: Color) {
    dom.document.body.style.background = color
  }

  def elapsedTimeFormatted() = {
    val now = js.Date.now()
    val startTimeOrNow = startTime.getOrElse(now)
    val stopTimeOrNow: Double = stopTime.getOrElse(now)
    val elapsed = (stopTimeOrNow - startTimeOrNow).toLong
    val mins = (elapsed / (1000 * 60)) % 60
    val secs = (elapsed / 1000) % 60
    val millis = elapsed % 1000
    "%02d:%02d:%03d".format(mins, secs, millis)
  }

  abstract class State {
    def onKeyPress(e: KeyboardEvent): State
  }

  object State {

    val Initialized = new State {
      override def onKeyPress(e: KeyboardEvent): State = e.keyCode match {
        case _ => start()
      }
    }

    val Running = new State {
      override def onKeyPress(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.enter => reset()
        case _ => fail()
      }
    }

    val Completed = new State {
      override def onKeyPress(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.enter => reset()
        case _ => this
      }
    }

  }

}
