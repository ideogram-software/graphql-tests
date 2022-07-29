package io.ekkles.app.examples.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective

/**
 * Used to verify the performance overhead of instrumentation on fields.
 * Marker directive only, does not have DirectiveWiring.
 */
@GraphQLDirective(
    name = TRACK_TIMES_INVOKED_DIRECTIVE_NAME,
    description = "If the field is marked with this directive, " +
        "we keep track of how many times this field was invoked per exection " +
        "and log the result server side through graphql-java Instrumentation"
)
annotation class TrackTimesInvoked

const val TRACK_TIMES_INVOKED_DIRECTIVE_NAME = "trackTimesInvoked"
