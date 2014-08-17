package uk.co.todddavies.app.models

import java.sql.ResultSet


class NoteCollection(val name: String, val filename: String,
                     val courseCode: String, val standard: Boolean,
                     val kindle: Boolean, val flashcards: Boolean,
                     val tags: List[String],
                     val downloadDirectory: String = "./downloads/notes/") {

  def this(name: String, filename: String, courseCode: String,
           standard: Boolean, kindle: Boolean, flashcards: Boolean,
           tags: String) = {
    this(name, filename, courseCode, standard, kindle, flashcards, tags.split(",").toList)
  }

  def this(resultsSet: ResultSet) = {
    this(resultsSet.getString("name"),
         resultsSet.getString("filename"),
         resultsSet.getString("coursecode"),
         resultsSet.getBoolean("standard"),
         resultsSet.getBoolean("kindle"),
         resultsSet.getBoolean("flashcards"),
         resultsSet.getString("tags"))
  }

  def getResourcePath: String = downloadDirectory + filename + ".pdf"

  def getKindleResourcePath: String = downloadDirectory + filename + "_kindle.pdf"

  def getFlashcardsResourcePath: String = downloadDirectory + filename + "_flashcards.pdf"
}

object NoteCollection extends Parsable[List[NoteCollection]] {
  implicit def combinator(r: ResultSet, l: List[NoteCollection]): List[NoteCollection] = l :+ new NoteCollection(r)
  implicit val start = List()

  val parseDetails: ((ResultSet, List[NoteCollection]) => List[NoteCollection], List[NoteCollection]) = (combinator, start)
}
