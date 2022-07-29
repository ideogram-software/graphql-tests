package io.ekkles.app.examples.directives

import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironmentImpl.newDataFetchingEnvironment
import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition

class StringEvalSchemaDirectiveWiring : KotlinSchemaDirectiveWiring {
    private val directiveName = getDirectiveName(StringEval::class)

    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val originalDataFetcher: DataFetcher<*> = environment.getDataFetcher()

        val defaultValueFetcher = DataFetcher<Any> { dataEnv ->
            val newArguments = HashMap(dataEnv.arguments)
            environment.element.arguments.associateWith {
                dataEnv.getArgument(it.name) as String?
            }.forEach { (graphQLArgumentType, value) ->
                if (graphQLArgumentType.getDirective(directiveName).getArgument(StringEval::lowerCase.name).argumentValue.value as Boolean) {
                    newArguments[graphQLArgumentType.name] = value?.lowercase()
                }
                if (value.isNullOrEmpty()) {
                    newArguments[graphQLArgumentType.name] = graphQLArgumentType.argumentDefaultValue.value
                }
            }
            val newEnv = newDataFetchingEnvironment(dataEnv)
                .arguments(newArguments)
                .build()
            originalDataFetcher.get(newEnv)
        }
        environment.setDataFetcher(defaultValueFetcher)
        return field
    }

    override fun onArgument(environment: KotlinSchemaDirectiveEnvironment<GraphQLArgument>): GraphQLArgument {
        val argument = environment.element
        val directive = environment.directive

        val default = directive.getArgument(StringEval::default.name).argumentValue.value as String
        return if (default.isNotEmpty()) {
            argument.transform { it.defaultValue(default) }
        } else {
            argument
        }
    }
}
