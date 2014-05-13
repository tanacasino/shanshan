package controllers

import play.api.mvc._
import play.api.Logger

object Home extends Controller {

  def index = Action { implicit request =>
    val userAgent = request.headers.get("User-Agent")
    val remoteAddr = request.remoteAddress
    Logger.debug(s"X-Forwarded-For: ${request.headers.get("X-Forwarded-For")}")
    val xff = request.headers.get("X-Forwarded-For") match {
      case Some(x) => x
      case None => request.remoteAddress
    }
    Logger.debug(s"xff : $xff")
    val env = Environment(userAgent.getOrElse("None"), remoteAddr, request.headers.toString)
    Ok(views.html.home(env))
  }

}

case class Environment(userAgent: String, remoteAddr: String, headers: String)
