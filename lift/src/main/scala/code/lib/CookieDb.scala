package code.lib

object CookieDb {
  case class Cookie(id: Int, name: String)

  var allCookies = collection.mutable.ArrayBuffer.empty[Cookie]
  private var idCounter = 0

  def addCookie(cookie:Cookie) = {
    allCookies += Cookie(idCounter, cookie.name)
    idCounter += 1
  }

  def removeCookie(id: Int): Cookie = {
    allCookies.remove(allCookies.indexWhere(_.id == id))
  }

  //Store some cookies in the "DB"
  addCookie(Cookie(0, "Chocolate Chip"))
  addCookie(Cookie(1, "Sugar"))
  addCookie(Cookie(2, "Oatmeal"))

}



