package zuri.designs.artofchicago.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artworks(
    @SerialName("pagination") val pagination: PaginationConfig,
    @SerialName("data") val data: List<ArtItemJson>,
    @SerialName("config") val config: ConfigData
)

@Serializable
data class PaginationConfig(
    @SerialName("current_page") val id: Int,
    @SerialName("prev_url") val prevPage: String?,
    @SerialName("next_url") val nextPage: String?,
)