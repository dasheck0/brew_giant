package dr.achim.brewgiant.data.remote.dto

data class IngredientsDto(
    val hops: List<HopsDto>,
    val malt: List<MaltDto>,
    val yeast: String
)