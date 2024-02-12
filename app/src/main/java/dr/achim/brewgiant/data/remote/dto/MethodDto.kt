package dr.achim.brewgiant.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MethodDto(
    val fermentation: FermentationDto,
    @SerializedName("mash_temp")
    val mashTemp: List<MashTempDto>,
    val twist: Any?
)