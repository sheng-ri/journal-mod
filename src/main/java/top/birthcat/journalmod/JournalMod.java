/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import top.birthcat.journalmod.common.CommonSetupHandler;

@Mod(JournalMod.MODID)
public class JournalMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "journalmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public JournalMod(IEventBus modEventBus, ModContainer modContainer) {
        CommonSetupHandler.onModInit(modEventBus);
    }

}
