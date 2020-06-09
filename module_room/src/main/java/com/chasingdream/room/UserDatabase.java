package com.chasingdream.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * 升级：ps https://www.jianshu.com/p/fda33acb7515
 * https://www.cnblogs.com/best-hym/p/12259615.html
 */
//                    表名         数据库版本     不添加会警告
@Database(entities = {User.class,Book.class}, version = 3, exportSchema = true)
public abstract class UserDatabase extends RoomDatabase {
 
    private static final String DB_NAME = "UserDatabase.db";
    private static volatile UserDatabase instance;
 
    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }
 
    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .build();
    }
 
    public abstract UserDao getUserDao();

    /**
     * 数据库版本 1->2 user表格新增了like列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user " + " ADD COLUMN likeAnima TEXT");
        }
    };

    /**
     * 数据库版本 1->2 user表格新增了like列
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `userId` INTEGER, 'time' TEXT)");
        }
    };
}