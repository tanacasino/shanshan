package controllers

import play.api.mvc._
import play.api.Logger
import is.tagomor.woothee.Classifier

object Home extends Controller {

  def index = Action { implicit request =>
    val userAgent = request.headers.get("User-Agent").getOrElse("None")
    val ipAddress = request.headers.get("X-Forwarded-For") match {
      case Some(x) => x
      case None => request.remoteAddress
    }
    val proto = request.headers.get("X-Forwarded-Proto") match {
      case Some(s) => s
      case None => "http"
    }
    val map: java.util.Map[String, String] = Classifier.parse(userAgent)

    val browser = Option(map.get("name")) match {
      case Some(name) => name
      case None => "unknown"
    }
    val browserVersion = Option(map.get("version")) match {
      case Some(name) => name
      case None => "unknown"
    }
    val category = Option(map.get("category")) match {
      case Some(name) => name
      case None => "unknown"
    }
    val os = Option(map.get("os")) match {
      case Some(name) => name
      case None => "unknown"
    }

    val env = Environment(UserAgent(userAgent, browser, browserVersion, os, category),
                          ipAddress, proto, request.headers.toString)
    Ok(views.html.home(env))
  }

}

case class Environment(userAgent: UserAgent, ipAddress: String, proto: String, headers: String)
case class UserAgent(userAgent: String, browser: String, version: String, os: String, category: String)