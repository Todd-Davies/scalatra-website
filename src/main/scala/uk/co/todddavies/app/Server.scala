package uk.co.todddavies.app


import java.sql.{SQLException, ResultSet, Connection}
import uk.co.todddavies.app.models.NoteCollection


case class Server(db: Connection) extends TodddaviesStack with DownloadServer {

  get("/") {
    contentType="text/html"
    ssp("home.ssp")
  }

  get("/notes") {
    contentType="text/html"
    val resultsSet = db.createStatement().executeQuery("SELECT * from notes")
    ssp("notes.ssp", "notes" -> parseQuery(resultsSet))
  }

  get("/notes/:tag") {
    contentType="text/html"
    val resultsSet = db.createStatement().executeQuery("SELECT * from notes")
    val notes: List[NoteCollection] = parseQuery(resultsSet)
    ssp("notes.ssp", "notes" -> notes.filter(
      x => x.tags.contains(params("tag").toLowerCase)
    ))
  }

  def parseQuery(resultsSet: ResultSet, list: List[NoteCollection] = List()): List[NoteCollection] = resultsSet.next() match {
    case true  => parseQuery(resultsSet, list :+ new NoteCollection(resultsSet))
    case false => list
  }

  get("/test") {
    val out = new StringBuilder()
    try {
      // create the statement, and run the select query
      val statement = db.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM downloads")
      while ( resultSet.next() ) {
        val name = resultSet.getString("name")
        val count = resultSet.getInt("count")
        out.append("name, count = " + name + ", " + count)
      }
    } catch {
      case e: SQLException => e.printStackTrace
    }
    out.toString()
  }

  get("/downloads/cv.pdf") {
    serveFile("/home/td/cv/cv.pdf", db)
  }
}
