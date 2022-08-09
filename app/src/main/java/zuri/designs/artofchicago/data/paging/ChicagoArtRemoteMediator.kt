package zuri.designs.artofchicago.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import zuri.designs.artofchicago.data.local.ChicagoArtDatabase
import zuri.designs.artofchicago.data.remote.ChicagoArtApi
import zuri.designs.artofchicago.model.ArtItem
import zuri.designs.artofchicago.model.ArtItemRemoteKeys
import zuri.designs.artofchicago.util.Constants.IMAGE_URL_END_DEFAULT
import zuri.designs.artofchicago.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@ExperimentalPagingApi
class ChicagoArtRemoteMediator @Inject constructor(
    private val chicagoArtApi: ChicagoArtApi,
    private val chicagoArtDatabase: ChicagoArtDatabase
) : RemoteMediator<Int, ArtItem>() {

    private val chicagoArtItemDao = chicagoArtDatabase.chicagoArtDao()
    private val chicagoRemoteKeysDao = chicagoArtDatabase.chicagoRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArtItem>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = chicagoArtApi.getAllArtItems(page = currentPage, limit = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.data.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            chicagoArtDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    chicagoArtItemDao.deleteAllArtItems()
                    chicagoRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.data.map { artItem ->
                    println("${artItem.id} -> ${prevPage}, $nextPage")
                    ArtItemRemoteKeys(
                        id = artItem.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                val items = response.data.mapIndexed {index,item ->
                    ArtItem(
                        primaryId = "${response.pagination.id}${index}".toInt(),
                        id = item.id,
                        title = item.title,
                        placeOfOrigin = item.placeOfOrigin,
                        artistName = item.artistName,
                        styleTitle = item.styleTitle,
                        imageId = if (item.imageId != null) createImageUrl(
                            item.imageId,
                            response.config.iiifUrl
                        ) else ""

                    )
                }
                chicagoRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                chicagoArtItemDao.addArtItems(artItems = items)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun createImageUrl(imageId: String, urlForImage: String): String {
        return "$urlForImage/$imageId/$IMAGE_URL_END_DEFAULT"
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArtItem>
    ): ArtItemRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                chicagoRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ArtItem>
    ): ArtItemRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { artItem ->
             chicagoRemoteKeysDao.getRemoteKeys(id = artItem.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ArtItem>
    ): ArtItemRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { artItem ->
               chicagoRemoteKeysDao.getRemoteKeys(id = artItem.id)
            }
    }
}