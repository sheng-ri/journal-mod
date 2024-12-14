package top.birthcat.journalmod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import top.birthcat.journalmod.cmmon.CommonSetupHandler;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(JournalMod.MODID)
public class JournalMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "journalmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final List<String> defaultContent = List.of("Welcome");

    public JournalMod(IEventBus modEventBus, ModContainer modContainer) {
        CommonSetupHandler.onModInit(modEventBus);
    }

}
