package com.emakeeva.graphLogic.service

import com.emakeeva.graphLogic.model.Graph
import com.emakeeva.graphLogic.model.GraphBody

interface IGraphService {
    fun create(graphBody: GraphBody)
    fun read(): Graph?
}