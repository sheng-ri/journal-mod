/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.birthcat.journalmod.JournalMod;

import java.util.List;
import java.util.function.Supplier;

import static top.birthcat.journalmod.common.JournalDataPacket.MAX_LEN_PER_PAGE;
import static top.birthcat.journalmod.common.JournalDataPacket.MAX_PAGES;

public class AttachmentTypes {

    public static DeferredRegister<AttachmentType<?>> MOD_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, JournalMod.MODID);

    public static final Supplier<AttachmentType<List<String>>> ATT_JOURNAL =
            MOD_ATTACHMENT_TYPES.register(
                    "journal", () -> AttachmentType.builder(AttachmentTypes::defaultContent)
                            .serialize(Codec.list(Codec.string(0, MAX_LEN_PER_PAGE), 0, MAX_PAGES))
                            .copyOnDeath()
                            .build()
            );

    private static List<String> defaultContent(IAttachmentHolder holder) {
        return List.of(
                Component.translatable("book.journalmod.default.content").getString()
        );
    }

}
