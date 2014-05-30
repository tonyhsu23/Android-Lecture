package main.activity;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private final static int dbVersion = 1;
	private final static String dbName = "Member.db";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, dbName, factory, dbVersion, errorHandler);
	}

	public DBHelper(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String member = "CREATE TABLE Member( " +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT NOT NULL, "+
				"email TEXT NOT NULL);";
		db.execSQL(member);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS member");
		onCreate(db);
	}
}
