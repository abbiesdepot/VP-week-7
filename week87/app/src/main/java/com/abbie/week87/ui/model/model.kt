package com.abbie.week87.ui.model

data class ArtistModel(
    val idArtist: String = "",
    val name: String = "",
    val genre: String = "",
    val thumbUrl: String = "",
    val bannerUrl: String = "",
    val biography: String = ""
)

data class AlbumModel(
    val idAlbum: String = "",
    val albumName: String = "",
    val albumThumb: String = "",
    val yearReleased: String = "",
    val artistName: String = ""
)

data class AlbumDetailModel(
    val idAlbum: String = "",
    val albumName: String = "",
    val albumThumb: String = "",
    val yearReleased: String = "",
    val genre: String = "",
    val description: String = "",
    val artistName: String = "",
    val tracks: List<TrackModel> = emptyList()
)

data class TrackModel(
    val idTrack: String = "",
    val trackName: String = "",
    val duration: String = "",
    val trackNumber: String = ""
)