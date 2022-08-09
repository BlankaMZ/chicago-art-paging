package zuri.designs.artofchicago.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import zuri.designs.artofchicago.model.Artworks

interface ChicagoArtApi {

    @GET("artworks")
    suspend fun getAllArtItems(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : Artworks

    @GET("artworks/search")
    suspend fun searchArtItems(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    )
}