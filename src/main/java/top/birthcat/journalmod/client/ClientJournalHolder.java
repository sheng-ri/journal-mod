/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.birthcat.journalmod.common.packet.UpdatePacket;

import java.util.List;

/**
 * Keep journal with client lifecycle.
 * get rid of LocalPlayer clone.
 * this also enable recover after logout.
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(Dist.CLIENT)
public class ClientJournalHolder {

    private static List<String> journalData = List.of();
    private static boolean isLoaded = false;

    public static List<String> getJournalData() {
        return journalData;
    }

    public static void setJournal(List<String> newJournal) {
        journalData = newJournal;
        PacketDistributor.sendToServer(new UpdatePacket(newJournal));
    }

    public static boolean isLoaded() {
        return isLoaded;
    }

    public static boolean isWelcomeText() {
        return journalData.isEmpty();
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        isLoaded = false;
        journalData = List.of();
    }

    public static void syncServer(List<String> pages) {
        journalData = pages;
        isLoaded = true;
    }

}
