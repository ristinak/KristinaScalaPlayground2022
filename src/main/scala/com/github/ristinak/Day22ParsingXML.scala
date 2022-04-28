package com.github.ristinak

import scala.xml.{Node, XML}

object Day22ParsingXML extends App {
  val src = "src/resources/xml/books.xml"

  val xml = XML.loadFile(src) //so we load the whole structure into xml Elem data type - so root element

  println(xml)

  //in Scala 2 i can still make XML directly, - XML is a first class citizen
  val myOwnXMl = <book id="bk305">
    <title>Best Fruit Cocktails
    </title>
    <author>Doe, John
    </author></book>

  println(myOwnXMl)

  val books = xml \ "book" //i am saying get all book tags that are direct children of this xml element(catalog here)

  val firstBook = books.head
  println(firstBook)

  //we can get the contents of some attribute considering the fact that the atribute might not exist at all
  val id = firstBook.attribute("id").getOrElse("bk0").toString
  println(s"Our book id is $id")

  //get children tag text contents
  println((firstBook \ "author").text) //theoretically if we had multiple author tags we would get ALL the text from those tags
  println((firstBook \ "title").text)
  println((firstBook \ "genre").text)
  println((firstBook \ "price").text)

  def getBook(node: Node):BookUnit = {
    val id = node.attribute("id").getOrElse("bk0").toString
    val author = (node \ "author").text
    val title = (node \ "title").text
    val genre = (node \ "genre").text
    val price = (node \ "price").text.toDouble
    val publish_date = (node \ "publish_date").text
    val description = (node \ "description").text
    BookUnit(id, author, title, genre, price, publish_date, description)
  }

  val bookUnits = (for (book <- books) yield getBook(book)).toArray

  println(bookUnits.head)
  println(bookUnits.last)

  val genres = bookUnits.map(_.genre).distinct
  println(genres.mkString(","))

  //TODO find the 5 most expensive books
  println(s"Five most expensive books are:\n${bookUnits.sortBy(_.price).reverse.take(5).mkString("\n")}")

  //TODO find the 5 cheapest books
  println(s"Five cheapest books are:\n${bookUnits.sortBy(_.price).take(5).mkString("\n")}")

  //TODO find all romance books
  val romanceBookUnits = bookUnits.filter(_.genre == "Romance")
  println(s"Romance books:\n${romanceBookUnits.mkString("\n")}")

  //TODO get all distinct authors in alphabetical order
  val authors = bookUnits.map(_.author).distinct
  println(s"Authors in alphabetical order:\n${authors.sorted.mkString("; ")}")
}

