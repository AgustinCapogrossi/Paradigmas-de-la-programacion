package edu.famaf.paradigmas

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps


object Sort {
  def apply(): Behavior[SupervisorCommand] = Behaviors.setup(context => new Supervisor(context))

  sealed trait SortCommand
  final case class SortFeed(feed: String, 
  							supervisorRef: ActorRef[Supervisor.SupervisorCommand]))
  							extends SortCommand
  
}

class Sort(context: ActorContext[Sort.SortCommand])
    extends AbstractBehavior[Sort.SortCommand](context) {
  context.log.info("Sorter Started")
  
  import Sort._

  override def onMessage(msg: SortCommand): Behavior[SortCommand] = {
    msg match {
      case SortFeed(feed,supervisorRef) =>
		//codigo lab 2      	
    }
    supervisorRef ! Supervisor.SortedOrderedFeed(variable)
  }
}

