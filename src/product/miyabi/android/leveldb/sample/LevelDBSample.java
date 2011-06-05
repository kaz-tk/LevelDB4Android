package product.miyabi.android.leveldb.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import product.miyabi.android.leveldb.R;
import product.miyabi.android.leveldb.db.*;
import product.miyabi.android.leveldb.db.options.Options;
import product.miyabi.android.leveldb.db.options.ReadOptions;
import product.miyabi.android.leveldb.db.options.WriteOptions;
public class LevelDBSample extends Activity {
	Database mDatabase;
	
	Button[] mButtons;
	int NO_BUTTONS=2;
	static final int ID_PUT_DATA=0;
	static final int ID_GET_DATA=1;
	String mBtnText[]; 
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        int [] vsn = Database.VERSION();
        mDatabase = new Database();
        Options options = new Options();
        Status status = mDatabase.Open(options, "/sdcard/leveldb.db");
        Toast.makeText(getApplicationContext(), "LevelDB Version:"+vsn[0]+"."+vsn[1],Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), status.toString(),Toast.LENGTH_SHORT).show();
        Log.i("LevelDB","Path");
        
        mButtons = new Button[NO_BUTTONS];
        mButtons[ID_PUT_DATA] = (Button) findViewById(R.id.button1);
        mButtons[ID_GET_DATA] = (Button) findViewById(R.id.button2);
        mBtnText = getResources().getStringArray(R.array.btntext);
        
        mButtons[ID_PUT_DATA].setText(mBtnText[ID_PUT_DATA]);
        mButtons[ID_GET_DATA].setText(mBtnText[ID_GET_DATA]);
        
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
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mDatabase.Release("/sdcard/leveldb.db");
    }
}