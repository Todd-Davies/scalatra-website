import java.sql.{Connection, DriverManager}
import uk.co.todddavies.app.Server
import org.scalatra._
import javax.servlet.ServletContext

import org.slf4j.LoggerFactory

class ScalatraBootstrap extends LifeCycle {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://127.0.0.1:3306/todddavies"
  val username = "root"
  val password = "root"

  Class.forName(driver)
  implicit val db: Connection = DriverManager.getConnection(url, username, password)

  val logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    context.mount(Server(), "/*")      // mount the application and provide the Database
  }

  private def closeDbConnection() {
    logger.info("Closing database connection")
    db.close()
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection()
  }
}
