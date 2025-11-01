package com.abbie.week87.data.repositories

import com.abbie.week87.data.services.ArtistService
import com.abbie.week87.ui.model.AlbumDetailModel
import com.abbie.week87.ui.model.AlbumModel
import com.abbie.week87.ui.model.ArtistModel
import com.abbie.week87.ui.model.TrackModel

class AudioDBRepository(private val service: ArtistService) {

    suspend fun getArtistInfo(artistName: String): ArtistModel {
        val response = service.searchArtist(artistName).body()!!
        val artist = response.artists?.firstOrNull()!!

        return ArtistModel(
            idArtist = artist.idArtist ?: "",
            name = artist.strArtist ?: "",
            genre = artist.strGenre ?: "",
            thumbUrl = artist.strArtistThumb ?: "",
            bannerUrl = artist.strArtistBanner ?: "",
            biography = artist.strBiographyEN ?: ""
        )
    }

    suspend fun getArtistAlbums(artistName: String): List<AlbumModel> {
        val response = service.searchAlbumsByArtist(artistName).body()!!
        val albums = response.album ?: emptyList()

        return albums.map { album ->
            AlbumModel(
                idAlbum = album.idAlbum ?: "",
                albumName = album.strAlbum ?: "",
                albumThumb = album.strAlbumThumb ?: "",
                yearReleased = album.intYearReleased ?: "",
                artistName = album.strArtist ?: ""
            )
        }
    }

    suspend fun getAlbumDetail(albumId: String): AlbumDetailModel {
        val albumResponse = service.getAlbumDetail(albumId).body()!!
        val album = albumResponse.album?.firstOrNull()!!

        val tracksResponse = service.getAlbumTracks(albumId).body()!!
        val tracks = tracksResponse.track?.map { track ->
            TrackModel(
                idTrack = track.idTrack ?: "",
                trackName = track.strTrack ?: "",
                duration = track.intDuration ?: "",
                trackNumber = track.intTrackNumber ?: ""
            )
        }?.sortedBy { it.trackNumber.toIntOrNull() ?: 0 } ?: emptyList()

        return AlbumDetailModel(
            idAlbum = album.idAlbum ?: "",
            albumName = album.strAlbum ?: "",
            albumThumb = album.strAlbumThumb ?: "",
            yearReleased = album.intYearReleased ?: "",
            genre = album.strGenre ?: "",
            description = album.strDescriptionEN ?: "",
            artistName = album.strArtist ?: "",
            tracks = tracks
        )
    }

    fun getImageUrl(thumbUrl: String?): String {
        return thumbUrl ?: ""
    }
}