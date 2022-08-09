package zuri.designs.artofchicago.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import zuri.designs.artofchicago.data.local.ChicagoArtDatabase
import zuri.designs.artofchicago.data.paging.ChicagoArtRemoteMediator
import zuri.designs.artofchicago.data.remote.ChicagoArtApi
import zuri.designs.artofchicago.model.ArtItem
import zuri.designs.artofchicago.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val chicagoArtApi: ChicagoArtApi,
    private val chicagoArtDatabase: ChicagoArtDatabase
){

    fun getAllArtItems(): Flow<PagingData<ArtItem>> {
        val pagingSourceFactory = {
            chicagoArtDatabase.chicagoArtDao().getAllArtItems()
        }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ChicagoArtRemoteMediator(
                chicagoArtApi = chicagoArtApi,
                chicagoArtDatabase = chicagoArtDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}