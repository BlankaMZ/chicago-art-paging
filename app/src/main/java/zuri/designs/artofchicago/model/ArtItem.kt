package zuri.designs.artofchicago.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import zuri.designs.artofchicago.util.Constants.CHICAGO_ART_ITEM_TABLE

@Serializable
@Entity(tableName = CHICAGO_ART_ITEM_TABLE)
data class ArtItem(
    @PrimaryKey(autoGenerate = false)
    val primaryId: Int,
    val id: Int,
    val title: String,
    @SerialName("place_of_origin")
    val placeOfOrigin: String?,
    @SerialName("artist_title")
    val artistName: String?,
    @SerialName("style_title")
    val styleTitle: String?,
    @SerialName("image_id")
    val imageId: String?
)

@Serializable
data class ArtItemJson(
    val id: Int,
    val title: String,
    @SerialName("place_of_origin")
    val placeOfOrigin: String?,
    @SerialName("artist_title")
    val artistName: String?,
    @SerialName("style_title")
    val styleTitle: String?,
    @SerialName("image_id")
    val imageId: String?
)