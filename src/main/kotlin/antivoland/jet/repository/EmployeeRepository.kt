package antivoland.jet.repository

import antivoland.jet.domain.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, String>