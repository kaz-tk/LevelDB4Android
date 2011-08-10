#include <jni.h>
#include <leveldb/db.h>
#include <leveldb/write_batch.h>
#include <jnihelper.h>
#include <string>



leveldb::Options convertOptions(jobject options);
leveldb::Options convertWriteOptions(jobject options);
leveldb::Options convertReadOptions(jobject options);

int convertCode(leveldb::Status status){
	if(status.ok()){
		return 0;	// leveldb::Code::kOk
	}else if(status.IsNotFound()){
		return 1;

	}else{//TODO
		return 2;
	}
}

jobject convertStatus(JNIEnv* env,leveldb::Status status){
	jobject jstatus;

	bool is_dbopen_ok = status.ok();
	std::string strStatus =status.ToString();
    const char * charStatus = strStatus.c_str();
    jstring jstrStatus = env->NewStringUTF(charStatus);

	jclass jstatuscls = env->FindClass("product/miyabi/android/leveldb/db/Status");
	/*
	jmethodID jmethodIdFactory;
	if (jstatuscls==NULL ){
		return NULL;
	}

	jmethodIdFactory =  env->GetMethodID(jstatuscls,"factory","(ILjava/lang/String;)Lproduct/miyabi/android/leveldb/db/Status;");
	if(jmethodIdFactory ==NULL){
		return NULL;
	}
	jstatus = env->CallStaticObjectMethod(jstatuscls,jmethodIdFactory,convertCode(status),jstrStatus);
	*/
	jmethodID jmethodIdInit =  env->GetMethodID(jstatuscls,"<init>","(ILjava/lang/String;)V");
	if(jmethodIdInit==NULL){
		return NULL;
	}
	jstatus = env->NewObject(jstatuscls,jmethodIdInit,convertCode(status),jstrStatus);
	return jstatus;
}


/**
 *
 */
leveldb::WriteBatch* convertWriteBatch(JNIEnv* env,jobject jbatch){

	leveldb::WriteBatch* nbatch = new leveldb::WriteBatch();

	jclass clzBatchSerializer = env->GetObjectClass(jbatch);

	jmethodID midGetSize = env->GetMethodID(clzBatchSerializer,"getSize","()I");
	jmethodID midGetKeys = env->GetMethodID(clzBatchSerializer,"getKeys","()[Ljava/lang/String;");
	jmethodID midGetValues = env->GetMethodID(clzBatchSerializer,"getValues","()[Ljava/lang/String;");

	jint         jsize   = env->CallIntMethod(jbatch,midGetSize);
	jobjectArray jKeys   = (jobjectArray)env->CallObjectMethod(jbatch,midGetKeys  ,NULL);
	jobjectArray jValues = (jobjectArray)env->CallObjectMethod(jbatch,midGetValues,NULL);


	// TODO case of NULL key/value
	for(int i=0;i<jsize;i++){

		jstring jkey = (jstring)env->GetObjectArrayElement(jKeys,i);
		jstring jvalue = (jstring)env->GetObjectArrayElement(jValues,i);

		//convert JString to char*
		std::string key   = env->GetStringUTFChars(jkey,  NULL);
		std::string value =  env->GetStringUTFChars(jvalue,NULL);

		const leveldb::Slice sliceKey   = key;
		const leveldb::Slice sliceValue = value;
		nbatch->Put(sliceKey,sliceValue);

		//env->ReleaseStringUTFChars(jkey,key);
		//env->ReleaseStringUTFChars(jvalue,value);
	}

	return nbatch;
}
