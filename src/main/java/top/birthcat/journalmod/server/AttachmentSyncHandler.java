/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.server;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.birthcat.journalmod.cmmon.JournalDataPacket;

import static top.birthcat.journalmod.cmmon.AttachmentTypes.ATT_PAGES;

@EventBusSubscriber
public class AttachmentSyncHandler {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
        var player = e.getEntity();
        if (player instanceof ServerPlayer sPlayer) {
            var sidePages = player.getData(ATT_PAGES);
            PacketDistributor.sendToPlayer(sPlayer, new JournalDataPacket(sidePages));
        }
    }
}
