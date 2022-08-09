package zuri.designs.artofchicago.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import zuri.designs.artofchicago.util.Constants.CHICAGO_ART_ITEM_REMOTE_KEYS


@Entity(tableName = CHICAGO_ART_ITEM_REMOTE_KEYS)
data class ArtItemRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
){
}