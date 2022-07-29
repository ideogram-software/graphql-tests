package io.ekkles.app.examples.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective

@GraphQLDirective(name = "lowercase", description = "Modifies the string field to lowercase")
annotation class LowercaseDirective
