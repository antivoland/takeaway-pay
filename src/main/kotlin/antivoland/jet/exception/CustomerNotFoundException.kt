package antivoland.jet.exception

class CustomerNotFoundException(id: String) :
    RuntimeException("Customer '$id' not found")