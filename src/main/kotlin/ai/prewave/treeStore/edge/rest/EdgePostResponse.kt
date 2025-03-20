package ai.prewave.treeStore.edge.rest

import org.jooq.generated.public_.tables.records.EdgeRecord

data class EdgePostResponse(
    val id: Long,
    val idFrom: Long,
    val idTo: Long
)

fun EdgeRecord.toEdgeResponse() = this.run { EdgePostResponse(id, fromId, toId) }