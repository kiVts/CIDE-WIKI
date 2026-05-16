package dev.example.pcinfo;

import dan200.computercraft.api.lua.IComputerSystem;
import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaFunction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PcInfoAPI implements ILuaAPI {

    private final IComputerSystem computer;

    public PcInfoAPI(IComputerSystem computer) {
        this.computer = computer;
    }

    @Override
    public String[] getNames() {
        return new String[]{ "pcinfo" };
    }

    @LuaFunction
    public final int getId() {
        return computer.getID();
    }

    @LuaFunction
    public final String getTime() {
        long tick = computer.getLevel().getDayTime() % 24000L;
        long totalMinutes = (tick * 24L * 60L) / 24000L;
        long hours   = (totalMinutes / 60 + 6) % 24;
        long minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    @LuaFunction(mainThread = true)
    public final String getFacing() {
        var state = computer.getLevel().getBlockState(computer.getPosition());
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return state.getValue(BlockStateProperties.HORIZONTAL_FACING).getName();
        }
        return "unknown";
    }
}
