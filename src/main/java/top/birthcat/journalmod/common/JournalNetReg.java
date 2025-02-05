/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.birthcat.journalmod.client.ClientJournalHolder;
import top.birthcat.journalmod.server.AttachmentSyncHandler;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JournalNetReg {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("1");
        var handler = new DirectionalPayloadHandler<>(
                JournalNetReg::handleOnClient,
                JournalNetReg::handleOnServer
        );
        registrar.playBidirectional(
                JournalDataPacket.TYPE,
                JournalDataPacket.STREAM_CODEC,
                handler
        );
    }

    private static void handleOnServer(JournalDataPacket p, IPayloadContext ctx) {
        AttachmentSyncHandler.syncOnEdit(ctx.player(),p.pages());
    }

    private static void handleOnClient(JournalDataPacket p, IPayloadContext ctx) {
        ClientJournalHolder.syncWithServer(p.pages());
    }
}
