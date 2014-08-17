package uk.co.todddavies.app

import java.io.File
import java.sql.{SQLException, Connection}

/**
 * A helper class to implement file downloads
 */
trait DownloadServer extends ToddDaviesStack {

  /**
   * Convience method for service a file over HTTP
   * @param path The path to the resource
   * @param filepath The path to the file on the server
   */
  def getFile(path: String, filepath: String)(implicit db: Connection) = get(path) { serveFile(filepath, db) }

  /**
   * Sends a file to the user over HTTP
   * Updates the download count in the database as it goes
   * @param path The path to the file on the server
   * @param db The database to update the count in
   */
  def serveFile(path: String, db: Connection): File = {
    contentType = "application/octet-stream"
    val file = new java.io.File(path)
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName)
    db.createStatement().executeUpdate("INSERT INTO downloads (name, count) VALUES (\"" + file.getName + "\", 1) ON DUPLICATE KEY UPDATE count=count + 1")
    file
  }

}
