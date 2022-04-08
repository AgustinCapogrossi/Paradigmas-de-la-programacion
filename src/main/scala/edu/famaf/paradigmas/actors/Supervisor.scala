package edu.famaf.paradigmas

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps


object Supervisor {
  def apply(): Behavior[SupervisorCommand] = Behaviors.setup(context => new Supervisor(context))

  sealed trait SupervisorCommand
  final case class StartFeed(url: String,
                            feed: List[String],
                            name: String,
                            urlType: String) extends SupervisorCommand
                            
  final case class SetupFeed(feed: String) extends SupervisorCommand
  final case class StopWithMessage(msg: String) extends SupervisorCommand
  final case class Stop() extends SupervisorCommand
}

class Supervisor(context: ActorContext[Supervisor.SupervisorCommand])
    extends AbstractBehavior[Supervisor.SupervisorCommand](context) {
  context.log.info("Supervisor Started")
  
  import Supervisor._
  
  var feedList: List[ActorRef[Feed.FeedRequest]] = List()
  var feeds: List[String] = List() 

  override def onMessage(msg: SupervisorCommand): Behavior[SupervisorCommand] = {
    msg match {
      case StartFeed(url,feed,name,urlType) =>
      	val feedToAdd = context.spawn(
      	  Feed(),s"Generating_feed_process_ID_${feedList.length}")
      	
      	feedList = feedToAdd :: feedList
      	
      	feedToAdd ! Feed.GetFeed(url ,feed ,urlType ,context.self)
      	Behaviors.same
      	
      case SetupFeed(newFeed) =>
      	feeds = newFeed :: feeds
      	context.log.info(s"Feed${newFeed}")
      	Behaviors.same
      case StopWithMessage(msg) =>
      	context.log.info(msg)
      	context.self ! Stop()
      	Behaviors.same
      case Stop() => Behaviors.stopped
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[SupervisorCommand]] = {
    case PostStop =>
      context.log.info("Supervisor Stopped")
      Behaviors.same
  }
}

