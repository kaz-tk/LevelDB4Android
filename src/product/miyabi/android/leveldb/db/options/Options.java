package product.miyabi.android.leveldb.db.options;

public class Options {
	enum CompressionType{
		NO_COMPRESSION,
		SNAPPY_COMPRESSION
	};

	//TODO どうやって扱うか？
	// ptr_comparator 
	// MyComparatorにして、扱いを決めてもらう。
	
	
	boolean mCreateMissing = false;
	boolean mRaiseError    = false;
	boolean mParanoidCheck = false;
	
	//TODO 扱いがわからない。
	// Env 
	
	// TODO　WritableFileはファイル名取っておけば大丈夫かな？
	// String mInfoLog
	
	//TODO intで大丈夫か？
	int mWriteBufferSize;
	
	int mFileCount = 1000;
	
	//TODO どうやって扱うか？
	//prt_cache
	
	// Do not Change ?
	int mBlockRestartInterval = 16;
	
	CompressionType mCompressionType = CompressionType.SNAPPY_COMPRESSION;
}
