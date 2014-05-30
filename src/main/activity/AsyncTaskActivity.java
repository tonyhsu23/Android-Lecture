package main.activity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncTaskActivity extends Activity {
	
	ProgressDialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctask);
		
		findViewById(R.id.back_btn).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AsyncTaskActivity.this, MainActivity.class);
            	startActivity(intent);
            	finish();
			}
		});
		
		try {
			new exampleAsyncTask().execute().get(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			Toast.makeText(AsyncTaskActivity.this, "連線逾時...", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
	}
	
	private class exampleAsyncTask extends AsyncTask<Void, Void, Void>{
		
		protected void onPreExecute(){
			dialog = ProgressDialog.show(AsyncTaskActivity.this, "", "Loading...", true);
			Log.w("onPreExecute tag", "onPreExecute msg");
			Toast.makeText(AsyncTaskActivity.this, "onPreExecute...", Toast.LENGTH_SHORT).show();
			TextView preText = (TextView) findViewById(R.id.on_pre_execute);
			preText.setText("onPreExecute finished.");
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Log.w("doInBackground tag", "doInBackground msg");
			publishProgress();	//onProgressUpdate		
			return null;
		}
		
		protected void onProgressUpdate(Void... progress){
			Log.w("onProgressUpdate tag", "onProgressUpdate msg");
			Toast.makeText(AsyncTaskActivity.this, "onProgressUpdate...", Toast.LENGTH_SHORT).show();
			TextView progressText = (TextView) findViewById(R.id.on_progress_update);
			progressText.setText("onProgressUpdate finished.");
		}
		
		protected void onPostExecute(Void result){
			dialog.dismiss();
			Log.w("onPostExecute tag", "onPostExecute msg");
			Toast.makeText(AsyncTaskActivity.this, "onPostExecute...", Toast.LENGTH_SHORT).show();
			TextView postText = (TextView) findViewById(R.id.on_post_execute);
			postText.setText("onPostExecute finished.");
		}		
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
