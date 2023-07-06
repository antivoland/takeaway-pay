package antivoland.jet

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerAPI {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = "Not implemented yet"

    @PostMapping("{id}/pay")
    fun pay(@PathVariable id: String) = "Not implemented yet"
}