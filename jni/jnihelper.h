#include <jni.h>
#include <leveldb/db.h>

#ifndef _Included_JNIHelper
#define _Included_JNIHelper
#ifdef __cplusplus
extern "C" {
#endif

leveldb::Options convertOptions(jobject options);
leveldb::Options convertWriteOptions(jobject options);
leveldb::Options convertReadOptions(jobject options);

jobject convertStatus(JNIEnv* env,leveldb::Status status);


#ifdef __cplusplus

}
#endif
#endif
