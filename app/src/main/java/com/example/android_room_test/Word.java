package com.example.android_room_test;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// **** SQLite 에서 테이블을 구성하는 클래스 **** //

@Entity(tableName = "word_table") // 테이블 이름 설정하는 어노테이션, 설정하지 않으면 테이블 이름은 클래스 이름으로 설정
public class Word {
    @PrimaryKey // 기본키 어노테이션
    @NonNull  // not null
    @ColumnInfo(name = "word") // 컬럼 이름 지정하는 어노테이션, 설정하지 않으면 기본적으로 변수명
    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}
}
