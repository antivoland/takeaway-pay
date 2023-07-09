package antivoland.jet.repository

import antivoland.jet.repository.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, String>