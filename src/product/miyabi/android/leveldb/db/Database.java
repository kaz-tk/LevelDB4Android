package product.miyabi.android.leveldb.db;

import product.miyabi.android.leveldb.db.options.Options;

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
	
	private native Status OpenNative(Options options,String dbname);
	
}
