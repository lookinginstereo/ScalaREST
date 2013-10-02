package com.cookies

import akka.actor._

import spray.routing._
import spray.util.SprayActorLogging

import spray.httpx.unmarshalling._
import spray.httpx.marshalling._
import spray.httpx._
import net.liftweb.json.DefaultFormats
import com.cookies.CookieDb._
import spray.httpx.SprayJsonSupport._
import CookieDb.MyCookieJsonProtocol._
import spray.http.{StatusCodes, StatusCode}

class MyServiceActor extends HttpServiceActor
  with SprayActorLogging
  with SprayJsonSupport {

  //set up the the child actor and return an ActorRef that you can
  //send messages to
  val myCookieActor = context.actorOf(Props[CookieActor],"cookie-actor")
  //This imports the case classes for messages that are handled by their respective
  //actors
  import CookieActor._
  //These imports handle spray-json

  def receive = runRoute {
    //explicitly ping a particular session
    path( "rest" / "cookie" ) {
      get { ctx =>
        myCookieActor ! GetCookies(ctx.responder)
      } ~
      post {
        entity(as[Cookie]) { cookie =>
          complete {
            myCookieActor ! AddCookieIgnore(cookie)
            cookie
          }
        }
      }
    } ~
    //ping a particular session based on the path
    path("rest" / "cookie" / IntNumber ){ cookieId : Int =>
      get { ctx =>
        myCookieActor ! GetCookie(ctx.responder, cookieId)
      } ~
      delete { ctx =>
        myCookieActor ! EatCookie(ctx.responder, cookieId)
      }
    }
  }
}


