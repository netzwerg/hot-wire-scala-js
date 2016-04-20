package ch.netzwerg

import org.scalajs.dom
import org.scalajs.dom.{KeyboardEvent, document}
import org.scalajs.dom.ext._

import scala.scalajs.js
import scalatags.JsDom.all._

object HotWire extends js.JSApp {

  val StopwatchId = "stopwatch"

  val IdleColor = Color("#006400")
  val RunningColor = IdleColor
  val SuccessColor = Color("#66FF66")
  val FailColor = Color("#8B0000")

  def now = Some(js.Date.now())
  var startTime: Option[Double] = None

  var stopTime: Option[Double] = None

  def main(): Unit = {
    transitionTo(idle)
    val timeElement = div(id := StopwatchId).render
    document.body.appendChild(timeElement)
    scala.scalajs.js.timers.setInterval(15) {
      timeElement.textContent = elapsedTimeFormatted()
    }
  }

  def transitionTo(state: State) {
    dom.document.onkeydown = (e: KeyboardEvent) => {
      transitionTo(state.map(e))
    }
  }

  def idle: State = {
    startTime = None
    stopTime = None
    setBgColor(IdleColor)
    State.Idle
  }

  def start: State = {
    startTime = now
    setBgColor(RunningColor)
    State.Running
  }

  def fail = complete(FailColor)

  def succeed = complete(SuccessColor)

  def complete(color: Color) = {
    stopTime = now
    setBgColor(color)
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

    val Idle = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.Left => start
        case _ => this
      }
    }

    val Running = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.Right => succeed
        case KeyCode.Enter => idle
        case _ => fail
      }
    }

    val Completed = new State {
      override def map(e: KeyboardEvent): State = e.keyCode match {
        case KeyCode.Enter => idle
        case _ => this
      }
    }
  }

}
