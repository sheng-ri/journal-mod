/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.cmmon;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.birthcat.journalmod.JournalMod;

import java.util.List;

/**
 * base on {@link net.minecraft.network.protocol.game.ServerboundEditBookPacket}
 * but is bidirectional.
 */
public record JournalDataPacket(
        List<String> pages
) implements CustomPacketPayload {

    public static final int MAX_LEN_PER_PAGE = 1024;
    public static final int MAX_PAGES = 100;

    public static final CustomPacketPayload.Type<JournalDataPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(JournalMod.MODID, "journal_edit_packet"));

    public static final StreamCodec<ByteBuf, JournalDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(MAX_LEN_PER_PAGE).apply(ByteBufCodecs.list(MAX_PAGES)),
            JournalDataPacket::pages,
            JournalDataPacket::new
    );

    @Override
    @NotNull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
