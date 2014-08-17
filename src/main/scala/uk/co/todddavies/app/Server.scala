package uk.co.todddavies.app


import java.sql.{ResultSet, Connection}


case class Server(implicit db: Connection) extends DownloadServer with HtmlServer with DatabaseInterface {

  get("/") {
    servePage("home.ssp")
  }

  get("/notes") {
    val notes = fetchNotes
    servePage("notes.ssp", "notes" -> notes)
  }

  get("/notes/:tag") {
    servePage("notes.ssp", "notes" -> fetchNotes.filter(
      x => x.tags.contains(params("tag").toLowerCase)
    ))
  }

  get("/test") {
    fetchDownloads.map(_.toString()).mkString("\n")
  }

  getFile("/downloads/cv.pdf")
}
