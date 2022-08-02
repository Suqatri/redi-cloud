package net.suqatri.redicloud.plugin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "redicloud-plugin", name = "RediCloud-Plugin", version = "1.0-SNAPSHOT", authors = "Suqatri")
public class VelocityCloudPlugin {

    private VelocityCloudAPI cloudAPI;

    @Inject
    public VelocityCloudPlugin(ProxyServer proxyServer, Logger logger) {
        cloudAPI = new VelocityCloudAPI(proxyServer, this);
    }

}
