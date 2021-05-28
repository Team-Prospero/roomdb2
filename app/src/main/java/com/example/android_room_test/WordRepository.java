package com.example.android_room_test;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//**** Repository 클래스, DAO와 애플리케이션(또는 네트워크) 사이에서 쿼리를 관리하고 여러 개의 백엔드에 DB를 사용할 수 있도록 한다 ****//
// 레포지토리는 애플리케이션에서 데이터를 가져오거나, 로컬 DB에서 결과를 애플리케이션으로 반환한다.
public class WordRepository {
    private WordDAO mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDataBase db = WordRoomDataBase.getDatabase(application);
        mWordDao = db.wordDAO(); // 모든 DB R/W 메소드를 가지는 wordDAO
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // 룸은 모든 각각의 쿼리를 다른 쓰레드에서 처리한다.
    // 메인 쓰레드에서 데이터에 변화가 생겼을 때, LiveData 는 옵저버에게 변화를 알린다.
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // 메인 쓰레드에 삽입작업을 하는 것을 방지하기 위해 WordRoomDataBase를 만들어 백그라운드 쓰레드에 삽입작업 수행
    void insert(Word word) {
        WordRoomDataBase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
