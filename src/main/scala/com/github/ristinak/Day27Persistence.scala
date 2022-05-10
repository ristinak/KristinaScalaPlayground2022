package com.github.ristinak

import java.nio.file.{Files, Paths}
import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneOffset, ZonedDateTime}
import java.util.Calendar

object Day27Persistence {
  //we could have also had the saveGameResult method made for Nim class which would be a fine choice
  //TODO move(refactor) saveGameResult method into Nim class
  //this means that winner and loser will not be needed as parameters

  def saveGameResult(dst:String, winner:String, loser:String) = {
    if (! Files.exists(Paths.get(dst))) {
      println("Saving header since no file exists")
      val header = "winner, loser, date" //we do not add \n since append will add \n
      Util.saveText(dst, header)
    } else {
      println(s"Need to save winner $winner and loser $loser")
      //TODO save above and also with the date
      //https://alvinalexander.com/scala/scala-get-current-date-time-hour-calendar-example/
      val now = Calendar.getInstance().getTime()
      println(s"Today is $now")
      val utcNow = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
      //      println(LocalDateTime.now().format( DateTimeFormatter.ISO_INSTANT ))
      //TODO get local time in ISO 8601 format
      val row = s"$winner, $loser, $utcNow"
      Util.saveText(dst, row, true) //crucial that we use append flag so we do not accidentally overwrite..
      //TODO explore logging solutions such as infamous log4j which make saving similar date more structured
    }

    //TODO
    //create a method saveGameScore(folder:String = "src/resources/nim", prefix:String = "game", suffix = ".csv"):Unit in Nim class that saves the actual game score as a single game
    //file should be saved as game_year_month_day_hour_min_second.csv in src/resources/nim
    //some ideas on how to get data info:
    //https://alvinalexander.com/scala/scala-get-current-date-time-hour-calendar-example/
    //format of the score should look like the following
    //row 1header will be player, move
    //row2 would be Alice, 1
    //row3 could be Computer, 2
    //row4 then Alice, 1
    //no need to declare winner as we know the last player to move loses

  }
}
