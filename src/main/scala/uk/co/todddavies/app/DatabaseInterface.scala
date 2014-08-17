package uk.co.todddavies.app

import java.sql.ResultSet

trait DatabaseInterface {
  def parseQuery[T](resultsSet: ResultSet, f: (ResultSet, T) => T, result: T): T = resultsSet.next() match {
    case true  => parseQuery(resultsSet, f, f(resultsSet, result))
    case false => result
  }
}