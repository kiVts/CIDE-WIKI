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

    /** Returns the unique numeric ID of this computer, matching os.getComputerID(). */
    @LuaFunction
    public final int getId() {
        return computer.getID();
    }

    /**
     * Returns the current in-game time as "HH:MM".
     * Minecraft's day starts at tick 0 = 06:00 and runs 24 000 ticks per day.
     * This method reads getDayTime() which is safe to call off the main thread.
     */
    @LuaFunction
    public final String getTime() {
        long tick = computer.getLevel().getDayTime() % 24000L;
        long totalMinutes = (tick * 24L * 60L) / 24000L;
        long hours   = (totalMinutes / 60 + 6) % 24;
        long minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Returns the horizontal direction the computer's face points toward.
     * Possible values: "north", "south", "east", "west", or "unknown".
     * Uses mainThread=true because getBlockState() is not thread-safe.
     */
    @LuaFunction(mainThread = true)
    public final String getFacing() {
        var state = computer.getLevel().getBlockState(computer.getPosition());
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return state.getValue(BlockStateProperties.HORIZONTAL_FACING).getName();
        }
        return "unknown";
    }
}
