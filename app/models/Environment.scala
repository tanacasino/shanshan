package models

import play.api.mvc.{AnyContent, Request}
import is.tagomor.woothee.Classifier
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.net.InetAddress


case class Environment(userAgent: UserAgent, ipAddress: String, hostname: String, proto: String, time: String, headers: String)
case class UserAgent(userAgent: String, browser: String, version: String, os: String, category: String)

object Environment {
  def apply(implicit request: Request[AnyContent]): Environment = {
    val userAgent = request.headers.get("User-Agent") getOrElse "None"
    val ipAddress = request.headers.get("X-Forwarded-For") getOrElse request.remoteAddress
    val protocol = request.headers.get("X-Forwarded-Proto") getOrElse "http"
    val map: java.util.Map[String, String] = Classifier.parse(userAgent)
    val browser = Option(map.get("name")) getOrElse "unknown"
    val browserVersion = Option(map.get("version")) getOrElse "unknown"
    val category = Option(map.get("category")) getOrElse "unknown"
    val os = Option(map.get("os")) getOrElse "unknown"
    val time = new DateTime().toString(DateTimeFormat.longDateTime)
    val hostname = try { InetAddress.getByName(ipAddress).getHostName } catch { case _: Throwable => "unknown" }

    Environment(UserAgent(userAgent, browser, browserVersion, os, category),
                ipAddress, hostname, protocol, time, request.headers.toString())
  }
}