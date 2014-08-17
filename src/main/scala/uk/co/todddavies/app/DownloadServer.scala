package uk.co.todddavies.app

import java.io.File
import java.sql.{SQLException, Connection}

trait DownloadServer extends ToddDaviesStack {

  def getFile(path: String)(implicit db: Connection) = get(path) { serveFile("/home/td/cv/cv.pdf", db) }

  def serveFile(path: String, db: Connection): File = {
    contentType = "application/octet-stream"
    val file = new java.io.File(path)
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName)
    db.createStatement().executeUpdate("INSERT INTO downloads (name, count) VALUES (\"" + file.getName + "\", 1) ON DUPLICATE KEY UPDATE count=count + 1")
    file
  }

}
