package thederpgamer.playerskills.data;

import api.network.PacketReadBuffer;
import api.network.PacketWriteBuffer;

import java.io.IOException;

/**
 * [Description]
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public interface NetworkSendable {

	void serialize(PacketWriteBuffer writeBuffer) throws IOException;
	void deserialize(PacketReadBuffer readBuffer) throws IOException;
}
