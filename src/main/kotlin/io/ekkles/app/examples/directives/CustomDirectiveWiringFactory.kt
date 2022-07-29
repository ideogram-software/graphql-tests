package io.ekkles.app.examples.directives

import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import graphql.schema.GraphQLDirectiveContainer
import kotlin.reflect.KClass

class CustomDirectiveWiringFactory : KotlinDirectiveWiringFactory(manualWiring = mapOf<String, KotlinSchemaDirectiveWiring>("lowercase" to LowercaseSchemaDirectiveWiring())) {

    private val stringEvalDirectiveWiring = StringEvalSchemaDirectiveWiring()
    private val caleOnlyDirectiveWiring = SpecificValueOnlySchemaDirectiveWiring()

    override fun getSchemaDirectiveWiring(environment: KotlinSchemaDirectiveEnvironment<GraphQLDirectiveContainer>): KotlinSchemaDirectiveWiring? = when (environment.directive.name) {
        getDirectiveName(StringEval::class) -> stringEvalDirectiveWiring
        getDirectiveName(SpecificValueOnly::class) -> caleOnlyDirectiveWiring
        else -> null
    }
}

internal fun getDirectiveName(kClass: KClass<out Annotation>): String = kClass.simpleName!!.replaceFirstChar { it.lowercase() }
