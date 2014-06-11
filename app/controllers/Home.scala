package controllers

import play.api.mvc._
import models.Environment

object Home extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.home(Environment(request)))
  }

}

