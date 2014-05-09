package controllers

import play.api.mvc._

object Home extends Controller {

  def index = Action { implicit request =>
    val userAgent = request.headers.get("User-Agent")
    val remoteAddr = request.remoteAddress
    val env = Environment(userAgent.getOrElse("None"), remoteAddr)
    Ok(views.html.home(env))
  }

}

case class Environment(userAgent: String, remoteAddr: String)
