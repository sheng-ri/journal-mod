package top.birthcat.journalmod.cmmon;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JournalNetReg {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("1");
        registrar.playToServer(
                JournalEditPacket.TYPE,
                JournalEditPacket.STREAM_CODEC,
                JournalEditPacket::handleOnServer
        );
    }
}
