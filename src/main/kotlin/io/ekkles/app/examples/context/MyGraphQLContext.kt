package io.ekkles.app.examples.context

import com.expediagroup.graphql.generator.execution.GraphQLContext
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.socket.WebSocketSession

/**
 * Simple [GraphQLContext] that holds extra value and the [ServerRequest]
 */
class MyGraphQLContext(
    request: ServerRequest,
    val myCustomValue: String
) : SpringGraphQLContext(request)

/**
 * Simple [GraphQLContext] that holds extra value and the [WebSocketSession]
 */
class MySubscriptionGraphQLContext(
    val request: WebSocketSession,
    var auth: String? = null
) : GraphQLContext
