package uk.co.todddavies.app.models

import java.sql.ResultSet

trait Parsable[T] {
  implicit def combinator(r: ResultSet, start: T): T
  implicit val start: T

  val parseDetails: ((ResultSet, T) => T, T)
}
