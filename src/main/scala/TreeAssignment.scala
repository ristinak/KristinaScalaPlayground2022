import scala.io.StdIn.readLine

object TreesAssignment extends App {
  //TODO ask person's name
  //TODO ask for tree height
  //TODO print a xmas tree (or another tree) by calling printTree function with the correct parameters
  //tree height should be the one assigned
  //simple version for height 3 would be
  //  *
  // ***
  //*****

  //for full points I would like to see the following
  //if user enters name Valdis  and height 9
  //then we should print
  //        *
  //       VVV
  //      AAAAA
  //     LLLLLLL
  //    DDDDDDDDD
  //   IIIIIIIIIII
  //  SSSSSSSSSSSSS
  // VVVVVVVVVVVVVVV
  //AAAAAAAAAAAAAAAAA

  //let's consider maximum height 40 (so person's name letters could repeat many times)

  def printTree(name:String = "Kristina", height:Int = 10, symbol:Char='*', maximumHeight:Int=40):Unit = {
    //for this exercise we are not going to adjust maximumHeight
    //TODO print the tree HINT: check the last entry in Day7Strings

    val name = (symbol + readLine("What is your name?\n")*maximumHeight).toUpperCase // a cheap way out :)
    val height = readLine(s"What is your tree height (between 1 and $maximumHeight?)\n").toInt

    for (i <- 0 until height) {
      println(" "*(height-(i+1)) + (name(i)).toString*(i*2+1))
    }
  }
  printTree()
}
