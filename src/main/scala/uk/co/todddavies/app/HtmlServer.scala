package uk.co.todddavies.app

import org.scalatra.Route

trait HtmlServer extends ToddDaviesStack {

  def get(path: String)(action: => Any) = super.get(path + "/?")(action)

  def servePage(template: String, attributes: (String, Any)*): String = {
    contentType="text/html"
    ssp(template, attributes:_*)
  }

}
