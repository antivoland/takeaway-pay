package antivoland.jet.exception

class CustomerHasInsufficientFundsException(id: String) :
    RuntimeException("Customer '$id' has insufficient funds")