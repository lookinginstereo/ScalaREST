package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._

object CookieDb {
  case class Cookie(id: Int, name: String)
  object Cookie {
    //this is used to reading/writing json for play from the
    //cookie case class
    implicit val cookieWrites = Json.format[Cookie]
  }

  var allCookies = collection.mutable.ArrayBuffer.empty[Cookie]
  private var idCounter = 0

  def addCookie(cookie:Cookie) = {
    allCookies += Cookie(idCounter, cookie.name)
    idCounter += 1
  }

  def removeCookie(id: Int): Option[Cookie] = {
    try{
      Some(allCookies.remove(allCookies.indexWhere(_.id == id)))
    } catch {
      case _ =>
        None
    }
  }

  //Store some cookies in the "DB"
  addCookie(Cookie(0, "Chocolate Chip"))
  addCookie(Cookie(1, "Sugar"))
  addCookie(Cookie(2, "Oatmeal"))

}


