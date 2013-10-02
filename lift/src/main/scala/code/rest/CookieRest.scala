package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http._
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import code.lib.CookieDb.Cookie
import net.liftweb.json.Extraction
import code.lib.CookieDb

object CookieRest extends RestHelper {

  def init = LiftRules.statelessDispatch.append(CookieRest)

  serve("rest" / "cookie" prefix {
    case Nil JsonGet _ => {
      val cookies = CookieDb.allCookies.map(Extraction.decompose(_))
      ("cookies" -> cookies):JValue
    }

    case Nil JsonPost json => {
      val cookieMaybe = Extraction.extractOpt[Cookie](json._1)
      cookieMaybe match {
        case Some(cookie) =>
          CookieDb.addCookie(cookie)
          OkResponse()
        case None => InternalServerErrorResponse()
      }
    }

    case id :: Nil JsonGet _ => {
      val intId = id.toInt
      val cookie = CookieDb.allCookies.find(_.id == intId)
      cookie match {
        case Some(cookie) => Extraction.decompose(cookie):JValue
        case None => NotFoundResponse()
      }
    }

    case id :: Nil JsonDelete _ => {
      val intId = id.toInt
      val deletedCookie = CookieDb.removeCookie(intId)
      OkResponse()
    }
  })

}
