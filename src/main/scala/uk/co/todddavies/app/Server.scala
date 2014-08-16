package uk.co.todddavies.app

import Tables._
import java.sql.DriverManager
import java.sql.Connection


case class Server(db: Connection) extends TodddaviesStack with DownloadServer {

  get("/") {
    contentType="text/html"
    ssp("/home.jade")
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
      case e => e.printStackTrace
    }
    out.toString()
  }

  get("/downloads/cv.pdf") {
    serveFile("/home/td/cv/cv.pdf", db)
  }
}
