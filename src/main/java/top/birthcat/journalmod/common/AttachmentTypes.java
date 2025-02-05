/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.birthcat.journalmod.JournalMod;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.item.component.WritableBookContent.MAX_PAGES;
import static net.minecraft.world.item.component.WritableBookContent.PAGE_EDIT_LENGTH;

public class AttachmentTypes {

    public static DeferredRegister<AttachmentType<?>> MOD_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, JournalMod.MODID);

    public static final Supplier<AttachmentType<List<String>>> ATT_JOURNAL =
            MOD_ATTACHMENT_TYPES.register(
                    "journal", () -> AttachmentType.builder(() -> List.<String>of())
                            .serialize(Codec.list(Codec.string(0, PAGE_EDIT_LENGTH), 0, MAX_PAGES))
                            .copyOnDeath()
                            .build()
            );

}
