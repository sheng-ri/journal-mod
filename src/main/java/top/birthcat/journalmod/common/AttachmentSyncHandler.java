/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.birthcat.journalmod.common.packet.UpdatePacket;

import java.util.List;

import static top.birthcat.journalmod.common.AttachmentTypes.ATT_JOURNAL;

@EventBusSubscriber
public class AttachmentSyncHandler {

    @SubscribeEvent
    public static void syncOnLogin(PlayerEvent.PlayerLoggedInEvent e) {
        var entity = e.getEntity();
        if (entity instanceof ServerPlayer p) {
            var sidePages = entity.getData(ATT_JOURNAL);
            PacketDistributor.sendToPlayer(p, new UpdatePacket(sidePages));
        }
    }

    public static void handleUpdateJournal(Player player, List<String> pages) {
        player.setData(ATT_JOURNAL, pages);
    }

}
