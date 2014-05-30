package main.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SqliteActivity extends Activity{
	
	DBHelper helper = new DBHelper(this);
	SQLiteDatabase db;	
	ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlite);
		
		retrieve();	
		
		findViewById(R.id.create).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				create();
				Intent intent = new Intent(SqliteActivity.this, SqliteActivity.class);
            	startActivity(intent);
            	finish();
			}
		});
		
		findViewById(R.id.update).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				update();
				Intent intent = new Intent(SqliteActivity.this, SqliteActivity.class);
            	startActivity(intent);
            	finish();
			}
		});
		
		findViewById(R.id.delete).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				delete();
				Intent intent = new Intent(SqliteActivity.this, SqliteActivity.class);
            	startActivity(intent);
            	finish();
			}
		});
		
		findViewById(R.id.go_back).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SqliteActivity.this, MainActivity.class);
            	startActivity(intent);
            	finish();
			}
		});
	}
	
	private void create(){		
		EditText name_edit = (EditText) findViewById(R.id.name_create);
		EditText email_edit = (EditText) findViewById(R.id.email_create);
		
		String name = name_edit.getText().toString();
		String email = email_edit.getText().toString();
		
		db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("email", email);
		db.insert("Member", null, cv);
	}
	
	private void retrieve(){
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("Select * From Member", null); // table, args
		
		String[] names = new String[cursor.getCount()];
		String[] mails = new String[cursor.getCount()];
		int rows_num = cursor.getCount();
		
		if(rows_num != 0) {
			cursor.moveToFirst();   			 
			for(int i=0; i<rows_num; i++) {
				names[i] = cursor.getString(1);
				mails[i] = cursor.getString(2);
				cursor.moveToNext();
			}
			ListView nameList = (ListView) findViewById(R.id.names);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
					android.R.layout.simple_list_item_1, names);
			nameList.setAdapter(adapter);
			
			ListView mailList = (ListView) findViewById(R.id.emails);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, 
					android.R.layout.simple_list_item_1, mails);
			mailList.setAdapter(adapter2);
		 }				
	}
	
	private void update(){		
		EditText name_edit_prev = (EditText) findViewById(R.id.name_update_prev);
		EditText name_edit_new = (EditText) findViewById(R.id.name_update_new);
		
		String name_prev = name_edit_prev.getText().toString();
		String name_new = name_edit_new.getText().toString();
		
		db = helper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("name", name_new);
		db.update("Member", cv, "name = ?", new String[]{name_prev}); //table, cv, where clause, where args		
	}
	
	private void delete(){
		EditText name_edit = (EditText) findViewById(R.id.name_delete);
		String name = name_edit.getText().toString();
		
		db = helper.getWritableDatabase();
		db.delete("Member", "name = ?", new String[]{name}); //table, where clause, where args
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
