object Day7StringExercise extends App {
  //TODO
  def processString(text:String, uppercaseChars:String ="", needsTrim:Boolean=false):String = {
    //TODO first trim string if it needs trimming from the argument
    //TODO replace All characters in uppercaseChars with their uppercase versions
    //you will need to write a loop
    //you will probably want to use var to store a temporary string that you keep rewriting
    //return newly created string


    var ourText = text
    if (needsTrim == true) {
      ourText = text.trim
    }

    for (upperC <- uppercaseChars) {
      for (c <- text) {
        if (c == upperC) {
          ourText.replace(c, upperC.toUpper)
        }
      }
    }
    ourText
  }

  println(processString("abracadabra", "cr")) //should print abRaCadabRa
  println(processString("   abracadabra  ", "cr", needsTrim = true)) //should print abRaCadabRa
}