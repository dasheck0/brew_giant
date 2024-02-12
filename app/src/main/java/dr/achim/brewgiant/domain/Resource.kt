package dr.achim.brewgiant.domain

sealed class Resource<out T> {
    abstract val data: T?
    open val message: String? = null

    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Success<T>(override val data: T) : Resource<T>()
    data class Error<T>(override val data: T? = null, override val message: String) : Resource<T>()
}