package dev.example.pcinfo;

import dan200.computercraft.api.ComputerCraftAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.lang.reflect.Method;
import java.util.List;

@Mod(PcInfoMod.MOD_ID)
public class PcInfoMod {

    public static final String MOD_ID = "pcinfo";

    private static final String WIKI_PAGE = """
            # pcinfo

            This is (an example mod of) how you add a new wiki and autocomplete entry to CIDE



            It supports quite a few of identations to make the wiki more readable - it doesn't support all of it, but quite a bit of MD formating tools.
            ---
            You can place 3 dashes to make a divider

            ## Two hases make a sub heading
            ### Three make a sub... sub? heading
            - a single dash will make a bullet point
            
            """;

    public PcInfoMod(IEventBus modBus) {
        modBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        ComputerCraftAPI.registerAPIFactory(PcInfoAPI::new);

        if (ModList.get().isLoaded("cide")) {
            registerCideIntegration();
        }
    }

    private static void registerCideIntegration() {
        try {
            Class<?> completionReg = Class.forName("dev.kivts.cide.client.LuaCompletionRegistry");
            Class<?> wikiReg      = Class.forName("dev.kivts.cide.wiki.WikiRegistry");

            Method register        = completionReg.getMethod("register", String.class);
            Method registerMembers = completionReg.getMethod("registerMembers", String.class, List.class);
            Method registerSymbol  = wikiReg.getMethod("registerSymbol", String.class, String.class);
            Method registerPage    = wikiReg.getMethod("registerPage", String.class, String.class);

            register.invoke(null, "pcinfo");
            registerMembers.invoke(null, "pcinfo", List.of("getId", "getTime", "getFacing"));

            registerSymbol.invoke(null, "pcinfo", "module/pcinfo");
            registerPage.invoke(null, "module/pcinfo", WIKI_PAGE);

        } catch (ReflectiveOperationException e) {
            // CIDE is loaded but its API changed — log and continue without CIDE integration
            System.err.println("[pcinfo] CIDE integration failed: " + e.getMessage());
        }
    }
}
