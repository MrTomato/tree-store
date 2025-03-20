package ai.prewave.treeStore.edge

import ai.prewave.treeStore.edge.rest.EdgePostRequest
import org.jooq.generated.public_.tables.records.EdgeRecord
import org.springframework.stereotype.Service

@Service
class EdgeService(private val edgeRepository: EdgeRepository) {
    fun createEdge(request: EdgePostRequest): Result<EdgeRecord> = request.runCatching {
        edgeRepository.createEdge(this)
    }
}