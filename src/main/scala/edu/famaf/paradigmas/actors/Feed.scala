package edu.famaf.paradigmas

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps


object Feed {
  def apply(): Behavior[FeedRequest] = Behaviors.setup(context => new Feed(context))

  sealed trait FeedRequest
  final case class GetFeed(
    urlTemplate : String,
    params: List[String],
    urlType: String,
    supervisorRef: ActorRef[Supervisor.SupervisorCommand]) extends FeedRequest
}

class Feed(context: ActorContext[Feed.FeedRequest])
      extends AbstractBehavior[Feed.FeedRequest](context) {
  context.log.info("Feed started")
  
  import Feed._
  
  val pat = "%s".r
  
  var feedList: List[(ActorRef[Parse.ParseCommand])] = List()

  override def onMessage(msg: FeedRequest): Behavior[FeedRequest] = {
    var urls: List[(String,String)] = List()
    msg match {
      case GetFeed(urlTemplate,params,urlType,supervisorRef) =>
 		val urlparams = params.map {s => pat replaceAllIn(urlTemplate,s)}.map{url => (url,urlType)}
        urls = urls ++ urlparams
       
        for (i <- urls){
        	val request = context.spawn(Parse(),
        	  s"Adding_parsing_process_on_feed_${feedList.length}")
        	request ! Parse.ParseFeed(i._1,i._2,supervisorRef)
        	feedList = request :: feedList
        }
      Behaviors.same
    }
  }
}
