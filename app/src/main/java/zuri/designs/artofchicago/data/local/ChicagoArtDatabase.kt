package zuri.designs.artofchicago.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import zuri.designs.artofchicago.data.local.dao.ChicagoArtDao
import zuri.designs.artofchicago.data.local.dao.ChicagoRemoteKeysDao
import zuri.designs.artofchicago.model.ArtItem
import zuri.designs.artofchicago.model.ArtItemRemoteKeys

@Database(entities = [ArtItem::class, ArtItemRemoteKeys::class], version = 1)
abstract class ChicagoArtDatabase : RoomDatabase() {

    abstract fun chicagoArtDao(): ChicagoArtDao
    abstract fun chicagoRemoteKeysDao(): ChicagoRemoteKeysDao
}