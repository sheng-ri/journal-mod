/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.cmmon;

import com.mojang.serialization.Codec;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.birthcat.journalmod.JournalMod;

import java.util.List;
import java.util.function.Supplier;

import static top.birthcat.journalmod.cmmon.JournalDataPacket.MAX_LEN_PER_PAGE;
import static top.birthcat.journalmod.cmmon.JournalDataPacket.MAX_PAGES;

public class AttachmentTypes {

    public static DeferredRegister<AttachmentType<?>> MOD_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, JournalMod.MODID);
    public static final Supplier<AttachmentType<List<String>>> ATT_PAGES =
            MOD_ATTACHMENT_TYPES.register(
            "journal", () -> AttachmentType.builder(AttachmentTypes::defaultContent)
                    .serialize(Codec.list(Codec.string(0, MAX_LEN_PER_PAGE), 0, MAX_PAGES))
                    .copyOnDeath()
                    .build()
    );

    private static List<String> defaultContent(IAttachmentHolder holder) {
        return List.of(
                I18n.get("book.journalmod.default.content")
        );
    }

    // hide sync
    public static void setPage(Player player,List<String> pages) {
        if (player.level().isClientSide && player.hasData(ATT_PAGES)) {
            PacketDistributor.sendToServer(new JournalDataPacket(pages));
        }
        player.setData(ATT_PAGES,pages);
    }

}
