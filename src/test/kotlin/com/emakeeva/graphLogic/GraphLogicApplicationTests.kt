package com.emakeeva.graphLogic

import com.emakeeva.graphLogic.model.Graph
import com.emakeeva.graphLogic.model.GraphBody
import com.emakeeva.graphLogic.service.GraphService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.lang.Exception

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GraphLogicApplicationTests {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var service: GraphService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    @Description("TASD-15")
    fun `call create method should return created`() {
        val graphBody = GraphBody(numberOfVertices = 5, fromRange = 1.0, toRange = 100.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        Assertions.assertEquals("created", result)
    }

    @Test
    @Description("TASD-16")
    fun `call create method with one vertex should return error`() {
        val graphBody = GraphBody(numberOfVertices = 1, fromRange = 1.0, toRange = 100.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn().response.errorMessage
        Assertions.assertEquals("Graph has one vertex", result)
    }

    @Test
    @Description("TASD-17")
    fun `call create method without vertices should return error`() {
        val graphBody = GraphBody(numberOfVertices = 0, fromRange = 1.0, toRange = 100.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn().response.errorMessage
        Assertions.assertEquals("Graph hasn't vertices", result)
    }

    @Test
    @Description("TASD-18")
    fun `call create method with an incorrect distance should return error`() {
        val graphBody = GraphBody(numberOfVertices = 5, fromRange = 1.0, toRange = 300.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn().response.errorMessage
        Assertions.assertEquals("Distance: going beyond boundaries", result)
    }

    @Test
    @Description("TASD-19")
    fun `call create method with negative distance should return error`() {
        val graphBody = GraphBody(numberOfVertices = 5, fromRange = -1.0, toRange = 100.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn().response.errorMessage
        Assertions.assertEquals("Distance: going beyond boundaries", result)
    }

    @Test
    @Description("TASD-20")
    fun `call create method with large vertex should return error`() {
        val graphBody = GraphBody(numberOfVertices = 20, fromRange = 1.0, toRange = 100.0)

        val result = mockMvc.perform(post("/create")
                .content(ObjectMapper().writeValueAsString(graphBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn().response.errorMessage
        Assertions.assertEquals("Limit vertices of the graph", result)
    }

    @Test
    @Description("TASD-21")
    fun `call graph method should return graph`() {
        val graphBody = GraphBody(numberOfVertices = 5, fromRange = 1.0, toRange = 100.0)
        service.create(graphBody)

        val result = mockMvc.perform(get("/graph")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        val graphResult = ObjectMapper().readValue(result, Graph::class.java)

        Assertions.assertEquals(5, graphResult.numberOfVertices)
    }

    @Test
    @Description("TASD-22")
    fun `call graph method should return error 404`() {
        val graphBody = GraphBody(numberOfVertices = 0, fromRange = 1.0, toRange = 100.0)
        try {
            service.create(graphBody)
        } catch (e: Exception) {
        }

        mockMvc.perform(get("/graph")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }
}
