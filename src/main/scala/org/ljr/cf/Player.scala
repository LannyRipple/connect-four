package org.ljr.cf

sealed abstract class Player {
  def other: Player
}

object Player {

  object Empty extends Player {
    val other: Player = Empty
  }

  object Red extends Player {
    val other: Player = Yellow
  }

  object Yellow extends Player {
    val other: Player = Red
  }
}

