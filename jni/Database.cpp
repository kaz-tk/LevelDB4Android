/*
 * database.cpp
 *
 *  Created on: 2011/05/31
 *      Author: miyabi
 */


#include <product_miyabi_android_leveldb_db_Database.h>
#include <jni.h>
#include <android/log.h>
#include <leveldb/db.h>
#include <Database.h>
#include <jnihelper.h>

leveldb::DB* db_;

JNIEXPORT jintArray JNICALL Java_product_miyabi_android_leveldb_db_Database_VERSION
  (JNIEnv *env, jobject clazz){
	jboolean bools;
	jint jMajorVsn,jiMinorVsn;
	jint* vsnArray;
	jintArray vsn = env->NewIntArray(2);
	vsnArray = env->GetIntArrayElements(vsn,&bools);

	vsnArray[0] = leveldb::kMajorVersion;
	vsnArray[1] = leveldb::kMinorVersion;

	//__android_log_print(ANDROID_LOG_DEFAULT,"LevelDB","%d:%d",leveldb::kMajorVersion,leveldb::kMinorVersion);
	env->ReleaseIntArrayElements(vsn,vsnArray,0);

	return vsn;
}



JNIEXPORT jobject JNICALL Java_product_miyabi_android_leveldb_db_Database_OpenNative
(JNIEnv *env, jobject clazz, jobject options, jstring jdbname){


	jboolean* isCopy;
	const char* dbnamechar = env->GetStringUTFChars(jdbname,isCopy);
	std::string* dbname = new std::string(dbnamechar);
    env->ReleaseStringUTFChars(jdbname,dbnamechar);



	leveldb::Options opts;
    if (options != NULL) {
      //opts = *options;
    	//TODO
        opts.create_if_missing = true;
    } else {
      opts.create_if_missing = true;
    }
    leveldb::Options last_options_ = opts;

    leveldb::Status status = leveldb::DB::Open(opts, *dbname, &db_);



	delete db_;



    return convertStatus(env,status);
}
