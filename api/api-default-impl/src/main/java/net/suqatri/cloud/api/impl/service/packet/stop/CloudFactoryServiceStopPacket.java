package net.suqatri.cloud.api.impl.service.packet.stop;

import lombok.Getter;
import lombok.Setter;
import net.suqatri.cloud.api.CloudAPI;
import net.suqatri.cloud.api.impl.packet.CloudPacket;

import java.util.UUID;

@Getter @Setter
public class CloudFactoryServiceStopPacket extends CloudPacket {

    private UUID serviceId;
    private boolean force;

    @Override
    public void receive() {
        CloudAPI.getInstance().getServiceManager().stopServiceAsync(this.serviceId, this.force)
                .onFailure(this::simplePacketResponseAsync)
                .onSuccess(holder -> this.simplePacketResponseAsync());
    }
}
