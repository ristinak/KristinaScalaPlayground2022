object Day6CityPopulation extends App {
  //easy function
  //TODO write a function (name it yourself) to calculate Farenheit from Celsius
  //f = 32 + c*9/5
  def celciusToFarenheit(tempInCelcius: Double = 36.6): Double = {
    (32 + tempInCelcius*9/5)
  }

  println(s"36.6 degrees C is ${celciusToFarenheit()} degrees Farenheit.")
  println(s"37 degrees C is ${celciusToFarenheit(37)} degrees Farenheit.")
  println(s"48.5 degrees C is ${celciusToFarenheit(48.5)} degrees Farenheit.")

  //TODO 2nd main TASK - not really related to first task
  println("Function to calculate city growth")
  //TODO write a function getCityYear which takes the following 4 parameters
  //p0: Int - how many people are in beginning
  //perc: Int - yearly growth rate in percentages
  //delta: Int - how many people immigrate(+)/emigrate to the city each year
  //targetPopulation: Int - population we want to reach

  //we want to return the year city will reach targetPopulation
  //or we return -1 if the city will NEVER reach the population
  //it is a little bit tricky because we do not want to use return statements
  //we want to return the last line only
  //so you probably want to use some variable to hold the result
  //you will need a loop - while probably
  //you will need some if else statements
  //if we write comments /**
  //those are so called ScalaDoc style comments and they can be used by automatic tools to create documentation

  // */

  /**
   * getCityYear
   * @param p0 - original City population
   * @param percentage - yearly growth rate in percentages
   * @param delta - how many people immigrate(+)/emigrate to the city each year
   * @param targetPopulation - population we want to reach
   * @return number of years to reach , -1 if not reachable
   */
  def getCityYear(p0: Int, percentage: Double, delta: Int, targetPopulation: Int):Int = {

    var numberOfYears = 0
    if((0*percentage/100 > delta) & (targetPopulation > p0)) { // if the population is changing in the right direction

      var newPopulation = p0
      while (newPopulation < targetPopulation) {
        newPopulation = (p0 + p0*percentage/100 + delta).toInt
        numberOfYears += 1
      }
  } else numberOfYears = -1

    numberOfYears

  println(getCityYear(1000,2,50,1200)) // should print 3
  println(getCityYear(1000,2,-50,1200)) // should print -1
  println(getCityYear(1500000,2.5,10000,2000000)) // should print 10
}
}