package ai.prewave.treeStore.edge

import ai.prewave.treeStore.edge.rest.EdgePostRequest
import org.jooq.DSLContext
import org.jooq.generated.public_.tables.Edge.EDGE
import org.jooq.generated.public_.tables.records.EdgeRecord
import org.springframework.stereotype.Repository

@Repository
class EdgeRepository(private val dsl: DSLContext) {
    fun createEdge(request: EdgePostRequest): EdgeRecord =
        dsl.insertInto(EDGE)
            .set(EDGE.FROM_ID, request.fromId)
            .set(EDGE.TO_ID, request.toId)
            .returning()
            .fetchSingle()
}