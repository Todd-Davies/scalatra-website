package uk.co.todddavies.app.models

import java.sql.ResultSet

class Download(val name: String, val count: Int) {

  def this(resultSet: ResultSet) = this(resultSet.getString("name"), resultSet.getInt("count"))

  override def toString: String = name + " -> " + count

}

object Download extends Parsable[List[Download]] {
  implicit def combinator(r: ResultSet, l: List[Download]): List[Download] = l :+ new Download(r)
  implicit val start = List()

  val parseDetails: ((ResultSet, List[Download]) => List[Download], List[Download]) = (combinator, start)
}
