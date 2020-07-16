package com.zhenpin.zhenkotlin_android.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aleyn.mvvm.base.BaseViewModel
import com.test_zhenpin.base.room.Word
import com.test_zhenpin.base.room.WordDao
import com.test_zhenpin.base.room.WordDatabase
import com.test_zhenpin.base.room.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Main2ViewModel() : BaseViewModel() {

//    val wordsDao: WordDao by lazy { WordDatabase.getDatabase(getApplication(), scope).wordDao() }
//    val scope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
//    val allWords: LiveData<List<Word>>
//
//    init {
//        allWords = wordsDao.getAllWordsLive()
//
//    }

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>
    init {
        val wordsDao = WordDatabase.getDatabase(getApplication(),viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }
    fun insert(word: Word) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(word)
    }
    fun delete(word: Word) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(word)
    }

}