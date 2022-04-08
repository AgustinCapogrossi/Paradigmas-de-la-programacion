package edu.famaf.paradigmas

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import scalaj.http.{Http, HttpResponse}
import scala.xml.XML
import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.jackson.JsonMethods._

class ParseRSS {
    def request(url: String): String={
    	try{
        	val response: HttpResponse[String] =
        	  Http(url).timeout(connTimeoutMs = 2000, readTimeoutMs = 5000).asString
        response.body
    	}
    	catch{ 
    		case e: Exception => ""
    	}
    }
    
    def parseURL(responsebody : String): Seq[String]={
    	responsebody match {
    	case "" => Seq()
    	case _ =>
        val xml = XML.loadString(responsebody)
        (xml \\ "item").map { item =>
          ((item \ "title").text + " " + (item \ "description").text)}
        }
    }
}

class ParseJSON extends ParseRSS{
    implicit val formats = DefaultFormats
    override def parseURL(responsebody: String): Seq[String]={ 
    	responsebody match {	
    		case "" => Seq()
    		case _ =>
   			 	val pattern ="(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r
   			 	val jsonString = pattern.replaceAllIn(responsebody,"").trim() 
    			val result = (parse(jsonString) \ "data" \ "children" \ "data")
    			.extract[List[Map[String, Any]]] 
    			val filteredmap = result.map((_.filter((t) => t._1 == "title" || t._1 == "selftext")
    			.mapValues(_.asInstanceOf[String]).values)) 
    			filteredmap.flatten.toSeq  
    	}
    }
}

object Parse {
  def apply(): Behavior[ParseCommand] = Behaviors.setup(context => new Parse(context))

  sealed trait ParseCommand
  final case class ParseFeed(
    url: String,
    urlType: String,
    supervisorRef: ActorRef[Supervisor.SupervisorCommand]) extends ParseCommand
}

class Parse(context: ActorContext[Parse.ParseCommand])
    extends AbstractBehavior[Parse.ParseCommand](context) {
  context.log.info("Parser started")
  
  var feeds: List[ActorRef[Feed.FeedRequest]] = List() 

  import Parse._

  override def onMessage(msg: ParseCommand): Behavior[ParseCommand] = {
    msg match {
   		case ParseFeed(url,urlType,supervisorRef) => 
   		var feed: String = ""
   		var requested: String = ""
   		urlType match {
   			case "rss" =>
   				val modelRSS = new ParseRSS
   				requested = modelRSS.request(url)
   				feed = modelRSS.parseURL(requested).mkString(" ") 
   		
   			case "reddit" =>
   				val modelJSON = new ParseJSON
   				requested = modelJSON.request(url)
   				feed = modelJSON.parseURL(requested).mkString(" ") 
   		}	
    	supervisorRef ! Supervisor.SetupFeed(feed)
    	Behaviors.same
    }
  }
}

