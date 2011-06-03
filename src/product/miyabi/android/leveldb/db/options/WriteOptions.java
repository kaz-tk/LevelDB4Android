package product.miyabi.android.leveldb.db.options;

public class WriteOptions {
	boolean mSync = false;

	public WriteOptions() {
		// TODO Auto-generated constructor stub
	}
	public WriteOptions(boolean isSync) {
		mSync = isSync;
	}

	// Move to Database.cpp
	// ptr_post_write_snapshot 

}
