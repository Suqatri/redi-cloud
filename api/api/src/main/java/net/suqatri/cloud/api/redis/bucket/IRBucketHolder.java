package net.suqatri.cloud.api.redis.bucket;

import net.suqatri.cloud.commons.function.future.FutureAction;

public interface IRBucketHolder<T> {

    T getObject(boolean force);
    FutureAction<T> getObjectAsync(boolean force);

    IRBucketHolder<T> update(T object);
    FutureAction<IRBucketHolder<T>> updateAsync(T object);

    void unlink();

    void mergeChanges(String json);

    String getRedisKey();
    String getRedisPrefix();
    String getIdentifier();

}