/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import top.birthcat.journalmod.common.CommonSetupHandler;

@Mod(JournalMod.MODID)
public class JournalMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "journalmod";

    public JournalMod(IEventBus modEventBus, ModContainer modContainer) {
        CommonSetupHandler.onModInit(modEventBus);
    }

}
