
package io.ekkles.app

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelNames
import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.execution.KotlinDataFetcherFactoryProvider
import com.expediagroup.graphql.generator.hooks.NoopSchemaGeneratorHooks
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.server.spring.GraphQLConfigurationProperties
import com.expediagroup.graphql.server.spring.subscriptions.ApolloSubscriptionHooks
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.execution.DataFetcherExceptionHandler
import io.ekkles.app.examples.directives.CustomDirectiveWiringFactory
import io.ekkles.app.examples.exceptions.CustomDataFetcherExceptionHandler
import io.ekkles.app.examples.execution.SpringDataFetcherFactory
import io.ekkles.app.examples.execution.CustomDataFetcherFactoryProvider
import io.ekkles.app.examples.execution.MySubscriptionHooks
import io.ekkles.app.examples.hooks.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
class Application {

    @Bean
    fun wiringFactory() = CustomDirectiveWiringFactory()

    @Bean
    fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)

    @Bean
    fun dataFetcherFactoryProvider(
        springDataFetcherFactory: SpringDataFetcherFactory,
        objectMapper: ObjectMapper,
        applicationContext: ApplicationContext
    ) = CustomDataFetcherFactoryProvider(springDataFetcherFactory, objectMapper, applicationContext)

    @Bean
    fun dataFetcherExceptionHandler(): DataFetcherExceptionHandler = CustomDataFetcherExceptionHandler()

    @Bean
    fun apolloSubscriptionHooks(): ApolloSubscriptionHooks = MySubscriptionHooks()

//    @Bean
//    fun schemaConfig(
////        config: GraphQLConfigurationProperties,
////        topLevelNames: Optional<TopLevelNames>,
////        hooks: Optional<SchemaGeneratorHooks>,
////        dataFetcherFactoryProvider: KotlinDataFetcherFactoryProvider
//    ): SchemaGeneratorConfig {
////        val generatorHooks = hooks.orElse(NoopSchemaGeneratorHooks)
//        return SchemaGeneratorConfig(
//            supportedPackages = listOf("io.ekkles"),
////            topLevelNames = topLevelNames.orElse(TopLevelNames()),
////            hooks = generatorHooks,
////            dataFetcherFactoryProvider = dataFetcherFactoryProvider,
////            introspectionEnabled = config.introspection.enabled
//        )
//    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
