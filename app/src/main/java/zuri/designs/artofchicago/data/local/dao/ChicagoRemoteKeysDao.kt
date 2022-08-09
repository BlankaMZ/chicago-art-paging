package zuri.designs.artofchicago.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zuri.designs.artofchicago.model.ArtItemRemoteKeys

@Dao
interface ChicagoRemoteKeysDao {

    @Query("SELECT * FROM chicago_art_item_remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): ArtItemRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ArtItemRemoteKeys>)

    @Query("DELETE FROM chicago_art_item_remote_keys")
    suspend fun deleteAllRemoteKeys()
}