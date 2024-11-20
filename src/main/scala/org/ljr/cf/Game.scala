package org.ljr.cf

sealed abstract class Game {
  def board: Board.Type
  def playersTurn: Player
}

case class Start(board: Board.Type, playersTurn: Player = Player.Yellow) extends Game

object Start {
  val empty: Start = Start(board = Vector.fill(Board.COLS, Board.ROWS)(Player.Empty))
}

case class Playing(board: Board.Type, playersTurn: Player) extends Game

case class Finished(board: Board.Type, playersTurn: Player = Player.Empty) extends Game
