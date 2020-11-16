package com.emakeeva.graphLogic.model

import com.emakeeva.graphLogic.*
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.random.Random

class Graph(
        var numberOfVertices: Int = 0,
        var adjacencyMatrix: List<List<Double>> = mutableListOf()
) {
    companion object {
        fun generateGraph(vertices: Int, leftBound: Double, rightBound: Double): Graph {
            validateParams(vertices, leftBound, rightBound)
            val adjacencyMatrix = MutableList(vertices) { MutableList(vertices) { 0.0 } }

            for (i in 0 until vertices)
                for (j in 0 until vertices)
                    if (i != j) {
                        val scale = 10.0.pow(1.0)
                        val result = ceil(Random.nextDouble(leftBound, rightBound) * scale) / scale
                        adjacencyMatrix[i][j] = result
                        adjacencyMatrix[j][i] = result
                    }

            return Graph(vertices, adjacencyMatrix)
        }

        private fun validateParams(vertices: Int, leftBound: Double, rightBound: Double) {
            when {
                vertices > 15 -> throw LimitVerticesException()
                vertices == 1 -> throw GraphHasOneVertexException()
                vertices < 1 -> throw GraphHasntVerticesException()
                !(leftBound >= 1 && rightBound <= 200) -> throw DistanceException()
            }
        }
    }
}