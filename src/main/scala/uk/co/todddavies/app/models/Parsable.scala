package uk.co.todddavies.app.models

import java.sql.ResultSet

/**
 * Defines an interface through which a ResultSet can be parsed
 * @tparam ReturnType The type that will be returned after parsing.
 */
trait Parsable[ReturnType] {
  implicit def combinator(r: ResultSet, start: ReturnType): ReturnType
  implicit val start: ReturnType
}
