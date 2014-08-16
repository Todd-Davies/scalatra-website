package uk.co.todddavies.app

import org.scalatra._
import scalate.ScalateSupport

class server extends TodddaviesStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/cv.pdf") {
    contentType = "application/octet-stream"
    val file = new java.io.File("/home/td/cv/cv.pdf")
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName)
    file
  }
  
}
