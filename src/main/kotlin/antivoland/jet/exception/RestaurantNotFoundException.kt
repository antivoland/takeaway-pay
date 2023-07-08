package antivoland.jet.exception

class RestaurantNotFoundException(id: String) :
    RuntimeException("Restaurant $id not found")