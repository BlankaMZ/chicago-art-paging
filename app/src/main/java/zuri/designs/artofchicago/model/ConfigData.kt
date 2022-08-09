package zuri.designs.artofchicago.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigData(
    @SerialName("iiif_url") val iiifUrl: String
)