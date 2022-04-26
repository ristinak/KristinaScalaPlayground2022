package com.github.ristinak

case class Fruit(genus: String,
                 name: String,
                 id: Int,
                 family: String,
                 order: String,
                 //i am flattening the nutritions
                 carbohydrates: Double = 0.0
                 //TODO add the rest of fields from nutriotions
                )
