package dev.example.pcinfo;

import dev.kivts.cide.CideIntegration;

final class PcInfoCideCompat {
    private static final String WIKI_PAGE = """
        # pcinfo
        Adds a small ComputerCraft API with information about the current computer.

        ---
        ### pcinfo.getId()
        Returns this computer's numeric ID, matching os.getComputerID().

        ---
        ### pcinfo.getTime()
        Returns the current in-game time as HH:MM.

        ---
        ### pcinfo.getFacing()
        Returns the horizontal direction this computer faces.
        """;

    private PcInfoCideCompat() {
    }

    static void register() {
        CideIntegration.registerModule("pcinfo", WIKI_PAGE, "getId", "getTime", "getFacing");
    }
}
