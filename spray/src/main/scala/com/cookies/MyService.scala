package com.cookies

import akka.actor._

import spray.routing._
import spray.util.SprayActorLogging
import spray.httpx.encoding.NoEncoding
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling._
import com.cookies.CookieDb.Cookie

class MyServiceActor extends HttpServiceActor with SprayActorLogging {

  //set up the the child actor and return an ActorRef that you can
  //send messages to
  val myCookieActor = context.actorOf(Props[CookieActor],"cookie-actor")
  //This imports the case classes for messages that are handled by their respective
  //actors
  import CookieActor._

  def receive = runRoute {
    //explicitly ping a particular session
    path( "rest" / "cookie" ) {
      get { ctx =>
        myCookieActor ! GetCookies(ctx.responder)
      } ~
      post { ctx =>
        entity(as[Cookie]) { cookie =>
          complete {
            myCookieActor ! AddCookie(ctx.responder, cookie)
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

