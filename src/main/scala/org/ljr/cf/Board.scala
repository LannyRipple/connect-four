package org.ljr.cf

/**
 * A Board provides convenince methods over the board implementation
 *
 * In this implemntation the board is an Vector of Connect Four columns (i.e., column major).
 * Each column contains the player pieces (or Empty) at each row from lowest to highest.
 *
 * Return will indicate a Stalemate, a winner, or a continuing game.
 */
case class Board(board: Board.Type) {

  case object Stalemate

  def isFull: Boolean = board.forall(_.length == Board.ROWS)

  def checkForWinner(player: Player, col: Int, row: Int): Either[Stalemate.type, Option[Player]] = {
    require(player != Player.Empty, "Invalid Player.Empty")

    def hasVerticalWinner: Boolean =
      board(col).sliding(4).exists(ps => ps.length == 4 && ps.forall(_ == player))

    def hasHorzontalWinner: Boolean =
      (col - 3 to col + 3)
        .zip(List.fill(Board.COLS)(row))
        .filter(cr => cr._1 >= 0 && cr._1 <= Board.COLS)
        .map { case (c, r) => board(c)(r) }
        .sliding(4)
        .exists(ps => ps.length == 4 && ps.forall(_ == player))

    def checkDiagWinner(f: (Int, Int) => Int => (Int, Int)): Boolean =
      (-3 to 3)
        .map(f(col, row)(_))
        .filter { case (c, r) =>
          c >= 0 && c <= Board.COLS && r >= 0 && r <= Board.ROWS
        }
        .map { case (c, r) => board(c)(r) }
        .sliding(4)
        .exists(ps => ps.length == 4 && ps.forall(_ == player))

    def hasLeftDiagWinner: Boolean =
      checkDiagWinner((col, row) => oset => (col + oset, row - oset))

    def hasRightDiagWinner: Boolean =
      checkDiagWinner((col, row) => oset => (col + oset, row + oset))

    if (hasVerticalWinner || hasHorzontalWinner || hasLeftDiagWinner || hasRightDiagWinner)
      Right(Option(player))
    else if (isFull)
      Left(Stalemate)
    else
      Right(Option.empty[Player])
  }
}

case object Board {
  type Type = Vector[Vector[Player]]

  final val COLS = 7
  final val ROWS = 6
}
