package net.suqatri.redicloud.api.impl.player.packet;

import lombok.Data;
import net.suqatri.redicloud.api.CloudAPI;
import net.suqatri.redicloud.api.impl.packet.CloudPacket;

import java.util.UUID;

@Data
public class CloudBridgeConnectServicePacket extends CloudPacket {

    private UUID uniqueId;
    private UUID serviceId;

    @Override
    public void receive() {
        CloudAPI.getInstance().getPlayerManager().getPlayerAsync(this.uniqueId)
                .onFailure(t -> CloudAPI.getInstance().getConsole().error("Failed to send message to player " + this.uniqueId, t))
                .onSuccess(playerHolder -> {
                    playerHolder.get().getBridge().connect(this.serviceId);
                });
    }
}