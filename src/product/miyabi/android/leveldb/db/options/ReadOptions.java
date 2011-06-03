package product.miyabi.android.leveldb.db.options;


/**
 * 
 * @author miyabi
 * Default Value:
 *   Verify Checksum : false
 *   Fill Cache      : true
 *   
 */
public class ReadOptions {
	boolean mVerifyCheckSum = false;
	boolean mFillcache      = true;
	
	//Move to Database.cpp
	// ptr_snapshot
	
	public ReadOptions(){
		
	}
	
	public ReadOptions(boolean verifyChecksum,boolean fillCache) {
		mVerifyCheckSum = verifyChecksum;
		mFillcache = fillCache;
		
	}
	
}
