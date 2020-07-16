package com.test_zhenpin.base.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class WordRepository (private val wordDao: WordDao){
    val allWords: LiveData<List<Word>> = wordDao.getAllWordsLive()
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
    @WorkerThread
    suspend fun delete(word: Word){
        wordDao.delete(word)
    }
}