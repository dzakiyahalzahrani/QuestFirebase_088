package com.example.questfirebase_088.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questfirebase_088.modeldata.DetailSiswa
import com.example.questfirebase_088.modeldata.UIStateSiswa
import com.example.questfirebase_088.modeldata.toDataSiswa
import com.example.questfirebase_088.modeldata.toUiStateSiswa
import com.example.questfirebase_088.repositori.RepositorySiswa
import com.example.questfirebase_088.view.HalamanEdit
import com.example.questfirebase_088.view.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // Mengambil ID sebagai String -> Long
    private val idSiswa: Long =
        savedStateHandle.get<Long>(HalamanEdit.itemIdArg) ?: 0L


    init {
        viewModelScope.launch {
            // Tanda !! berarti kita yakin data ada (force unwrap) sesuai gambar
            uiStateSiswa = repositorySiswa.getSatuSiswa(idSiswa)!!
                .toUiStateSiswa(true)
        }
    }


}