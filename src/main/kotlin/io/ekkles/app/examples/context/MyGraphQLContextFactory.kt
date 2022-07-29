package io.ekkles.app.examples.context

import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import com.expediagroup.graphql.server.spring.subscriptions.SpringSubscriptionGraphQLContextFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.socket.WebSocketSession

/**
 * [GraphQLContextFactory] that generates [MyGraphQLContext] that will be available when processing GraphQL requests.
 */
@Component
class MyGraphQLContextFactory : SpringGraphQLContextFactory<io.ekkles.app.examples.context.MyGraphQLContext>() {

    override suspend fun generateContext(request: ServerRequest): io.ekkles.app.examples.context.MyGraphQLContext =
        io.ekkles.app.examples.context.MyGraphQLContext(
            request = request,
            myCustomValue = request.headers().firstHeader("MyHeader") ?: "defaultContext"
        )
}

/**
 * [GraphQLContextFactory] that generates [MySubscriptionGraphQLContext] that will be available when processing subscription operations.
 */
@Component
class MySubscriptionGraphQLContextFactory : SpringSubscriptionGraphQLContextFactory<io.ekkles.app.examples.context.MySubscriptionGraphQLContext>() {

    override suspend fun generateContext(request: WebSocketSession): io.ekkles.app.examples.context.MySubscriptionGraphQLContext =
        io.ekkles.app.examples.context.MySubscriptionGraphQLContext(
            request = request,
            auth = null
        )
}
