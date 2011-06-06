package product.miyabi.android.leveldb.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.util.Log;
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
import product.miyabi.android.leveldb.sample.task.BaseTask.TaskFinishedCallback;
import product.miyabi.android.leveldb.sample.task.GetTask;
import product.miyabi.android.leveldb.sample.task.PutTask;
public class LevelDBSample extends Activity 
implements TaskFinishedCallback
{
	Database mDatabase;

	//---------------------------------
	// Database Info Area
	//---------------------------------
	TextView[] mDBInfoLabel;
	int NO_INFO=2;
	static final int ID_INFO_VERSION=0;
	static final int ID_INFO_STATS=1;
	
	//---------------------------------
	// Operation Area
	//---------------------------------

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

	//---------------------------------

	
	Handler mHandler = new Handler();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

		        mDatabase = new Database();
		        Options options = new Options();
		        Status status = mDatabase.Open(options, "/sdcard/leveldb.db");
				//---------------------------------
				// Database Info Area
				//---------------------------------
		        String prop[] =new String[1];

		        mDBInfoLabel = new TextView[NO_INFO];
		        mDBInfoLabel[ID_INFO_VERSION] = (TextView) findViewById(R.id.tvVersion);
		        mDBInfoLabel[ID_INFO_STATS] = (TextView) findViewById(R.id.tvStats);
				int [] vsn = Database.VERSION();
				
				mDBInfoLabel[ID_INFO_VERSION].setText(vsn[0]+"."+vsn[1]);
		        mDatabase.GetProperty("leveldb.stats", prop);
				mDBInfoLabel[ID_INFO_STATS].setText(prop[0]);
		        
		    	//---------------------------------
		    	// Operation Area
		    	//---------------------------------
		        
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
						new PutTask(mActivity).execute(mDatabase);
					}
				});
		        mButtons[ID_GET_DATAS].setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GetTask task = new GetTask(mActivity);
						task.execute(mDatabase);
						
					}
				});
		 				
			}
		},10);
        
   }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mDatabase.Release("/sdcard/leveldb.db");
    }

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub
		String[] prop = new String[1];
		prop[0]= "";
        mDatabase.GetProperty("leveldb.stats", prop);
		mDBInfoLabel[ID_INFO_STATS].setText(prop[0]);
		Log.v("LevelDB4Android", prop[0]);
	}
}