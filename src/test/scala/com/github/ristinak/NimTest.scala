package com.github.ristinak

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite


class NimTest extends AnyFunSuite with BeforeAndAfter {
  //https://alvinalexander.com/scala/writing-tdd-unit-tests-with-scalatest/

  var nim: Nim = _
  var nimDB: NimDB = new NimDB("src/resources/nim/nim.db")

  before {
    //we put whatever  initialization we need for tests in this particular suite
    nim = new Nim("Valdis","COMPUTER")
  }

  test("Nim.clampMove") {
    assert(nim.clampMove(5,1,3) === 3)
    assert(nim.clampMove(0,1,3) === 1)
    assert(nim.clampMove(-1,1,3) === 1)
    assert(nim.clampMove(1,1,3) === 1)
    assert(nim.clampMove(2,1,3) === 2)
    assert(nim.clampMove(3,1,3) === 3)
  }


  //we should create a new class just for unit testing NimAI
  test("NimAI.getSmartStrategy") {

    assert(NimAI.getSmartStrategy(2) === 1)
    assert(NimAI.getSmartStrategy(3) === 2)
    assert(NimAI.getSmartStrategy(4) === 3)
  }

  //TODO write 3 more tests involving Nim class

  test("Nim.nextPlayer") {
    var isPlayerATurn = true

    assert(nim.nextPlayer() === "COMPUTER")
    assert(nim.nextPlayer() === "Valdis")
  }

  test("Nim.isGameActive") {
    assert(nim.isGameActive === true)
  }

  //TODO write a test testing database functionality - reading is one you can test

  test("NimDB.insertNewUser") {
    val name = "TotallyNewPlayer"
    assert(nimDB.insertNewUser(name) === 14) //there are 13 players in my users table
  }

  test("NimDB.getUserId") {
    val name = "Tina"
    assert(nimDB.getUserId(name) === 1)
  }
}
