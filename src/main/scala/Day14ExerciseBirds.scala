//TODO create FlightTrait trait with fly method declaration (no need to define it, just declare it)
trait FlightTrait {
  def fly(): Unit = println("I'm flying!")
}
//TODO create RunningTrait with at least run method declaration
trait RunningTrait {
  def run(): Unit
}
//TODO create SwimmingTrait with swim method declaration inside
trait SwimmingTrait {
  def swim(): Unit
}
class Bird
//TODO create Penguin class extended with appropriate traits
class Penguin extends Bird with RunningTrait with SwimmingTrait {
  def run(): Unit = println("I'm running now!")
  def swim(): Unit = println("I'm swimming now!")
}
//TODO create Chicken class extended with appropriate traits
///optional create generic Bird class first and extend that
//There is no one True solution here
class Chicken extends Bird with FlightTrait with RunningTrait {
  override def fly(): Unit = println("I'm flying!.. But just barely...")
  def run(): Unit = println("I'm running!")
}

object Day14ExerciseBirds extends App {
  println("Let's create some birds using traits!")
  //TODO create two penguin objects
  val p1 = new Penguin
  val p2 = new Penguin
  //TODO make them swim
  p1.swim()
  p2.swim()
  //TODO create two chicken objects
  val ch1 = new Chicken
  val ch2 = new Chicken
  //TODO make the chickens run
  ch1.run()
  ch2.run()



}
