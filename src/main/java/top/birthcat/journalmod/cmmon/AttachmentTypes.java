/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.cmmon;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.birthcat.journalmod.JournalMod;

import java.util.List;
import java.util.function.Supplier;

import static top.birthcat.journalmod.cmmon.JournalEditPacket.MAX_LEN_PER_PAGE;
import static top.birthcat.journalmod.cmmon.JournalEditPacket.MAX_PAGES;

public class AttachmentTypes {

    public static DeferredRegister<AttachmentType<?>> MOD_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, JournalMod.MODID);
    public static final Supplier<AttachmentType<List<String>>> ATT_PAGES =
            MOD_ATTACHMENT_TYPES.register(
            "journal", () -> AttachmentType.builder(() -> JournalMod.defaultContent)
                    .serialize(Codec.list(Codec.string(0, MAX_LEN_PER_PAGE), 0, MAX_PAGES))
                    .copyOnDeath()
                    .build()
    );

    public static void setPage(Player player,List<String> pages) {
        player.setData(ATT_PAGES,pages);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            PacketDistributor.sendToServer(new JournalEditPacket(pages));
        }
    }

}
