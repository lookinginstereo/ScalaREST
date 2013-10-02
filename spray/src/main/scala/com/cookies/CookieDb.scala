package com.cookies

import spray.httpx.unmarshalling.pimpHttpEntity
import spray.json.DefaultJsonProtocol
import spray.httpx.marshalling._
import spray.http._
import HttpCharsets._
import MediaTypes._


object CookieDb {
  case class Cookie(id: Int, name: String)

  object MyCookieJsonProtocol extends DefaultJsonProtocol {
    implicit val CookieFormat = jsonFormat2(Cookie)
  }

  var allCookies = collection.mutable.ArrayBuffer.empty[Cookie]
  private var idCounter = 0

  def addCookie(cookie:Cookie) = {
    allCookies += Cookie(idCounter, cookie.name)
    idCounter += 1
  }

  def removeCookie(id: Int): Option[Cookie] = {
    try {
      Some(allCookies.remove(allCookies.indexWhere(_.id == id)))
    } catch {
      case _ : Throwable => None
    }
  }

  //Store some cookies in the "DB"
  addCookie(Cookie(0, "Chocolate Chip"))
  addCookie(Cookie(1, "Sugar"))
  addCookie(Cookie(2, "Oatmeal"))

}

