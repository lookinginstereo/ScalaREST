package com.zensey

import akka.actor._
import akka.pattern._
import akka.util.Timeout
import akka.io.IO

import spray.util.SprayActorLogging
import spray.http.{HttpCookie, HttpResponse, Uri, HttpMethods}

import scala.concurrent._
import ExecutionContext.Implicits.global


import java.util.concurrent.TimeUnit
import net.liftweb.json.DefaultFormats
import scala.collection.parallel.mutable

//This actor has a session actor and will use that session actor to
//fetch the session
class ToDoActor extends Actor with SprayActorLogging {
  import ToDoActor._

  //wanted by IO stuff
  implicit def system = context.system
  implicit val timeout = Timeout(120, TimeUnit.SECONDS)
  //lift-json support requirement
  import net.liftweb.json.JsonDSL._
  import net.liftweb.json._

  def receive = {
    case GetToDos(responder) =>
      val todos: List[ToDo] = allToDos.map(_._2).toList
      val json =
        ("todos" -> todos.map( t=> (
          ("id" -> t.id) ~
            ("title" -> t.title) ~
            ("completed" -> t.completed)

          ) ))
      responder ! HttpResponse(entity = compact(render(json)))
    case
  }

}
//companion object containing messages handled
object ToDoActor{
  case class CreateToDo(responder: ActorRef, todo: ToDo)
  case class UpdateToDo(responder: ActorRef, todo: ToDo)
  case class GetToDos(responder: ActorRef)
  case class DeleteToDo(responder: ActorRef, id: Int)

  case class ToDo(id: Int, title: String, completed: Boolean)

  //not thread safe
  private var allToDos: mutable.ParHashMap[Int, ToDo] = mutable.ParHashMap()
  allToDos += (0 -> ToDo(0, "test todo", false))
}

