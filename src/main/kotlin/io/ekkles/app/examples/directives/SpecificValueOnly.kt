package io.ekkles.app.examples.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective

@GraphQLDirective(description = "This validates inputted string is equal to specified argument")
annotation class SpecificValueOnly(val value: String)
