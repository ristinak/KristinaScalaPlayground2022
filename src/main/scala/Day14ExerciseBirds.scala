trait FlightTrait {
  def fly(): Unit = println("I'm flying!")
}

trait RunningTrait {
  def run(): Unit
}

trait SwimmingTrait {
  def swim(): Unit
}
class Bird

class Penguin extends Bird with RunningTrait with SwimmingTrait {
  def run(): Unit = println("I'm running now!")
  def swim(): Unit = println("I'm swimming now!")
}

class Chicken extends Bird with FlightTrait with RunningTrait {
  override def fly(): Unit = println("I'm flying!.. But just barely...")
  def run(): Unit = println("I'm running!")
}

object Day14ExerciseBirds extends App {
  println("Let's create some birds using traits!")

  val p1 = new Penguin
  val p2 = new Penguin

  p1.swim()
  p2.run()

  val ch1 = new Chicken
  val ch2 = new Chicken

  ch1.run()
  ch2.fly()



}
