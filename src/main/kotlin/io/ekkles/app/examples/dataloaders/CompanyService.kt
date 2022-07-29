package io.ekkles.app.examples.dataloaders

import io.ekkles.app.examples.model.Company
import org.springframework.stereotype.Component

@Component("companyServiceBean")
class CompanyService {
    private val companies = listOf(
        Company(id = 1, name = "FirstCompany"),
        Company(id = 2, name = "SecondCompany")
    )

    fun getCompanies(ids: List<Int>): List<Company> = companies
}
