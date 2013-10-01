package com.zensey

import akka.actor._

import spray.routing._
import spray.util.SprayActorLogging

class MyServiceActor extends HttpServiceActor with SprayActorLogging {

  //set up the the child actor and return an ActorRef that you can
  //send messages to
  val myToDoActor = context.actorOf(Props[ToDoActor],"todo-actor")
  //This imports the case classes for messages that are handled by their respective
  //actors
  import ToDoActor._

  def receive = runRoute {
    //explicitly ping a particular session
    path( "api" / "todo" ) {
      get { ctx =>
        myToDoActor ! GetToDos(ctx.responder)
      }
    }/* ~
    //ping a particular session based on the path
    path("ping" / Segment ){ session_cookie: String =>
      get { ctx =>
        mySessionActor ! PingWith(session_cookie, ctx.responder)
      }
    } ~
    cookie("ext_id") { cookie =>
      path(""){
        get { ctx =>
          mySessionActor ! ValidateSession(cookie, ctx.responder)
        }
      }
    } ~
    //get goals based on a username
    path("getUserGoals" / Segment ){ username: String =>
      get { ctx =>
        fetchGoalActor ! GetUserGoals(username, ctx.responder)
      }
    }*/
  }
}


