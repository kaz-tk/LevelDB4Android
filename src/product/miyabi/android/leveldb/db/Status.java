package product.miyabi.android.leveldb.db;


/**
 * @abstract Handle Database Status
 * @author miyabi
 *
 */
public class Status {
	private int mByteLengthMsg;
	private int mDatabaseStatusCode;
	private String mMsg;
	
	public enum Code{
		OK,
		NOTFOUND,
		CORRUPTION,
		NOTSUPPORTED,
		INVALIDARGUMENT,
		IOERROR
	}
	
	
	private Status(String msg){
		//mDatabaseStatusCode = statusCode;
		mMsg                = msg;
	}

	public Status factory( int statuscode , String msg){
		return new Status( msg );
	}
	
	/**
	 * @abstract Copy Constructor
	 * @param status
	 * @return 
	 * @throws CloneNotSupportedException 
	 */
	public  Status(Status status) throws CloneNotSupportedException{
		
	}
	
	
	
}
