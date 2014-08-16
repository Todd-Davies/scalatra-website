import java.sql.{Connection, DriverManager}
import uk.co.todddavies.app.Server
import org.scalatra._
import javax.servlet.ServletContext

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import scala.slick.driver.H2Driver.simple._

class ScalatraBootstrap extends LifeCycle {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://127.0.0.1:3306/downloads"
  val username = "root"
  val password = "think"

  Class.forName(driver)
  val db: Connection = DriverManager.getConnection(url, username, password)

  val logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    context.mount(Server(db), "/*")      // mount the application and provide the Database
  }

  private def closeDbConnection() {
    logger.info("Closing database connection")
    db.close()
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection
  }
}
