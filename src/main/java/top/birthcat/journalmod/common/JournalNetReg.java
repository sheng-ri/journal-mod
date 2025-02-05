/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import com.google.common.collect.Lists;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.birthcat.journalmod.client.ClientJournalHolder;
import top.birthcat.journalmod.common.packet.TranscribePacket;
import top.birthcat.journalmod.common.packet.UpdatePacket;

import java.util.List;

import static top.birthcat.journalmod.common.AttachmentTypes.ATT_JOURNAL;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class JournalNetReg {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("2");
        registrar.playBidirectional(
                UpdatePacket.TYPE,
                UpdatePacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        JournalNetReg::clientHandleUpdate,
                        JournalNetReg::serverHandleUpdate
                )
        );
        registrar.playToServer(
                TranscribePacket.TYPE,
                TranscribePacket.STREAM_CODEC,
                JournalNetReg::handleTranscribe
        );
    }

    /**
     * Base on {@link ServerGamePacketListenerImpl#handleEditBook}
     */
    private static void handleTranscribe(TranscribePacket p, IPayloadContext ctx) {
        var player = ctx.player();
        ItemStack itemstack = player.getInventory().getItem(p.slot());
        if (itemstack.has(DataComponents.WRITABLE_BOOK_CONTENT)) {
            List<Filterable<String>> filterPages = Lists.newArrayList();
            for (var page : player.getData(ATT_JOURNAL)) {
                filterPages.add(Filterable.from(FilteredText.passThrough(page)));
            }
            itemstack.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(filterPages));
        }
    }

    private static void clientHandleUpdate(UpdatePacket p, IPayloadContext ctx) {
        ClientJournalHolder.syncServer(p.pages());
    }

    private static void serverHandleUpdate(UpdatePacket p, IPayloadContext ctx) {
        AttachmentSyncHandler.handleUpdateJournal(ctx.player(), p.pages());
    }
}
