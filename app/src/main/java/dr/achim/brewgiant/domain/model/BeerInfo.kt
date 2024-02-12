package dr.achim.brewgiant.domain.model

import dr.achim.brewgiant.data.remote.dto.IngredientsDto
import dr.achim.brewgiant.data.remote.dto.VolumeDto

data class BeerInfo(
    val abv: Double,
    val description: String,
    val id: Int,
    val imageUrl: String?,
    val ingredients: IngredientsDto,
    val name: String,
    val volume: VolumeDto
)