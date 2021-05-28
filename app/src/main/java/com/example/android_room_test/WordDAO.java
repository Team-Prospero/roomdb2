package com.example.android_room_test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// ****  Data Access Object 의 약자로, 컴파일 시점에 SQL 문의 유효성을 검사하고 메소드에 연결한다.   ****//
// 어노테이션 @Insert @Delete @Update @Query 가 있고,
// 이 중 @Query 는 괄호 내부에 사용자 작성 쿼리를 넣어 메소드에 연결할 수 있다.

@Dao
public interface WordDAO {

    // 데이터가 충돌했을 때 어떻게 행동할지 정해서 같은 데이터를 여러 번 집어넣을 수 있다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    // LiveData 는 생명주기 라이브러리 클래스로, DB 업데이트마다 갱신되어 항상 최신 데이터로 자동으로 교체된다.
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
