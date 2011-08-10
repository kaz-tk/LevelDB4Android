package product.miyabi.android.leveldb.sample;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import product.miyabi.android.leveldb.R;
import product.miyabi.android.leveldb.db.*;
import product.miyabi.android.leveldb.db.options.Options;
import product.miyabi.android.leveldb.db.options.ReadOptions;
import product.miyabi.android.leveldb.db.options.WriteOptions;
public class LevelDBSample extends Activity {
	Database mDatabase;
	File mDbPath;
	String mDbName = "leveldb.db";

	TextView[] mLabel;
	int NO_LABEL  =2;
	static final int ID_OP_SINGLE=0;
	static final int ID_OP_MULTI=1;
	String mLabelText[];
	
	Button[] mButtons;
	int NO_BUTTONS=5;
	static final int ID_PUT_DATA=0;
	static final int ID_GET_DATA=1;
	static final int ID_PUT_DATAS=2;
	static final int ID_WRITE_DATAS=3;
	static final int ID_GET_DATAS=4;
	String mBtnText[]; 
	Activity mActivity;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        mDbPath = Environment.getExternalStorageDirectory();
        
        
        int [] vsn = Database.VERSION();
        mDatabase = new Database();
        Options options = new Options();
        Status status = mDatabase.Open(options, mDbPath+"/"+mDbName);
        Toast.makeText(getApplicationContext(), "LevelDB Version:"+vsn[0]+"."+vsn[1],Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), status.toString(),Toast.LENGTH_SHORT).show();
        Log.i("LevelDB","Path");
        mDatabase.Release(mDbPath+"/"+mDbName);
        
        mLabel     = new TextView[NO_LABEL];
        mLabel[ID_OP_SINGLE] = (TextView) findViewById(R.id.textView2);
        mLabel[ID_OP_MULTI] = (TextView) findViewById(R.id.textView3);
        mLabelText = getResources().getStringArray(R.array.itemlabel);
        for(int i=0;i<NO_LABEL;i++){
        	mLabel[i].setText(mLabelText[i]);
        }
        
        
        mButtons = new Button[NO_BUTTONS];
        mButtons[ID_PUT_DATA] = (Button) findViewById(R.id.putSingle);
        mButtons[ID_GET_DATA] = (Button) findViewById(R.id.getSingle);
        mButtons[ID_PUT_DATAS] = (Button) findViewById(R.id.putMulti);
        mButtons[ID_WRITE_DATAS] = (Button) findViewById(R.id.writeMulti);
        mButtons[ID_GET_DATAS] = (Button) findViewById(R.id.getMulti);

        mBtnText = getResources().getStringArray(R.array.btntext);
        for(int i=0;i<NO_BUTTONS;i++){
        	mButtons[i].setText(mBtnText[i]);
        }
        
        mButtons[ID_PUT_DATA].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Status status = mDatabase.Open(new Options(), "/sdcard/leveldb.db");

				WriteOptions opts = new WriteOptions();
				String key = "Hello";
				String value = "World";
				
				status= mDatabase.Put(opts, key, value);
				Toast.makeText(getApplicationContext(), status.toString()+" PUT("+key+","+value+")",Toast.LENGTH_SHORT).show();
				mDatabase.Release(mDbPath+"/"+mDbName);
			}
		});
        
        mButtons[ID_GET_DATA].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Status status = mDatabase.Open(new Options(), "/sdcard/leveldb.db");
				ReadOptions opts = new ReadOptions();
				String key = "Hello";
				String[] value = new String[1];
				status= mDatabase.Get( key,value);
		        Toast.makeText(getApplicationContext(), status.toString() +":"+value[0],Toast.LENGTH_SHORT).show();
				mDatabase.Release(mDbPath+"/"+mDbName);
				
			}
		});
        
        mButtons[ID_PUT_DATAS].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("LevelDB", "Put Data");
				//new PutsTask(mActivity).execute(mDatabase);
			}
		});
        mButtons[ID_WRITE_DATAS].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				Status status = mDatabase.Open(new Options(), "/sdcard/leveldb.db");
				Log.d("LevelDB",status.toString());
		        Log.d("LevelDB", "Write Data");
				WriteBatch batch = new WriteBatch();
				int i=0;
				for(i=0;i<10;i++){
					String key = String.valueOf(i);
					String value = String.valueOf(i+1000);
					
					Log.d("LevelDB",key+":"+value +" Added");
					batch.put(key, value);
				}
				status= mDatabase.Write(new WriteOptions(), batch);
		        Toast.makeText(getApplicationContext(), status.toString() ,Toast.LENGTH_SHORT).show();
		        mDatabase.Release("/sdcard/leveldb.db");
			}
		});
        mButtons[ID_GET_DATAS].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("LevelDB", "Get Data");
				// TODO Auto-generated method stub
				//new GetsTask(mActivity).task.execute(mDatabase);
				
			}
		});
    }
    
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
    	// TODO Auto-generated method stub
    	menu.add(Menu.NONE, 0, Menu.NONE, "Bench");
    	return super.onMenuOpened(featureId, menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	// TODO Auto-generated method stub
    	switch(featureId){
    		case 0:{
    			startActivity(new Intent(mActivity, product.miyabi.android.leveldb.sample.BenchActivity.class));
    		}
    		default:{
    	    	return super.onMenuItemSelected(featureId, item);
    		}
    	}
    	
    	
    	
    	//return super.onMenuItemSelected(featureId, item);
    }
    
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mDatabase.Release("/sdcard/leveldb.db");
    }
}