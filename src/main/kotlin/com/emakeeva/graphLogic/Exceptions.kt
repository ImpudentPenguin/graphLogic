package com.emakeeva.graphLogic

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Graph hasn't vertices")
class GraphHasntVerticesException: Exception("Graph hasn't vertices")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Graph has one vertex")
class GraphHasOneVertexException: Exception("Graph has one vertex")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Distance: going beyond boundaries")
class DistanceException: Exception("Going beyond boundaries")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Limit vertices of the graph")
class LimitVerticesException: Exception("Limit vertices of the graph")