package com.example.android_room_test;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//**** ViewModel 클래스, UI와 레포지토리 클래스 사이의 커뮤니티 센터라고 할 수 있다, 데이터를 UI로 전송하고 내부 구성의 변경을 유지하는 클래스이다. ****//
// 뷰모델은 앱 내의 액티비티, 프래그먼트의 생명주기에서 UI 데이터를 유지한다, UI와 데이터를 분리해서 관리할 수 있게 해주는 클래스라고 보면 된다.
// 여기서 LiveData를 사용하면 레포지토리와 UI를 완전히 분리함과 동시에 DB 호출을 레포지토리에서만 처리할 수 있다.
public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords; // 레포지토리의 모든 데이터를 저장하는 mAllWords

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() { return mAllWords; }//

    public void insert(Word word) { mRepository.insert(word); }// 레포지토리의 insert() 메소드를 호출함으로서
}
