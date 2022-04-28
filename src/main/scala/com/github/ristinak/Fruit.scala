package com.github.ristinak

case class Fruit(genus: String,
                 name: String,
                 id: Int,
                 family: String,
                 order: String,
                 //i am flattening the nutritions
                 carbohydrates: Double = 0.0,
                 protein: Double = 0.0,
                 fat: Double = 0.0,
                 calories: Double = 0.0,
                 sugar: Double = 0.0
                ) {
  val nonSugarCarbs: Double = carbohydrates - sugar

  def prettyPrint():Unit = {

    println(s"Fruit genus: $genus, name: $name, family: $family, order: $order" +
     s"carbs: $carbohydrates, sugar: $sugar, protein: $protein, fat: $fat, calories: $calories")
  }
}
