package com.clashwars.cwcore.dependencies;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.util.io.file.FilenameException;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class CWWorldEdit {

    public static void saveSchematic(Location loc1, Location loc2, File file) throws FilenameException, IOException, DataException {
        WorldEdit we = WorldEdit.getInstance();
        EditSession editSession = new EditSession(new BukkitWorld(loc1.getWorld()), we.getConfiguration().maxChangeLimit);

        Vector min = getMin(loc1, loc2);
        Vector max = getMax(loc1, loc2);

        file = we.getSafeSaveFile(null, file.getParentFile(), file.getName(), "schematic", new String[] { "schematic" });
        editSession.enableQueue();
        CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
        clipboard.copy(editSession);
        SchematicFormat.MCEDIT.save(clipboard, file);
        editSession.flushQueue();
    }

    public static void loadSchematic(File file, Location location) throws FilenameException, IOException, DataException, MaxChangedBlocksException {
        WorldEdit we = WorldEdit.getInstance();
        EditSession editSession = new EditSession(new BukkitWorld(location.getWorld()), we.getConfiguration().maxChangeLimit);

        file = we.getSafeSaveFile(null, file.getParentFile(), file.getName(), "schematic", new String[] { "schematic" });
        editSession.enableQueue();
        CuboidClipboard clipboard = SchematicFormat.MCEDIT.load(file);
        clipboard.place(editSession, new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), false);
        editSession.flushQueue();
        we.flushBlockBag(null, editSession);
    }


    private static Vector getMin(Location l1, Location l2) {
        return new Vector(Math.min(l1.getBlockX(), l2.getBlockX()), Math.min(l1.getBlockY(), l2.getBlockY()), Math.min(l1.getBlockZ(), l2.getBlockZ()));
    }

    private static Vector getMax(Location l1, Location l2) {
        return new Vector(Math.max(l1.getBlockX(), l2.getBlockX()), Math.max(l1.getBlockY(), l2.getBlockY()), Math.max(l1.getBlockZ(), l2.getBlockZ()));
    }
}
