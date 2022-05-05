package com.github.ristinak

import java.sql.DriverManager
import java.sql.DriverManager
import scala.collection.mutable.ArrayBuffer

//TODO Create Album Case Class with appropriate data types for each field
case class Album(albumID: Int, Title:String, artistID: Int)

//TODO Create Track Case Class
case class Track(
                TrackID: Int,
                Name: String,
                AlbumID: Int,
                MediaTypeID: Int,
                GenreID: Int,
                Composer: String,
                Milliseconds: Int,
                Bytes: Int,
                UnitPrice: Double
                )

object Day25DBConnectionExercise extends App {

  //TODO connect to chinook and extract into Array of Album (using ArrayBuffer to build it up)

  val dbPath = "src/resources/db/chinook.db"
  val url =  s"jdbc:sqlite:$dbPath"

  val conn = DriverManager.getConnection(url) //TODO handle exceptions at connection time
  val statement = conn.createStatement() //we create a statement object that will handle sending SQL statements to the DB

  val sqlAlbum =
    """
      |SELECT * FROM albums;
      |""".stripMargin

  val resultSetAlbum = statement.executeQuery(sqlAlbum)
  val metaDataAlbum = resultSetAlbum.getMetaData

  val albumBuffer = ArrayBuffer[Album]() //so we start with an empty buffer to store our rows

  while (resultSetAlbum.next()) {
    val album = Album(
      resultSetAlbum.getInt("albumId"),
      resultSetAlbum.getString("Title"),
      resultSetAlbum.getInt("ArtistId"))
    albumBuffer += album
  }

  //TODO connect to database and extract into Array of Tracks
  val sqlTrack =
    """
      |SELECT * FROM tracks;
      |""".stripMargin

  val resultSetTrack = statement.executeQuery(sqlTrack)
  val metaDataTrack = resultSetTrack.getMetaData

  val trackBuffer = ArrayBuffer[Track]() //so we start with an empty buffer to store our rows

  while (resultSetTrack.next()) {
    val track = Track(
      resultSetTrack.getInt("TrackId"),
      resultSetTrack.getString("Name"),
      resultSetTrack.getInt("AlbumId"),
      resultSetTrack.getInt("MediaTypeId"),
      resultSetTrack.getInt("GenreId"),
      resultSetTrack.getString("Composer"),
      resultSetTrack.getInt("Milliseconds"),
      resultSetTrack.getInt("Bytes"),
      resultSetTrack.getDouble("UnitPrice")
                      )
    trackBuffer += track
  }

  conn.close()

  val albumCollection = albumBuffer.toArray
  albumCollection.take(10).foreach(println)

  val trackCollection = trackBuffer.toArray
  trackCollection.take(10).foreach(println)


    //Extra Challenge
  //TODO save all Tracks into CSV - in src/resources/csv/tracks.csv -
  //results should be very similar or identical to what you get in DBeaver export CSV - tracks_exported.csv
  //Check Day 20 examples on how we did this

  val dst = "src/resources/csv/tracks.csv"
//  val trackLines = trackCollection.map(_.toString)

  val trackLines1 = for (tr <- trackCollection) yield {
    tr.TrackID.toString + "," +
      tr.Name + "," +
      tr.AlbumID.toString + "," +
      tr.MediaTypeID.toString + "," +
      tr. GenreID.toString + "," +
      tr.Composer + "," +
      tr.Milliseconds.toString + "," +
      tr.Bytes.toString + "," +
      tr.UnitPrice.toString
  }
    trackLines1.take(5).foreach(println)
    val header: Array[String] = Array("TrackId,Name,AlbumId,MediaTypeId,GenreId,Composer,Milliseconds,Bytes,UnitPrice")
    val csvContent = header ++ trackLines1

    Util.saveLines(dst, csvContent)

}
