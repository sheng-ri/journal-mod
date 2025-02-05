/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import top.birthcat.journalmod.JournalMod;

import java.util.List;

import static net.minecraft.world.item.component.WritableBookContent.MAX_PAGES;
import static net.minecraft.world.item.component.WritableBookContent.PAGE_EDIT_LENGTH;

/**
 * base on {@link ServerboundEditBookPacket}
 */
public record UpdatePacket(
        List<String> pages
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdatePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(JournalMod.MODID, "update"));

    public static final StreamCodec<ByteBuf, UpdatePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(PAGE_EDIT_LENGTH).apply(ByteBufCodecs.list(MAX_PAGES)),
            UpdatePacket::pages,
            UpdatePacket::new
    );

    @Override
    @NotNull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
