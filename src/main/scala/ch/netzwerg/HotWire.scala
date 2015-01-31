package ch.netzwerg

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.extensions.{Color, KeyCode}
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

object HotWire extends js.JSApp {

  val DarkGreen = Color("#006400")
  val LightGreen = Color("#66FF66")
  val DarkRed = Color("#8B0000")

  def now = Some(js.Date.now())

  var startTime: Option[Double] = None
  var stopTime: Option[Double] = None

  def main() {
    transitionTo(reset)
    dom.setInterval(() => jQuery("#stopwatch").text(elapsedTimeFormatted()), 15)
  }

  def transitionTo(state: State) {
    dom.onkeydown = (e: KeyboardEvent) => {
      global.console.log("Key " + e.keyCode)
      transitionTo(state.map(e))
    }
  }

  def start = {
    startTime = now
    State.Running
  }

  def reset: State = {
    startTime = None
    stopTime = None
    setBgColor(DarkGreen)
    State.Initialized
  }

  def fail = complete(DarkRed)
  def succeed = complete(LightGreen)

  def complete(color: Color) = {
    setBgColor(color)
    stopTime = now
    State.Completed
  }

  def setBgColor(color: Color) {
    dom.document.body.style.background = color
  }

  def elapsedTimeFormatted() = {
    val nowValue = now.get
    val startTimeOrNow = startTime.getOrElse(nowValue)
    val stopTimeOrNow = stopTime.getOrElse(nowValue)
    val elapsed = (stopTimeOrNow - startTimeOrNow).toLong
    val mins = (elapsed / (1000 * 60)) % 60
    val secs = (elapsed / 1000) % 60
    val millis = elapsed % 1000
    "%02d:%02d:%03d".format(mins, secs, millis)
  }

  abstract class State {
    def map(e: KeyboardEvent): State
  }

  object State {

    val Initialized = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.left => start
        case _ => this
      }
    }

    val Running = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.right => succeed
        case KeyCode.enter => reset
        case _ => fail
      }
    }

    val Completed = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.enter => reset
        case _ => this
      }
    }
  }

}
