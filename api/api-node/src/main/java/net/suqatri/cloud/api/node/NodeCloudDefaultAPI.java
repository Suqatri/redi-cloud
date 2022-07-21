package net.suqatri.cloud.api.node;

import net.suqatri.cloud.api.impl.CloudDefaultAPIImpl;
import net.suqatri.cloud.api.impl.node.CloudNode;
import net.suqatri.cloud.api.impl.poll.timeout.ITimeOutPollManager;
import net.suqatri.cloud.api.node.file.IFileTransferManager;
import net.suqatri.cloud.api.node.service.screen.IServiceScreenManager;
import net.suqatri.cloud.api.utils.ApplicationType;

public abstract class NodeCloudDefaultAPI extends CloudDefaultAPIImpl<CloudNode> {

    public NodeCloudDefaultAPI() {
        super(ApplicationType.NODE);
    }

    public abstract CloudNode getNode();
    public abstract IFileTransferManager getFileTransferManager();
    public abstract IServiceScreenManager getScreenManager();
    public abstract ITimeOutPollManager timeOutPoolManager;

}
