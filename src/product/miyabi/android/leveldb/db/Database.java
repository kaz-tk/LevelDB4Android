package product.miyabi.android.leveldb.db;

import android.util.Log;
import android.widget.Toast;
import product.miyabi.android.leveldb.db.options.Options;
import product.miyabi.android.leveldb.db.options.ReadOptions;
import product.miyabi.android.leveldb.db.options.WriteOptions;

/**
 * @abstract Database API
 * @author miyabi
 * 
 */
public class Database {

	static {
		System.loadLibrary("leveldb-java");
	}
	
	String mDatabaseName;

	public Database(){
		
	}
	
	public static native int[] VERSION();
	
	public Status Open(
			Options options,
			String dbname
	){
		
		return OpenNative(options, dbname);
	}

	public void Release(String dbname){
		ReleaseNative(dbname);
	}

	/**
	 * 
	 * @param IN key
	 * @param IN value
	 * @return
	 */
	public Status Put(String key,String value){
		return PutNative(new WriteOptions(),mDatabaseName, key, value);
	}
	
	
	/**
	 * 
	 * @param IN writeopts
	 * @param IN key
	 * @param IN value
	 * @return
	 */
	public Status Put(WriteOptions writeopts,String key,String value){
		return PutNative(writeopts,mDatabaseName, key, value);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Status Get(String key,String[] value){
		return Get(new ReadOptions(),key,value);
	}
	
	/**
	 * 
	 * @param IN readopts
	 * @param IN key
	 * @param OUT value
	 * @return Status Database Operation Result
	 */
	public Status Get(ReadOptions readopts,String key,String[] values){
		
		Status status = GetNative(readopts,mDatabaseName, key,values);
		Log.i("LevelDB",values[0]);
		 
		return status;
	}
	
	
	//-------------------------------------------
	// Native Method
	//-------------------------------------------
	private native Status OpenNative(Options options,String dbname);
	private native void ReleaseNative(String dbname);

	private native Status PutNative(WriteOptions writeopts,String dbname,String key,String value);
	private native Status GetNative(ReadOptions readopts,  String dbname,String key, String[] value);
	private native Status DeleteNative(String dbname,String key,String value);
}
