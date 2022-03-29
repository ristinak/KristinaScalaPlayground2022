import scala.collection.mutable

class Animal(val name:String,
             val animalType:String,
             var likes:scala.collection.mutable.Set[String],
             // a set of likes, which may change with age
             val sound:String,
             var age:Int) {     // age changes with time

  def makeSound(sound:String): Unit = {
    println(sound)
  }

  def meet(contact:String): Unit = {
    if (likes(contact)) {
      println("Hello there, I like that \u263a \n")
    }
    else println("Nope. Byeeeee.\n")
  }

  def addLike(newLike:String, likes:scala.collection.mutable.Set[String]): mutable.Set[String] = {
    likes += newLike
    likes
  }

}

object Day12ExerciseAnimals extends App{

  val myCat = new Animal("Autumn", "cat", mutable.HashSet("pets", "food", "snacks", "cuddles"), "meow", 12)
  val brothersDog = new Animal("Mūza", "dog", mutable.HashSet("food", "walks", "play"), "woof", 4)
  val streetCat = new Animal("Lialia", "cat", mutable.HashSet("attention", "pets"), "keke", 16)

  println("The animals have gathered:")
  myCat.makeSound(myCat.sound)
  brothersDog.makeSound(brothersDog.sound)
  streetCat.makeSound(streetCat.sound)

  var contact = "pets"
  println(s"I will give $contact to myCat:")
  myCat.meet(contact)
  println(s"I will give $contact to brothersDog:")
  brothersDog.meet(contact)
  println(s"I will give $contact to streetCat:")
  streetCat.meet(contact)

  contact = "food"
  println(s"I will give $contact to myCat:")
  myCat.meet(contact)
  println(s"I will give $contact to brothersDog:")
  brothersDog.meet(contact)
  println(s"I will give $contact to streetCat:")
  streetCat.meet(contact)

  contact = "sleep"
  println(s"I will give $contact to myCat:")
  myCat.meet(contact)

  myCat.addLike("sleep", myCat.likes)
  contact = "sleep"
  println(s"I will give $contact to myCat:")
  myCat.meet(contact)
}
