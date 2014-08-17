package uk.co.todddavies.app

/**
 * Provides convenience methods for serving static (including templates) HTML
 */
trait HtmlServer extends ToddDaviesStack {

  /**
   * Extends the default get command by letting you have trailing slashes on paths
   * This has the unfortunate side effect of working for files too (e.g. /downloads/cv.pdf/)
   * This is easily mitigated by using the ScalatraBase get though
   */
  def get(path: String)(action: => Any) = super.get(path + "/?")(action)

  /**
   * Convenience method for serving a web page
   * @param template The template to render
   * @param attributes See http://scalatra.org/2.3/guides/http/routes.html#toc_250
   * @return the html from the rendered template
   */
  def servePage(template: String, attributes: (String, Any)*): String = {
    contentType="text/html"
    ssp(template, attributes:_*)
  }

}
