package ru.myitschool.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase {
    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_GAME_NAME = "tableGames";

    private static final String COLUMN_GAME_ID = "id";
    private static final String COLUMN_GAME_NAME = "gameName";

    private static final int NUM_COLUMN_GAME_ID = 0;
    private static final int NUM_COLUMN_GAME_NAME = 1;

    private static final String TABLE_REVIEW_NAME = "tableReviews";

    private static final String COLUMN_REVIEW_ID = "reviewId";
    private static final String COLUMN_REVIEW_TEXT = "text";
    private static final String COLUMN_REVIEW_MARK = "mark";
    private static final String COLUMN_REVIEW_GAME_ID = "gameId";

    private static final int NUM_COLUMN_REVIEW_ID = 0;
    private static final int NUM_COLUMN_REVIEW_TEXT = 1;
    private static final int NUM_COLUMN_REVIEW_MARK = 2;
    private static final int NUM_COLUMN_REVIEW_GAME_ID = 3;

    private SQLiteDatabase mDataBase;

    public DataBase(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insertGame(String game_name) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_GAME_NAME, game_name);
        return mDataBase.insert(TABLE_GAME_NAME, null, cv);
    }

    public long insertReview(String text, int mark, long game_id) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_REVIEW_TEXT, text);
        cv.put(COLUMN_REVIEW_MARK, mark);
        cv.put(COLUMN_REVIEW_GAME_ID, game_id);
        return mDataBase.insert(TABLE_REVIEW_NAME, null, cv);
    }

    public ArrayList<GamesBD> selectAllGames() {
        Cursor mCursor = mDataBase.query(TABLE_GAME_NAME, null, null, null, null, null, null);

        ArrayList<GamesBD> arr = new ArrayList<GamesBD>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_REVIEW_ID);
                String GameName = mCursor.getString(NUM_COLUMN_GAME_NAME);
                arr.add(new GamesBD(id, GameName));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<ReviewsBD> selectAllReviews(long game_id) {
        Cursor mCursor = mDataBase.query(TABLE_REVIEW_NAME, null, COLUMN_REVIEW_GAME_ID + " = ?", new String[]{String.valueOf(game_id)}, null, null, null);

        ArrayList<ReviewsBD> arr = new ArrayList<ReviewsBD>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long ReviewId = mCursor.getLong(NUM_COLUMN_GAME_ID);
                String Text = mCursor.getString(NUM_COLUMN_REVIEW_TEXT);
                int Mark = mCursor.getInt(NUM_COLUMN_REVIEW_MARK);
                long GameId = mCursor.getLong(NUM_COLUMN_REVIEW_GAME_ID);
                arr.add(new ReviewsBD(ReviewId, Text, Mark, GameId));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public double selectAverageMark(long game_id) {
        Cursor mCursor = mDataBase.query(TABLE_REVIEW_NAME, new String[]{"AVG("+COLUMN_REVIEW_MARK+")"}, COLUMN_REVIEW_GAME_ID + " = ?", new String[]{String.valueOf(game_id)}, COLUMN_REVIEW_GAME_ID, null, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            return mCursor.getDouble(0);
        }
        return 0;
    }

    public void deleteAllGames() {
        mDataBase.delete(TABLE_GAME_NAME, null, null);
    }

    public void deleteAllReviews() {
        mDataBase.delete(TABLE_REVIEW_NAME, null, null);
    }

    public String selectGame(long id) {
        Cursor mCursor = mDataBase.query(TABLE_GAME_NAME, null, COLUMN_GAME_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String GameName = mCursor.getString(NUM_COLUMN_GAME_NAME);
        return GameName;
    }

    public boolean containsGameWithName(String gameName) {
        Cursor mCursor = mDataBase.query(TABLE_GAME_NAME, new String[]{"COUNT(*)"}, COLUMN_GAME_NAME + " = ?", new String[]{String.valueOf(gameName)}, null, null, null);
        mCursor.moveToFirst();
        return mCursor.getInt(0)>0;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query_game = "CREATE TABLE " + TABLE_GAME_NAME + " (" +
                    COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GAME_NAME + " TEXT);";
            db.execSQL(query_game);
            String query_review = "CREATE TABLE " + TABLE_REVIEW_NAME + " (" +
                    COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REVIEW_TEXT + " TEXT, " +
                    COLUMN_REVIEW_MARK + " INT, " +
                    COLUMN_REVIEW_GAME_ID + " INT," +
                    "FOREIGN KEY (" + COLUMN_REVIEW_ID + ") REFERENCES " + TABLE_GAME_NAME + "(" + COLUMN_GAME_ID + ") ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";
            db.execSQL(query_review);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_NAME + ";");
            onCreate(db);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW_NAME + ";");
            onCreate(db);
        }
    }

}
