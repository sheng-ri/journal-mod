/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static top.birthcat.journalmod.client.ClientSetupHandler.OPEN_MAP;

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
        @SuppressWarnings("DataFlowIssue")
        var editScreen = new JournalEditScreen(player, ClientJournalHolder.getJournalData());
        mc.setScreen(editScreen);
    }
}
