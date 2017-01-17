package com.github.ldaniels528.trifecta.sjs.models

import com.github.ldaniels528.trifecta.sjs.models.Query.SavedResult
import org.scalajs.dom.browser.console
import org.scalajs.nodejs.util.ScalaJsHelper._
import org.scalajs.sjs.JsUnderOrHelper._

import scala.scalajs.js

/**
  * Represents Topic Details
  * @author lawrence.daniels@gmail.com
  */
@js.native
trait TopicDetails extends js.Object {
  var topic: String = js.native
  var partitions: js.Array[PartitionDetails] = js.native
  var leader: js.Array[Broker] = js.native
  var replicas: js.Array[ReplicaGroup] = js.native
  var totalMessages: Int = js.native

  // ui-specific properties
  var expanded: js.UndefOr[Boolean] = js.native
  var loading: js.UndefOr[Boolean] = js.native
  var loadingConsumers: js.UndefOr[Boolean] = js.native
  var queriesExpanded: js.UndefOr[Boolean] = js.native
  var query: js.UndefOr[Query] = js.native
  var queryResults: js.UndefOr[js.Array[SavedResult]] = js.native
  var replicaExpanded: js.UndefOr[Boolean] = js.native
  var updatingTopics: js.UndefOr[Int] = js.native

}

/**
  * Topic Details Companion Object
  * @author lawrence.daniels@gmail.com
  */
object TopicDetails {

  /**
    * Topic Details Enrichment
    * @author lawrence.daniels@gmail.com
    */
  final implicit class TopicDetailsEnrichment(val details: TopicDetails) extends AnyVal {

    def apply(partitionId: Int): Option[PartitionDetails] = {
      details.partitions.find(_.partition == partitionId)
    }

    def copy(topic: js.UndefOr[String] = js.undefined,
             partitions: js.UndefOr[js.Array[PartitionDetails]] = js.undefined,
             leader: js.UndefOr[js.Array[Broker]] = js.undefined,
             replicas: js.UndefOr[js.Array[ReplicaGroup]] = js.undefined,
             totalMessages: js.UndefOr[Int] = js.undefined,
             expanded: js.UndefOr[Boolean] = js.undefined,
             loading: js.UndefOr[Boolean] = js.undefined,
             loadingConsumers: js.UndefOr[Boolean] = js.undefined,
             queriesExpanded: js.UndefOr[Boolean] = js.undefined,
             replicaExpanded: js.UndefOr[Boolean] = js.undefined,
             queryResults: js.UndefOr[js.Array[SavedResult]] = js.undefined,
             updatingTopics: js.UndefOr[Int] = js.undefined): TopicDetails = {
      val cloned = New[TopicDetails]
      cloned.topic = topic getOrElse details.topic
      cloned.partitions = partitions getOrElse details.partitions
      cloned.leader = leader getOrElse details.leader
      cloned.replicas = replicas getOrElse details.replicas
      cloned.totalMessages = totalMessages getOrElse details.totalMessages
      cloned.expanded = expanded ?? details.expanded
      cloned.loading = loading ?? details.loading
      cloned.loadingConsumers = loadingConsumers ?? details.loadingConsumers
      cloned.queriesExpanded = queriesExpanded ?? details.queriesExpanded
      cloned.queryResults = queryResults ?? details.queryResults
      cloned.replicaExpanded = replicaExpanded ?? details.replicaExpanded
      cloned.updatingTopics = updatingTopics ?? details.updatingTopics
      cloned
    }

    def replace(delta: PartitionDelta) {
      details.partitions.indexWhere(_.partition ?== delta.partition) match {
        case -1 =>
          details.partitions.push(PartitionDetails(delta))
        case index =>
          // set the total messages
          delta.totalMessages.foreach(details.totalMessages = _)

          // update the partition detail
          val myDelta = details.partitions(index)
          myDelta.startOffset = delta.startOffset
          myDelta.endOffset = delta.endOffset
          myDelta.messages = delta.messages
          myDelta.totalMessages = delta.totalMessages
      }
    }

    def replace(message: Message) {
      for {
        partitionId <- message.partition
      } {
        details.partitions.indexWhere(_.partition.contains(partitionId)) match {
          case -1 =>
            console.warn(s"Partition $partitionId does not exist for topic ${details.topic}")
          case index =>
            details.partitions(index).offset = message.offset
        }
      }
    }

  }

}