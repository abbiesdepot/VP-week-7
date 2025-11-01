package com.abbie.week87.ui.viewmodel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbie.week87.data.dto.Album
import com.abbie.week87.data.dto.Artist
import com.abbie.week87.data.dto.Track
import com.abbie.week87.data.repositories.Artistrepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ArtistUiState {
    object Idle : ArtistUiState()
    object Loading : ArtistUiState()
    data class ArtistLoaded(val artist: Artist, val albums: List<Album>) : ArtistUiState()
    data class AlbumSelected(val album: Album, val tracks: List<Track>) : ArtistUiState()
    data class Error(val message: String) : ArtistUiState()
}

class ArtistViewModel(
    private val repository: Artistrepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArtistUiState>(ArtistUiState.Idle)
    val uiState: StateFlow<ArtistUiState> = _uiState

    fun searchArtist(artistName: String) {
        viewModelScope.launch {
            try {
                _uiState.value = ArtistUiState.Loading
                val artist = repository.getArtistInfo(artistName)
                val albums = repository.getArtistAlbums(artistName)
                _uiState.value = ArtistUiState.ArtistLoaded(artist, albums)
            } catch (e: Exception) {
                _uiState.value = ArtistUiState.Error(e.localizedMessage ?: "Failed to fetch artist")
            }
        }
    }

    fun selectAlbum(album: Album) {
        viewModelScope.launch {
            try {
                _uiState.value = ArtistUiState.Loading
                val tracks = repository.getAlbumTracks(album.idAlbum)
                _uiState.value = ArtistUiState.AlbumSelected(album, tracks)
            } catch (e: Exception) {
                _uiState.value = ArtistUiState.Error(e.localizedMessage ?: "Failed to load tracks")
            }
        }
    }

    fun backToAlbums() {
        val current = _uiState.value
        if (current is ArtistUiState.AlbumSelected) {
            viewModelScope.launch {
                val artistName = current.album.strArtist
                searchArtist(artistName)
            }
        }
    }
}
