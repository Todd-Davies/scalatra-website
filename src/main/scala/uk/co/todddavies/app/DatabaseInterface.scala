package uk.co.todddavies.app

import java.sql.{Connection, ResultSet}
import uk.co.todddavies.app.models.{Parsable, Download, NoteCollection}

trait DatabaseInterface {
  private val TABLE_NOTES = "notes"
  private val TABLE_DOWNLOADS = "downloads"

  def fetchNotes(implicit db: Connection): List[NoteCollection] = fetch("SELECT * from " + TABLE_NOTES, NoteCollection)

  def fetchDownloads(implicit db: Connection): List[Download] = fetch("SELECT * from " + TABLE_DOWNLOADS, Download)

  def fetch[T](query: String, p: Parsable[T])(implicit db: Connection): T = parseQuery(db.createStatement.executeQuery(query))(p.combinator, p.start)

  def parseQuery[T](resultsSet: ResultSet)(implicit f: (ResultSet, T) => T, result: T): T = resultsSet.next() match {
    case true  => parseQuery(resultsSet)(f, f(resultsSet, result))
    case false =>
      resultsSet.close()
      result
  }
}