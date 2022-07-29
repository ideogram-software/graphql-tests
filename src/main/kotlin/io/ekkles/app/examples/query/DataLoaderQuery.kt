package io.ekkles.app.examples.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.ekkles.app.examples.dataloaders.CompanyDataLoader
import io.ekkles.app.examples.model.Company
import io.ekkles.app.examples.model.Employee
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

/**
 * Example query that showcases the usage of data loader pattern.
 */
@Component
class DataLoaderQuery : Query {
    private val employees = listOf(
        Employee(name = "Mike", companyId = 1, skills = setOf("sales", "sales")),
        Employee(name = "John", companyId = 1, skills = setOf("management")),
        Employee(name = "Steve", companyId = 2)
    )

    @GraphQLDescription("Get all employees")
    fun employees(): List<Employee> {
        return employees
    }
}

@Component("CompanyDataFetcher")
class CompanyDataFetcher : DataFetcher<CompletableFuture<Company>> {

    override fun get(environment: DataFetchingEnvironment): CompletableFuture<Company> {
        val companyId = environment.getSource<Employee>().companyId
        return environment.getValueFromDataLoader(io.ekkles.app.examples.dataloaders.CompanyDataLoader.name, companyId)
    }
}
