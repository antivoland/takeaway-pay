package antivoland.jet.repository

import antivoland.jet.domain.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, String>