package product.miyabi.android.leveldb.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
	

	TextView[] mLabel;
	int NO_LABEL  =2;
	static final int ID_OP_SINGLE=0;
	static final int ID_OP_MULTI=1;
	String mLabelText[];
	
	Button[] mButtons;
	int NO_BUTTONS=4;
	static final int ID_PUT_DATA=0;
	static final int ID_GET_DATA=1;
	static final int ID_PUT_DATAS=2;
	static final int ID_GET_DATAS=3;
	String mBtnText[]; 
	Activity mActivity;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        int [] vsn = Database.VERSION();
        mDatabase = new Database();
        Options options = new Options();
        Status status = mDatabase.Open(options, "/sdcard/leveldb.db");
        Toast.makeText(getApplicationContext(), "LevelDB Version:"+vsn[0]+"."+vsn[1],Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), status.toString(),Toast.LENGTH_SHORT).show();
        Log.i("LevelDB","Path");
        
        mLabel     = new TextView[NO_LABEL];
        mLabel[ID_OP_SINGLE] = (TextView) findViewById(R.id.textView2);
        mLabel[ID_OP_MULTI] = (TextView) findViewById(R.id.textView3);
        mLabelText = getResources().getStringArray(R.array.itemlabel);
        for(int i=0;i<NO_LABEL;i++){
        	mLabel[i].setText(mLabelText[i]);
        }
        
        
        mButtons = new Button[NO_BUTTONS];
        mButtons[ID_PUT_DATA] = (Button) findViewById(R.id.button1);
        mButtons[ID_GET_DATA] = (Button) findViewById(R.id.button2);
        mButtons[ID_PUT_DATAS] = (Button) findViewById(R.id.button3);
        mButtons[ID_GET_DATAS] = (Button) findViewById(R.id.button4);
        mBtnText = getResources().getStringArray(R.array.btntext);

        for(int i=0;i<NO_BUTTONS;i++){
        	mButtons[i].setText(mBtnText[i]);
        }
        
        mButtons[ID_PUT_DATA].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteOptions opts = new WriteOptions();
				String key = "Hello";
				String value = "World";
				
				Status status= mDatabase.Put(opts, key, value);
				Toast.makeText(getApplicationContext(), status.toString()+" PUT("+key+","+value+")",Toast.LENGTH_SHORT).show();
				
			}
		});
        
        mButtons[ID_GET_DATA].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ReadOptions opts = new ReadOptions();
				String key = "Hello";
				String[] value = new String[1];
				Status status= mDatabase.Get( key,value);
		        Toast.makeText(getApplicationContext(), status.toString() +":"+value[0],Toast.LENGTH_SHORT).show();
				
			}
		});
        
        mButtons[ID_PUT_DATAS].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new PutsTask(mActivity).execute(mDatabase);
			}
		});
        mButtons[ID_GET_DATAS].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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