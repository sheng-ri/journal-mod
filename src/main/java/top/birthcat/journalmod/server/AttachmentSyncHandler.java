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
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.birthcat.journalmod.common.JournalDataPacket;

import static top.birthcat.journalmod.common.AttachmentTypes.ATT_JOURNAL;

@EventBusSubscriber
public class AttachmentSyncHandler {

    @SubscribeEvent
    public static void syncOnLogin(PlayerEvent.PlayerLoggedInEvent e) {
        var player = e.getEntity();
        if (player instanceof ServerPlayer sPlayer) {
            var sidePages = player.getData(ATT_JOURNAL);
            PacketDistributor.sendToPlayer(sPlayer, new JournalDataPacket(sidePages));
        }
    }

    public static void syncOnEdit(JournalDataPacket packet, IPayloadContext ctx) {
        ctx.player().setData(ATT_JOURNAL, packet.pages());
    }
}
