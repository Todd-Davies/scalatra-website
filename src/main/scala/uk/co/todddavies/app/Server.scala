package uk.co.todddavies.app


import java.sql.{SQLException, ResultSet, Connection}
import uk.co.todddavies.app.models.NoteCollection


case class Server(db: Connection) extends TodddaviesStack with DownloadServer with HtmlServer with DatabaseInterface {

  get("/") {
    servePage("home.ssp")
  }

  get("/notes") {
    val resultsSet = db.createStatement().executeQuery("SELECT * from notes")
    servePage("notes.ssp", "notes" -> parseQuery(resultsSet, NoteCollection.combinator, List()))
  }

  get("/notes/:tag") {
    val resultsSet = db.createStatement().executeQuery("SELECT * from notes")
    val notes: List[NoteCollection] = parseQuery(resultsSet, NoteCollection.combinator, List())
    servePage("notes.ssp", "notes" -> notes.filter(
      x => x.tags.contains(params("tag").toLowerCase)
    ))
  }

  get("/test") {
    val resultSet = db.createStatement().executeQuery("SELECT * FROM downloads")
    def combinator(r: ResultSet, s: StringBuilder): StringBuilder = {
      val name = r.getString("name")
      val count = r.getInt("count")
      s.append("name, count = " + name + ", " + count)
    }
    parseQuery(resultSet, combinator, new StringBuilder()).toString()
  }

  get("/downloads/cv.pdf") {
    serveFile("/home/td/cv/cv.pdf", db)
  }
}
