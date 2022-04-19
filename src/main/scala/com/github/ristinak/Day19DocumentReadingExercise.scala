package com.github.ristinak

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

  // returns a file name in the format of author_title_timestamp.txt
  def getFileName: String = {
    author.slice(0,10) + "_" + title.slice(0,15) + "_" + getTimeStamp + ".txt"
  }
  // returns the original lines with 3 additional lines in the beginning
  def appendLines: Array[String] = {
    val firstLine:String = s"URL: $url"
    val secondLine:String = s"Author: $author"
    val thirdLine:String = s"Title: $title"
    val newLines: Array[String] = Array(firstLine, secondLine, thirdLine, "\n\n\n") ++ rows
    newLines
  }

  def save(dst: String = "", folder: String = "src/resources/texts"): Unit = {
    val newLines = appendLines
    val dstPath: String = if (dst == "") folder + "/" + getFileName else folder + "/" + dst
    Util.saveLines(dstPath, newLines)
  }
}

object Day19DocumentReadingExercise extends App {
  //TODO create a program that reads web addresses from a file and downloads multiple files with some changes
  //check for system arguments (see Day14commandArguments )
  //use first argument as filePath to process
  //otherwise default filePath will be src/resources/webPages.txt

  def getDocumentsFromUrls(urls:Array[String]):Array[Document] = {
    val documentArray = for (url <- urls) yield {
      val rows = Util.getLinesFromWeb(url)
      //      val title = if (url.endsWith(".txt")) GutenbergUtil.getTitle(rows) else {url.split("/").last}
      // the above doesn't work if there is a colon (:) - then the .txt file is not named properly
      val title = if (url.endsWith(".txt")) GutenbergUtil.getTitle(rows) else {url.split("\\p{Punct}").last}
      val author = if (url.endsWith(".txt")) GutenbergUtil.getAuthor(rows) else {url.split("/")(2)}
      Document(title, author, url, rows)
    }
    documentArray
}
  //  val filePath = if (args(0) != "") args(0) else "src/resources/webPages.txt"
  val filePath = "src/resources/webPages.txt"
  val fileLines = Util.getLinesFromFile(filePath)
  val urlArray = for (line <- fileLines) yield {
    if (line.startsWith("http://") || line.startsWith("https://")) line
    else "https://" + line
  }
  val myDocumentArray = getDocumentsFromUrls(urlArray)
  for (doc <- myDocumentArray) {
    val dstPath = doc.getFileName
    doc.save(dstPath)
    println(s"Just saved ${doc.title} by ${doc.author} from ${doc.url} to file $dstPath.")
    Thread.sleep(200)
  }

}