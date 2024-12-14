package top.birthcat.journalmod.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static top.birthcat.journalmod.client.ClientSetupHandler.OPEN_MAP;
import static top.birthcat.journalmod.cmmon.AttachmentTypes.ATT_PAGES;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber
public class KeyPressHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!OPEN_MAP.get().consumeClick()) {
            return;
        }
            var mc = Minecraft.getInstance();
            var player = mc.player;
            var pages = player.getData(ATT_PAGES);
            var editScreen = new JournalEditScreen(player, pages);
            mc.setScreen(editScreen);
    }
}
