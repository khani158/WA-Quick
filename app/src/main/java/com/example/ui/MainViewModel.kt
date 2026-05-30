package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.RecentNumber
import com.example.data.RecentNumberDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: RecentNumberDao) : ViewModel() {

    val recentNumbers: StateFlow<List<RecentNumber>> = dao.getRecentNumbers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addRecentNumber(countryCode: String, number: String) {
        viewModelScope.launch {
            val existing = dao.getByNumber(number, countryCode)
            if (existing != null) {
                dao.update(existing.copy(lastUsedTimestamp = System.currentTimeMillis()))
            } else {
                dao.insert(RecentNumber(countryCode = countryCode, number = number))
            }
        }
    }
}

class MainViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database.recentNumberDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
