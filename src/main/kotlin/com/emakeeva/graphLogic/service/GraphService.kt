package com.emakeeva.graphLogic.service

import com.emakeeva.graphLogic.model.Graph
import com.emakeeva.graphLogic.model.GraphBody
import org.springframework.stereotype.Service

@Service
class GraphService : IGraphService {
    private var graph: Graph? = null

    override fun create(graphBody: GraphBody) {
        graph = null
        graph = Graph.generateGraph(graphBody.numberOfVertices, graphBody.fromRange, graphBody.toRange)
    }

    override fun read(): Graph? {
        return graph
    }
}