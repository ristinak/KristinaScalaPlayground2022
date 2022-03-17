object Day7StringExercise extends App {
  //TODO
  def processString(text:String, uppercaseChars:String ="", needsTrim:Boolean=false):String = {
    //TODO first trim string if it needs trimming from the argument
    //TODO replace All characters in uppercaseChars with their uppercase versions
    //you will need to write a loop
    //you will probably want to use var to store a temporary string that you keep rewriting
    //return newly created string

    val ourText = if (needsTrim) text.trim else text
    var newText = ourText
    for (upperC <- uppercaseChars) {
      if (ourText.contains(upperC)) {
          newText = newText.replace(upperC, upperC.toUpper)
        }
      }
    newText
  }

  println(processString("abracadabra", "cr")) //should print abRaCadabRa
  println(processString("   abracadabra  ", "cr", needsTrim = true)) //should print abRaCadabRa
}