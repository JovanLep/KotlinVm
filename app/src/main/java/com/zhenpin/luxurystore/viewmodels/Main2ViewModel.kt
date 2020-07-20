package com.zhenpin.luxurystore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zhen.mvvm.base.BaseViewModel
import com.zhen.base.room.Word
import com.zhen.base.room.WordDatabase
import com.zhen.base.room.WordRepository
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