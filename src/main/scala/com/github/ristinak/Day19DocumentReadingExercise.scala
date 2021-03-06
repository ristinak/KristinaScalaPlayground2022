package com.github.ristinak

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.io.Source

case class Document(title: String = "", author: String = "", url: String = "", rows: Array[String] = Array[String]()) {
  val rowCount: Int = rows.length
  val wordCountsInRows: Array[Int] = rows.map(_.length)
  val wordCount: Int = wordCountsInRows.sum

  // returns the timestamp of the current moment, e.g., 2022_04_18_21_48
  def getTimeStamp: String = {
    val now = LocalDateTime.now()
    val formattedTimeStamp = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm").format(now)
    formattedTimeStamp
  }

  // returns the original lines with 3 additional lines in the beginning
  def appendLines: Array[String] = {
    val firstLine:String = s"URL: $url"
    val secondLine:String = s"Author: $author"
    val thirdLine:String = s"Title: $title"
    val newLines: Array[String] = Array(firstLine, secondLine, thirdLine, "\n\n\n") ++ rows
    newLines
  }
  //saves lines (an Array of String) to a file after creating a custom file name
  def save(dst: String = "", folder: String = "src/resources/texts"): String = {
    val newLines = appendLines
    val dstPath: String = {
      if (dst == "") folder + "/" + author.slice(0,10) + "_" + title.slice(0,15) + "_" + getTimeStamp + ".txt"
      else folder + "/" + dst + "_" + getTimeStamp + ".txt"
    }
    Util.saveLines(dstPath, newLines)
    dstPath
  }
}

object Day19DocumentReadingExercise {

  //returns an Array of lines from Web
  def getLinesFromWeb(url: String): Array[String] = {
    val source = Source.fromURL(url)
    val html = source.getLines().toArray
    source.close()
    html
  }

  //returns an array of Document class objects
  def getDocumentsFromUrls(urls: Array[String]): Array[Document] = {
    val documentArray = for (url <- urls) yield {
      val rows = getLinesFromWeb(url)
      //      val title = if (url.endsWith(".txt")) GutenbergUtil.getTitle(rows) else {url.split("/").last}
      // the above doesn't work if there is a colon (:) - then the .txt file is not named properly
      val title = if (url.endsWith(".txt")) GutenbergUtil.getTitle(rows) else {
        url.split("\\p{Punct}").last //takes the last "word" in a url
        //I'd rather scrape the html code and get the <title> tag
      }
      val author = if (url.endsWith(".txt")) GutenbergUtil.getAuthor(rows) else {
        url.split("/")(2) //takes the first "word" after http://
      }
      Document(title, author, url, rows)
    }
    documentArray
  }

  def main(args: Array[String]): Unit = {
    val filePath = if (!args.isEmpty) args(0) else "src/resources/webPages.txt"
    val fileLines = Util.getLinesFromFile(filePath)
    val urlArray = for (line <- fileLines) yield {
      if (line.startsWith("http://") || line.startsWith("https://")) line
      else "https://" + line
    }
    val myDocumentArray = getDocumentsFromUrls(urlArray)
    for (doc <- myDocumentArray) {
      val dstPath = doc.save() //saves the file and returns the destination path as String
      println(s"Just saved ${doc.title} by ${doc.author} from ${doc.url} to file $dstPath")
      Thread.sleep(200)
    }
  }
}

