package uk.co.todddavies.app.models

import java.sql.ResultSet


/**
 * Represents a row in the `notes` table of the database
 */
class NoteCollection(val name: String, val filename: String,
                     val courseCode: String, val standard: Boolean,
                     val kindle: Boolean, val flashcards: Boolean,
                     val tags: List[String],
                     val downloadDirectory: String = "./downloads/notes/") {

  /**
   * The same as the default constructor, except the tags are parsed into a List
   * @param tags The comma delimited list of tags to be parsed
   */
  def this(name: String, filename: String, courseCode: String,
           standard: Boolean, kindle: Boolean, flashcards: Boolean,
           tags: String) = {
    this(name, filename, courseCode, standard, kindle, flashcards, tags.split(",").toList.map(_.trim))
  }

  /**
   * Parses a results set into a NoteCollection
   */
  def this(resultsSet: ResultSet) = {
    this(resultsSet.getString("name"),
         resultsSet.getString("filename"),
         resultsSet.getString("coursecode"),
         resultsSet.getBoolean("standard"),
         resultsSet.getBoolean("kindle"),
         resultsSet.getBoolean("flashcards"),
         resultsSet.getString("tags"))
  }

  /**
   * The path to the notes file
   * @return Either the path or a ResourceNotPresentException if this set of notes doesn't contain the resource
   */
  def getResourcePath: Either[String, ResourceNotPresentException] = constructPathIfAvailable(standard, ".pdf")

  /**
   * The path to the kindle version of the notes file
   * @return Either the path or a ResourceNotPresentException if this set of notes doesn't contain the resource
   */
  def getKindleResourcePath: Either[String, ResourceNotPresentException] = constructPathIfAvailable(kindle, "_kindle.pdf")

  /**
   * The path to the flashcards version of the notes
   * @return Either the path or a ResourceNotPresentException if this set of notes doesn't contain the resource
   */
  def getFlashcardsResourcePath: Either[String, ResourceNotPresentException] = constructPathIfAvailable(flashcards, "_flashcards.pdf")

  /**
   * Returns the path to the resource if it is available
   * @param present is the resource present?
   * @param fileEnding the string that the file path ends with
   * @return Either the path or a ResourceNotPresentException
   */
  private def constructPathIfAvailable(present: Boolean, fileEnding: String): Either[String, ResourceNotPresentException] = present match {
    case true =>  Left(downloadDirectory + filename + fileEnding)
    case false => Right(new ResourceNotPresentException())
  }

  class ResourceNotPresentException extends Exception
}

object NoteCollection extends Parsable[List[NoteCollection]] {
  implicit def combinator(r: ResultSet, l: List[NoteCollection]): List[NoteCollection] = l :+ new NoteCollection(r)
  implicit val start = List()
}
