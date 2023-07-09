package antivoland.jet.repository

import antivoland.jet.repository.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String>