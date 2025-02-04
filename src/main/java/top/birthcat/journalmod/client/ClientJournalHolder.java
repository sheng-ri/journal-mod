/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.birthcat.journalmod.common.JournalDataPacket;

import java.util.List;

/**
 * Keep journal with client lifecycle.
 * get rid of LocalPlayer clone.
 * this also enable recover after logout.
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class ClientJournalHolder {

    public static final List<@NotNull String> DEFAULT_DATA = List.of(
            I18n.get("book.journalmod.load")
    );
    private static List<String> journalData = DEFAULT_DATA;

    public static List<String> getJournalData() {
        return journalData;
    }

    public static void setJournal(List<String> newJournal, ItemStack stack,int slot) {
        journalData = newJournal;
        if (DEFAULT_DATA != journalData) {
            PacketDistributor.sendToServer(new JournalDataPacket(newJournal, (CompoundTag) stack.save(Minecraft.getInstance().level.registryAccess()),slot));
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        journalData = DEFAULT_DATA;
    }

    public static void syncWithServer(JournalDataPacket packet, IPayloadContext ctx) {
        journalData = packet.pages();
    }
}
