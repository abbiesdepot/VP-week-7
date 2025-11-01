package com.abbie.week87.data.services

import com.abbie.week87.data.dto.Albumapi
import com.abbie.week87.data.dto.Artistapi
import com.abbie.week87.data.dto.Trackapi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {

    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): Response<Artistapi>

    @GET("searchalbum.php")
    suspend fun searchAlbumsByArtist(
        @Query("s") artistName: String
    ): Response<Albumapi>

    @GET("album.php")
    suspend fun getAlbumDetail(
        @Query("m") albumId: String
    ): Response<Albumapi>

    @GET("track.php")
    suspend fun getAlbumTracks(
        @Query("m") albumId: String
    ): Response<Trackapi>

    companion object {
        private const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/2/"

        fun create(): ArtistService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ArtistService::class.java)
        }
    }
}