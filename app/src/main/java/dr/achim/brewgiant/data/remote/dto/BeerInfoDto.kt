package dr.achim.brewgiant.data.remote.dto

import com.google.gson.annotations.SerializedName
import dr.achim.brewgiant.domain.model.BeerInfo

data class BeerInfoDto(
    val id: Int,
    val abv: Double,
    @SerializedName("attenuation_level")
    val attenuationLevel: Double,
    @SerializedName("boil_volume")
    val boilVolume: BoilVolumeDto,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    @SerializedName("contributed_by")
    val contributedBy: String,
    val description: String,
    val ebc: Double,
    @SerializedName("first_brewed")
    val firstBrewed: String,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
    val ibu: Double,
    @SerializedName("image_url")
    val imageUrl: String?,
    val ingredients: IngredientsDto,
    val method: MethodDto,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    @SerializedName("target_fg")
    val targetFg: Double,
    @SerializedName("target_og")
    val targetOg: Double,
    val volume: VolumeDto
) {
    fun toModel() = BeerInfo(
        abv = abv,
        description = description,
        id = id,
        imageUrl = imageUrl,
        ingredients = ingredients,
        name = name,
        volume = volume
    )
}