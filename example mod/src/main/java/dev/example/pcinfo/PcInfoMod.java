package dev.example.pcinfo;

import dan200.computercraft.api.ComputerCraftAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(PcInfoMod.MOD_ID)
public class PcInfoMod {
    public static final String MOD_ID = "pcinfo";

    public PcInfoMod(IEventBus modBus) {
        modBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        ComputerCraftAPI.registerAPIFactory(PcInfoAPI::new);
    }
}
