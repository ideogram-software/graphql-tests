package io.ekkles.app.examples.dataloaders

import com.expediagroup.graphql.server.execution.KotlinDataLoader
import io.ekkles.app.examples.model.Company
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class CompanyDataLoader(private val service: io.ekkles.app.examples.dataloaders.CompanyService) : KotlinDataLoader<Int, Company> {
    companion object {
        const val name = "CompanyDataLoader"
    }

    override val dataLoaderName = io.ekkles.app.examples.dataloaders.CompanyDataLoader.Companion.name
    override fun getDataLoader() = DataLoader<Int, Company> { ids ->
        CompletableFuture.supplyAsync { service.getCompanies(ids) }
    }
}
