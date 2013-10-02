package com.cookies

import akka.actor._
import akka.util.Timeout

import spray.util.SprayActorLogging
import spray.http.{StatusCodes, HttpResponse}

import scala.concurrent._
import ExecutionContext.Implicits.global


import java.util.concurrent.TimeUnit
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import com.cookies.CookieDb.Cookie

//This actor has a session actor and will use that session actor to
//fetch the session
class CookieActor extends Actor with SprayActorLogging {
  import CookieActor._

  //wanted by IO stuff
  implicit def system = context.system
  implicit val timeout = Timeout(120, TimeUnit.SECONDS)
  //lift-json support requirement
  implicit def liftJsonFormats = DefaultFormats

  def receive = {
    //Get all the cookies
    case GetCookies(responder) =>
      val cookies: List[JValue] = CookieDb.allCookies.map(Extraction.decompose(_)).toList
      val json = ("cookies" -> cookies)
      responder ! HttpResponse(entity = compact(render(json)))

    //Get a single cookie
    case GetCookie(responder, cookieId) =>
      val cookieMaybe = CookieDb.allCookies.find(_.id == cookieId)
      cookieMaybe match {
        case Some(cookie) =>
          val json: JValue = Extraction.decompose(cookie)
          responder ! HttpResponse(entity = compact(render(json)))
        case None =>
          responder ! HttpResponse(status = StatusCodes.NotFound)
      }

    //Add a cookie
    case AddCookie(responder, cookie) =>
      CookieDb.addCookie(cookie)
      responder ! HttpResponse //default 200
  }

}
//companion object containing messages handled
object CookieActor{
  case class AddCookie(responder: ActorRef, cookie: Cookie)
  case class GetCookies(responder: ActorRef)
  case class GetCookie(responder: ActorRef, id: Int)
  case class EatCookie(responder: ActorRef, id: Int)

}

