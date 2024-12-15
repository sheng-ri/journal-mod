/*
 * Copyright (c) BirthCat
 * SPDX-License-Identifier: Apache-2.0
 */

package top.birthcat.journalmod.common;

import net.neoforged.bus.api.IEventBus;

import static top.birthcat.journalmod.common.AttachmentTypes.MOD_ATTACHMENT_TYPES;

public class CommonSetupHandler {

    public static void onModInit(IEventBus bus) {
        MOD_ATTACHMENT_TYPES.register(bus);
    }

}
