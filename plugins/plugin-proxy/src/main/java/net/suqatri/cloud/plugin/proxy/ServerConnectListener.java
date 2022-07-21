package net.suqatri.cloud.plugin.proxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.suqatri.cloud.api.CloudAPI;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectEvent event){
        if(event.isCancelled()) return;

        ServerInfo serverInfo = event.getTarget().getName().equals("fallback")
                ? ProxyServer.getInstance()
                .getServerInfo(CloudAPI.getInstance().getServiceManager().getFallbackService().get().getServiceName())
                : event.getTarget();

        if(serverInfo == null) {
            event.getPlayer().disconnect("Fallback service is not available.");
            return;
        }

        event.setTarget(serverInfo);
    }

}