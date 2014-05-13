package controllers

import play.api.mvc._
import play.api.Logger

object Home extends Controller {

  def index = Action { implicit request =>
    val userAgent = request.headers.get("User-Agent")
    val ipAddress = request.headers.get("X-Forwarded-For") match {
      case Some(x) => x
      case None => request.remoteAddress
    }
    val env = Environment(userAgent.getOrElse("None"), ipAddress, request.headers.toString)
    Ok(views.html.home(env))
  }

}

case class Environment(userAgent: String, ipAddress: String, headers: String)
