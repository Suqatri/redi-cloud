package net.suqatri.cloud.api.service.configuration;

import net.suqatri.cloud.api.redis.bucket.IRBucketHolder;
import net.suqatri.cloud.api.service.ICloudService;
import net.suqatri.cloud.api.service.ServiceEnvironment;
import net.suqatri.cloud.commons.function.future.FutureAction;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IServiceStartConfiguration {

    ServiceEnvironment getEnvironment();
    String getJavaCommand();

    UUID getNodeId();
    void setNodeId(UUID nodeId);

    String getName();
    UUID getUniqueId();

    String getServiceVersionName();
    void setServiceVersionName(String serviceVersionName);

    int getId();
    void setId(int id);

    int getMaxMemory();

    default int getStartPort(){
        return -1;
    }

    Collection<UUID> getPossibleNodeIds();

    int getStartPriority();

    boolean isStatic();

    Collection<String> getTemplateNames();

    @Nullable
    String getGroupName();
    boolean isGroupBased();

    List<String> getProcessParameters();
    List<String> getJvmArguments();

    FutureAction<IRBucketHolder<ICloudService>> getStartListener();
    void listenToStart();

}