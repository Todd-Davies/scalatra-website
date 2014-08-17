package uk.co.todddavies.app.models

import java.sql.ResultSet

/**
 * Represents a row in the `downloads` table in the database.
 * @param name The name of the file
 * @param count The number of times the file has been downloaded
 */
class Download(val name: String, val count: Int) {

  /**
   * Parses a results set into a Download
   * @param resultSet the results set to parse
   */
  def this(resultSet: ResultSet) = this(resultSet.getString("name"), resultSet.getInt("count"))

  override def toString: String = name + " -> " + count

}

object Download extends Parsable[List[Download]] {
  implicit def combinator(r: ResultSet, l: List[Download]): List[Download] = l :+ new Download(r)
  implicit val start = List()
}
