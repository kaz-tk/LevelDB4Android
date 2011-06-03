#include <jni.h>
#include <leveldb/db.h>
#include <jnihelper.h>


leveldb::Options convertOptions(jobject options);
leveldb::Options convertWriteOptions(jobject options);
leveldb::Options convertReadOptions(jobject options);

jobject convertStatus(JNIEnv* env,leveldb::Status status){
	jobject jstatus;

	bool is_dbopen_ok = status.ok();
	std::string strStatus =status.ToString();
    const char * charStatus = strStatus.c_str();
    jstring jstrStatus = env->NewStringUTF(charStatus);

	jclass jstatuscls = env->FindClass("product/miyabi/android/leveldb/db/Status");
//	jclass jstatuscls = env->FindClass("Lproduct/miyabi/android/leveldb/db/Status;");
	jmethodID jmethodIdInit;
	if (jstatuscls==NULL ){
		return NULL;
	}
	/*
	jmethodIdInit =  env->GetMethodID(jstatuscls,"factory","(Ljava/lang/String;)Lproduct/miyabi/android/leveldb/db/Status");
	jstatus = env->CallStaticObjectMethod(jstatuscls,jmethodIdInit,jstrStatus);
	*/
	jmethodIdInit =  env->GetMethodID(jstatuscls,"<init>","(Ljava/lang/String;)V");
	if(jmethodIdInit==NULL){
		return NULL;
	}
	jstatus = env->NewObject(jstatuscls,jmethodIdInit,jstrStatus);
	return jstatus;
}
