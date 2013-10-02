package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object Application extends Controller {
  import CookieDb.Cookie

  def getCookies = Action {
    val cookiesJson = CookieDb.allCookies.map(Json.toJson(_)).toSeq
    val json = Json.obj("cookies" -> cookiesJson)
    Ok(json).withHeaders(CONTENT_TYPE -> "application/json")
  }

  def getCookie(id: Int) = Action {
    CookieDb.allCookies.find(_.id == id) match {
      case Some(cookie) =>
        val cookieJson = Json.toJson(cookie)
        Ok(cookieJson).withHeaders(CONTENT_TYPE -> "application/json")
      case None =>
        NotFound
    }
  }

  //{'id':3,'name':'peanut butter'}
  def addCookie = Action { implicit request =>
    val maybeCookie = request.body.asJson.flatMap(Json.fromJson[Cookie](_).asOpt)
    maybeCookie match {
      case Some(cookie) =>
        CookieDb.addCookie(cookie)
        Ok
      case _ =>
       InternalServerError
    }
  }
  def removeCookie(id: Int) = Action {
    CookieDb.removeCookie(id) match {
      case None => NotFound
      case _ => Ok
    }
  }

}
