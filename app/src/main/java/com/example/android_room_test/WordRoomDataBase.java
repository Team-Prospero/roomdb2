package com.example.android_room_test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Insert;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//**** Room DB 클래스, SQLite DB 상위계층 ****//
@Database(entities = {Word.class}, version = 1, exportSchema = false) // entity = 사용할 엔티티 담겨있는 클래스, version = 버전 구분, exportSchema = 스키마를 외부로 보내는 것을 허용할지 말지
public abstract class WordRoomDataBase extends RoomDatabase {// 무조건 추상클래스에 RoomDatabase 상속받아야 함.

    public abstract WordDAO wordDAO();// 각 DB는 DAO 의 추상 메소드를 이용해 DAO 를 사용한다.

    private static volatile WordRoomDataBase INSTANCE; // 싱글톤 패턴으로 만들어진 DB 인스턴스 (volatile 키워드로 변수를 캐시에 저장하지 않고 메인 메모리에 R/W 한다.)
    private static final int NUMBER_OF_THREADS = 4; // 각 쿼리는 서로 다른 쓰레드에서 실행되어야 한다.
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS); // DB 접근하여 사용하기 위한 백그라운드 쓰레드 (고정된 쓰레드 갯수)

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDAO dao = INSTANCE.wordDAO();
                dao.deleteAll();

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };

    static WordRoomDataBase getDatabase(final Context context) {

        if (INSTANCE == null) { // DB 접근 시
            synchronized (WordRoomDataBase.class) { // DB 싱크
                if (INSTANCE == null) { // 처음 DB 접근시 DB 생성
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDataBase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

}
