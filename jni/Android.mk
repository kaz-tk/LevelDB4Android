# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#

LOCAL_PATH := $(call my-dir)

# ========================================================
# Build Static Library (Port)
# ========================================================

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION:= cc
LOCAL_MODULE:=leveldb-port

PORT_SRC_FILES:= port_android.cc \
                 sha1_portable.cc

LOCAL_SRC_FILES=$(PORT_SRC_FILES:%=../port/%)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../platforms/android-9/arch-arm/usr/include/ \
										$(LOCAL_PATH)/../ \
										$(LOCAL_PATH)/../include/ \
										$(LOCAL_PATH)/../port/ \
										$(LOCAL_PATH)/../util/ \
										$(LOCAL_PATH)/../db/ \
										$(LOCAL_PATH)/../table/

LOCAL_CFLAGS += $(LOCAL_C_INCLUDES:%=-I%) \
	-DLEVELDB_PLATFORM_ANDROID \
	-DARMV6_OR_7

LOCAL_STATIC_LIBS:= -lstdlib

include $(BUILD_STATIC_LIBRARY)

# ========================================================
# Build Static Library (Util)
# ========================================================

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION:= cc
LOCAL_MODULE:=leveldb-util

UTIL_SRC_FILES:= \
	arena.cc \
	cache.cc \
	coding.cc \
	comparator.cc \
	crc32c.cc \
	env.cc \
	env_posix.cc \
	hash.cc \
	histogram.cc \
	logging.cc \
	options.cc \
	status.cc \
	testharness.cc \
	testutil.cc


LOCAL_SRC_FILES=$(UTIL_SRC_FILES:%=../util/%)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../platforms/android-9/arch-arm/usr/include/ \
										$(LOCAL_PATH)/../ \
										$(LOCAL_PATH)/../include/ \
										$(LOCAL_PATH)/../port/ \
										$(LOCAL_PATH)/../util/ \
										$(LOCAL_PATH)/../db/ \
										$(LOCAL_PATH)/../table/

LOCAL_CFLAGS += $(LOCAL_C_INCLUDES:%=-I%) \
	-DLEVELDB_PLATFORM_ANDROID \
	-DARMV6_OR_7

LOCAL_STATIC_LIBS:= -lstdlib
LOCAL_STATIC_LIBRARIES:= leveldb-port

include $(BUILD_STATIC_LIBRARY)

# ========================================================
# Build Static Library (Table)
# ========================================================

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION:= cc
LOCAL_MODULE:=leveldb-table

TABLE_SRC_FILES:= table_builder.cc \
	block.cc \
	block_builder.cc \
	format.cc \
	iterator.cc \
	merger.cc \
	table.cc \
	two_level_iterator.cc 


LOCAL_SRC_FILES=$(TABLE_SRC_FILES:%=../table/%)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../platforms/android-9/arch-arm/usr/include/ \
										$(LOCAL_PATH)/../ \
										$(LOCAL_PATH)/../include/ \
										$(LOCAL_PATH)/../port/ \
										$(LOCAL_PATH)/../util/ \
										$(LOCAL_PATH)/../db/ \
										$(LOCAL_PATH)/../table/

LOCAL_CFLAGS += $(LOCAL_C_INCLUDES:%=-I%) \
	-DLEVELDB_PLATFORM_ANDROID \
	-DARMV6_OR_7

LOCAL_STATIC_LIBS:= -lstdlib

LOCAL_STATIC_LIBRARIES:= leveldb-port leveldb-util
include $(BUILD_STATIC_LIBRARY)

# ========================================================
# Build Static Library (DB)
# ========================================================

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION:= cc
LOCAL_MODULE:= leveldb-db


#DB_SRC_FILES:= builder.cc \
#									db_test.cc \
#									log_reader.cc \
#									version_set.cc \
#									dbformat.cc \
#									skiplist_test.cc \
#									corruption_test.cc \
#									log_test.cc \
#									write_batch.cc \
#									db_bench.cc \
#									dbformat_test.cc \
#									log_writer.cc \
#									table_cache.cc \
#									db_impl.cc \
#									filename.cc \
#									write_batch_test.cc \
#									memtable.cc \
#									version_edit.cc \
#									db_iter.cc \
#									filename_test.cc \
#									repair.cc \
#									version_edit_test.cc

DB_SRC_FILES:=builder.cc \
							db_impl.cc \
							db_iter.cc \
							filename.cc \
							log_reader.cc \
							log_writer.cc \
							memtable.cc \
							repair.cc \
							table_cache.cc \
							dbformat.cc \
							version_edit.cc \
							version_set.cc \
							write_batch.cc 

#							db_bench.cc \


LOCAL_SRC_FILES=$(DB_SRC_FILES:%=../db/%)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../platforms/android-9/arch-arm/usr/include/ \
										$(LOCAL_PATH)/../ \
										$(LOCAL_PATH)/../include/ \
										$(LOCAL_PATH)/../port/ \
										$(LOCAL_PATH)/../util/ \
										$(LOCAL_PATH)/../db/ \
										$(LOCAL_PATH)/../table/

LOCAL_CFLAGS += $(LOCAL_C_INCLUDES:%=-I%) \
	-DLEVELDB_PLATFORM_ANDROID \
	-DARMV6_OR_7

LOCAL_STATIC_LIBS:= -lstdlib 
LOCAL_STATIC_LIBRARIES:= leveldb-port leveldb-util

include $(BUILD_STATIC_LIBRARY)


# ========================================================
# Build Shared Library 
# ========================================================

include $(CLEAR_VARS)

LOCAL_MODULE:= leveldb-java

JNI_SRC_FILES:= \
	database.cpp \
	jnihelper.cpp 


LOCAL_SRC_FILES:=$(JNI_SRC_FILES) 

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../platforms/android-9/arch-arm/usr/include/ \
	$(LOCAL_PATH)    \
	$(LOCAL_PATH)/../ \
	$(LOCAL_PATH)/../include/ \
	$(LOCAL_PATH)/../port/ \
	$(LOCAL_PATH)/../util/ \
	$(LOCAL_PATH)/../db/ \
	$(LOCAL_PATH)/../table/


LOCAL_CPPFLAGS += $(LOCAL_C_INCLUDES:%=-I%) \
	-DLEVELDB_PLATFORM_ANDROID \
	-DARMV6_OR_7

#LOCAL_SHARED_LIBRARIES :=
LOCAL_LDLIBS:=  -llog

LOCAL_STATIC_LIBS:= -lstdlib -llog
LOCAL_STATIC_LIBRARIES:=  leveldb-db leveldb-table leveldb-util leveldb-port  

include $(BUILD_SHARED_LIBRARY)

