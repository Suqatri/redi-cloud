package net.suqatri.redicloud.plugin.velocity.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import net.suqatri.redicloud.api.impl.player.CloudPlayer;
import net.suqatri.redicloud.api.velocity.utils.LegacyMessageUtils;
import net.suqatri.redicloud.plugin.velocity.VelocityCloudAPI;

public class PostLoginListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onPostLogin(PostLoginEvent event){
        if(event.getPlayer().isOnlineMode()) return;

        boolean isLoggedIn = false;
        if(VelocityCloudAPI.getInstance().getPlayerManager().existsPlayer(event.getPlayer().getUniqueId())){
            CloudPlayer cloudPlayer = (CloudPlayer) VelocityCloudAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            isLoggedIn = cloudPlayer.isLoggedIn();
            cloudPlayer.setLastLogin(System.currentTimeMillis());
            cloudPlayer.setConnected(true);
            cloudPlayer.setLastIp(event.getPlayer().getRemoteAddress().getHostString());
            cloudPlayer.setLastConnectedProxyId(VelocityCloudAPI.getInstance().getService().getUniqueId());
            cloudPlayer.updateAsync();
        }

        if(isLoggedIn){
            event.getPlayer().sendMessage(LegacyMessageUtils.component("You are logged in as " + event.getPlayer().getUsername() + "!"));
        }
    }

}