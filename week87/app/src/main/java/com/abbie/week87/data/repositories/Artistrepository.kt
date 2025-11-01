package com.abbie.week87.data.repositories

import com.abbie.week87.data.dto.Album
import com.abbie.week87.data.dto.Artist
import com.abbie.week87.data.dto.Track
import com.abbie.week87.data.services.ArtistService

class Artistrepository(private val service: ArtistService) {

    suspend fun getArtistInfo(artistName: String): Artist {
        val response = service.searchArtist(artistName).body()!!
        val artist = response.artists.firstOrNull()!!
        return artist
    }

    suspend fun getArtistAlbums(artistName: String): List<Album> {
        val response = service.searchAlbumsByArtist(artistName).body()!!
        val albums = response.album ?: emptyList()
        return albums
    }

    suspend fun getAlbumDetail(albumId: String): Album {
        val response = service.getAlbumDetail(albumId).body()!!
        val album = response.album?.firstOrNull()!!
        return album
    }

    suspend fun getAlbumTracks(albumId: String): List<Track> {
        // takes response from service
        val response = service.getAlbumTracks(albumId).body()

        // 2. access list of tracks, kalo g ya null return empty list yay
        val tracks = response?.track ?: emptyList()

        // 3. sorting out existing tracks
        return tracks.sortedBy { it.intTrackNumber?.toIntOrNull() ?: 0 }
    }
}