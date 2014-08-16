package uk.co.todddavies.app

import scala.slick.driver.H2Driver.simple._

object Tables {

  class Downloads(tag: Tag) extends Table[(Int, String, Int)](tag, "DOWNLOADS") {
    def id      = column[Int]("ID", O.PrimaryKey) // This is the primary key column
    def name    = column[String]("NAME")
    def count  = column[Int]("COUNT")

    def * = (id, name, count)

  }
  val downloads = TableQuery[Downloads]
}
