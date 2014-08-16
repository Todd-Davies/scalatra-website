package uk.co.todddavies.app

import java.io.File
import java.sql.Connection

trait DownloadServer extends TodddaviesStack {

  def serveFile(path: String, db: Connection): File =
  {
    contentType = "application/octet-stream"
    val file = new java.io.File(path)
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName)
    try {
      db.createStatement().executeUpdate("INSERT INTO downloads (name, count) VALUES (\"" + file.getName + "\", 1) ON DUPLICATE KEY UPDATE count=count + 1")
    } catch {
      case e => e.printStackTrace
    }
    file
  }

}
