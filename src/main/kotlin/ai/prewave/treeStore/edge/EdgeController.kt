package ai.prewave.treeStore.edge

import ai.prewave.treeStore.edge.rest.EdgePostRequest
import ai.prewave.treeStore.edge.rest.toEdgeResponse
import ai.prewave.treeStore.errors.DatabaseError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/edges")
class EdgeController(private val edgeService: EdgeService) {
    @PostMapping
    fun createEdge(@RequestBody request: EdgePostRequest): ResponseEntity<out Any> =
        edgeService.createEdge(request)
            .map {
                ResponseEntity.ok(it.toEdgeResponse())
            }
            .getOrElse {
                ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DatabaseError("Could not persist edge!", it.stackTraceToString()))
            }
}