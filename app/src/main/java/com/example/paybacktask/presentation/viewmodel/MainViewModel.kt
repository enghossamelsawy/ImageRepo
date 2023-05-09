package com.example.paybacktask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paybacktask.data.constant.Constants
import com.example.paybacktask.domain.entity.ImageViewEntities
import com.example.paybacktask.domain.usecases.ImagesUseCase
import com.example.paybacktask.presentation.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val imagesUseCase: ImagesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    private val _detailsUiState = MutableStateFlow(MainUiState())
    val detailsUiState: StateFlow<MainUiState> = _detailsUiState

    fun getImages(searchString: String) = imagesUseCase(searchString)
        .onStart { _uiState.value = MainUiState(inProgress = true) }
        .onEach(::onEachUpdate)
        .catch { _uiState.emit(MainUiState(errorMessage = Constants.GENERAL_ERROR)) }
        .launchIn(viewModelScope)


    private fun onEachUpdate(imageViewEntities: ImageViewEntities) {
        _uiState.value = when (imageViewEntities) {
            is ImageViewEntities.ImageDomainEntity -> {
                MainUiState(images = imageViewEntities.images)
            }

            is ImageViewEntities.Failure -> {
                MainUiState(errorMessage = imageViewEntities.errorText)
            }
        }
    }

    fun getImage(itemId: Int) {
        imagesUseCase.fetchImage(itemId).onEach {
            _detailsUiState.value = when (it) {
                is ImageViewEntities.ImageDomainEntity -> {
                    MainUiState(images = it.images)
                }

                is ImageViewEntities.Failure -> {
                    MainUiState(errorMessage = it.errorText)
                }
            }
        }.launchIn(viewModelScope)
    }
}



