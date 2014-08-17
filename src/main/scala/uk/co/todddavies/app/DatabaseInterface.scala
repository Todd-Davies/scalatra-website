package uk.co.todddavies.app

import java.sql.{Connection, ResultSet}
import uk.co.todddavies.app.models.{Parsable, Download, NoteCollection}

/**
 * Provides an interface to the mysql database
 */
trait DatabaseInterface {
  // Define the names of the tables
  private val TABLE_NOTES = "notes"
  private val TABLE_DOWNLOADS = "downloads"

  /**
   * Fetch all of the notes in the database
   * @return A List[NoteCollection] of the notes in the database
   */
  def fetchNotes(implicit db: Connection): List[NoteCollection] = fetchAndParse("SELECT * from " + TABLE_NOTES, NoteCollection)

  /**
   * Fetch all of the downloads in the database
   * @return A List[Download] of the downloads in the database
   */
  def fetchDownloads(implicit db: Connection): List[Download] = fetchAndParse("SELECT * from " + TABLE_DOWNLOADS, Download)

  /**
   * A higher order, generic function that executes a SQL query and parses it
   * @param query The query to execute
   * @param parsable The parsable object that the query will be transformed into
   * @param db The database to query
   * @tparam ReturnType The type that the query will be transformed into
   * @return An instance of whatever the ReturnType was
   */
  def fetchAndParse[ReturnType](query: String, parsable: Parsable[ReturnType])(implicit db: Connection): ReturnType =
    parseQuery(db.createStatement.executeQuery(query))(parsable.combinator, parsable.start)

  /**
   * Parses a ResultSet from a database query
   * @param resultsSet The ResultSet to parse
   * @param combinator A function to parse each row in the ResultSet
   * @param startValue The starting value to pass into the combinator function (similar to fold)
   * @tparam ReturnType What you want parseQuery to return in the end
   * @return An instance of ReturnType, produced by the combinator function
   */
  def parseQuery[ReturnType](resultsSet: ResultSet)(implicit combinator: (ResultSet, ReturnType) => ReturnType, startValue: ReturnType): ReturnType = resultsSet.next() match {
    case true  => parseQuery(resultsSet)(combinator, combinator(resultsSet, startValue))
    case false =>
      resultsSet.close()
      startValue
  }
}