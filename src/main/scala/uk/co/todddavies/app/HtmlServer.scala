package uk.co.todddavies.app

trait HtmlServer extends TodddaviesStack {

  def servePage(template: String, attributes: (String, Any)*): String =
  {
    contentType="text/html"
    ssp(template, attributes:_*)
  }

}
