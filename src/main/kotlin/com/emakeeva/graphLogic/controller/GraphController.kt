package com.emakeeva.graphLogic.controller

import com.emakeeva.graphLogic.model.Graph
import com.emakeeva.graphLogic.model.GraphBody
import com.emakeeva.graphLogic.service.GraphService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GraphsController() {
    private lateinit var graphService: GraphService

    @Autowired
    constructor(graphService: GraphService) : this() {
        this.graphService = graphService
    }

    @PostMapping(value = ["/create"])
    fun create(@RequestBody graphBody: GraphBody): ResponseEntity<String> {
        graphService.create(graphBody)
        return ResponseEntity("created", HttpStatus.OK)
    }

    @GetMapping(value = ["/graph"])
    fun read(): ResponseEntity<Graph> {
        val graph = graphService.read()
        return graph?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}