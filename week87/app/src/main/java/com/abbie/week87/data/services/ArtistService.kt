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
    suspend fun searchArtist(@Query("s") artistName: String): ArtistResponse

    @GET("searchalbum.php")
    suspend fun getAlbums(@Query("s") artistName: String): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetail(@Query("m") albumId: String): AlbumResponse

    @GET("track.php")
    suspend fun getTracks(@Query("m") albumId: String): TrackResponse
}