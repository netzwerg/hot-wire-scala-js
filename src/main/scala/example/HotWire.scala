package example

import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.extensions.{Color, KeyCode}

import scala.scalajs.js
import org.scalajs.dom

object HotWire extends js.JSApp {

  val DarkGreen = Color("#006400")
  val DarkRed = Color("#8B0000")

  def main() {
    init()
    dom.onkeypress = {(e: KeyboardEvent) =>
      if (e.keyCode == KeyCode.enter) {
        reset()
      } else {
        fail()
      }
    }
  }

  def init() {
    reset()
  }

  def reset() {
    setBgColor(DarkGreen)
  }

  def fail() {
    setBgColor(DarkRed)
  }

  def setBgColor(color: Color) {
    dom.document.body.style.background = color
  }

}