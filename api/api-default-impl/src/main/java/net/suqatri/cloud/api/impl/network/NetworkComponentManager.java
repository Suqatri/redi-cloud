package net.suqatri.cloud.api.impl.network;

import net.suqatri.cloud.api.CloudAPI;
import net.suqatri.cloud.api.impl.node.CloudNode;
import net.suqatri.cloud.api.network.INetworkComponentInfo;
import net.suqatri.cloud.api.network.INetworkComponentManager;
import net.suqatri.cloud.api.network.NetworkComponentType;
import net.suqatri.cloud.api.node.ICloudNode;
import net.suqatri.cloud.api.redis.bucket.IRBucketHolder;
import net.suqatri.cloud.api.service.ICloudService;
import net.suqatri.cloud.commons.function.future.FutureAction;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class NetworkComponentManager implements INetworkComponentManager {

    private final ConcurrentHashMap<String, INetworkComponentInfo> cachedNodes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, INetworkComponentInfo> cachedServices = new ConcurrentHashMap<>();

    @Override
    public INetworkComponentInfo getComponentInfo(String key) {
        NetworkComponentType type = key.startsWith("node@") ? NetworkComponentType.NODE : NetworkComponentType.SERVICE;
        String identifier = key.substring(type.name().length() + 1);
        return this.getComponentInfo(type, identifier);
    }

    @Override
    public INetworkComponentInfo getComponentInfo(NetworkComponentType type, String identifier) {
        if(type == NetworkComponentType.NODE && this.cachedNodes.containsKey(identifier)) return this.cachedNodes.get(identifier);
        if(type == NetworkComponentType.SERVICE && this.cachedServices.containsKey(identifier)) return this.cachedServices.get(identifier);
        return new NetworkComponentInfo(type, identifier);
    }

    @Override
    public INetworkComponentInfo getComponentInfo(ICloudNode node) {
        if(this.cachedNodes.containsKey(node.getUniqueId().toString())) return this.cachedNodes.get(node.getUniqueId().toString());
        return new NetworkComponentInfo(NetworkComponentType.NODE, node.getUniqueId().toString());
    }

    @Override
    public INetworkComponentInfo getComponentInfo(ICloudService service) {
        if(this.cachedServices.containsKey(service.getUniqueId().toString())) return this.cachedServices.get(service.getUniqueId().toString());
        return new NetworkComponentInfo(NetworkComponentType.SERVICE, service.getUniqueId().toString());
    }

    @Override
    public FutureAction<Collection<INetworkComponentInfo>> getAllComponentInfoAsync() {
        FutureAction<Collection<INetworkComponentInfo>> futureAction = new FutureAction<>();

        CloudAPI.getInstance().getNodeManager().getNodesAsync()
                .onFailure(futureAction)
                .onSuccess(o -> {
                    Collection<IRBucketHolder<CloudNode>> nodes = (Collection<IRBucketHolder<CloudNode>>) o;
                    for(IRBucketHolder<CloudNode> node : nodes) {
                        this.cachedNodes.put(node.get().getUniqueId().toString(), new NetworkComponentInfo(NetworkComponentType.NODE, node.get().getUniqueId().toString()));
                    }
                    futureAction.complete(this.cachedNodes.values());
                });

        //TODO add service

        return futureAction;
    }

    @Override
    public Collection<INetworkComponentInfo> getAllComponentInfo() {
        Collection<IRBucketHolder<CloudNode>> nodes = CloudAPI.getInstance().getNodeManager().getNodes();
        for(IRBucketHolder<CloudNode> node : nodes) {
            this.cachedNodes.put(node.get().getUniqueId().toString(), new NetworkComponentInfo(NetworkComponentType.NODE, node.get().getUniqueId().toString()));
        }

        //TODO add service

        return this.cachedNodes.values();
    }

}