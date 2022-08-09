package zuri.designs.artofchicago.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zuri.designs.artofchicago.model.ArtItem

@Dao
interface ChicagoArtDao {

    @Query("SELECT * FROM chicago_art_item_table")
    fun getAllArtItems() : PagingSource<Int, ArtItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArtItems(artItems: List<ArtItem>)

    @Query("DELETE FROM chicago_art_item_table")
    suspend fun deleteAllArtItems()
}