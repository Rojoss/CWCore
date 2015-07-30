package com.clashwars.cwcore.dependencies;

import com.clashwars.cwcore.debug.Debug;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.util.io.file.FilenameException;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.AsyncWorldEditMain;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacerListener;
import org.primesoft.asyncworldedit.api.blockPlacer.IJobEntryListener;
import org.primesoft.asyncworldedit.blockPlacer.BlockPlacer;
import org.primesoft.asyncworldedit.blockPlacer.entries.JobEntry;
import org.primesoft.asyncworldedit.worldedit.AsyncCuboidClipboard;
import org.primesoft.asyncworldedit.worldedit.AsyncEditSessionFactory;
import org.primesoft.asyncworldedit.worldedit.ThreadSafeEditSession;

import java.io.File;
import java.io.IOException;

public class CWWorldEdit {

    public static void saveSchematic(Location loc1, Location loc2, File file) throws FilenameException, IOException, DataException {
        WorldEdit we = WorldEdit.getInstance();
        EditSession editSession = we.getEditSessionFactory().getEditSession(new BukkitWorld(loc1.getWorld()), we.getConfiguration().maxChangeLimit);

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
        EditSession editSession = we.getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), we.getConfiguration().maxChangeLimit);

        file = we.getSafeSaveFile(null, file.getParentFile(), file.getName(), "schematic", new String[] { "schematic" });
        editSession.enableQueue();
        CuboidClipboard clipboard = SchematicFormat.MCEDIT.load(file);
        clipboard.place(editSession, new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), false);
        editSession.flushQueue();
        we.flushBlockBag(null, editSession);
    }

    public static void loadSchematicAsync(File file, Location location, final IJobEntryListener callback) throws FilenameException, IOException, com.sk89q.worldedit.world.DataException, MaxChangedBlocksException {
        WorldEdit we = WorldEdit.getInstance();
        AsyncWorldEditMain awe = AsyncWorldEditMain.getInstance();
        if (we == null || awe == null) {
            return;
        }
        file = we.getSafeSaveFile(null, file.getParentFile(), file.getName(), "schematic", new String[] { "schematic" });
        EditSession editSession = we.getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), we.getConfiguration().maxChangeLimit);

        final IBlockPlacerListener listener = new IBlockPlacerListener() {
            @Override
            public void jobAdded(JobEntry job) {
                job.addStateChangedListener(callback);
            }
            @Override
            public void jobRemoved(JobEntry job) {
                job.removeStateChangedListener(callback);
            }
        };
        BlockPlacer bPlacer = (BlockPlacer)awe.getBlockPlacer();
        bPlacer.addListener(listener);

        ThreadSafeEditSession es = ((AsyncEditSessionFactory)we.getEditSessionFactory()).getThreadSafeEditSession(new BukkitWorld(location.getWorld()), Integer.MAX_VALUE);
        AsyncCuboidClipboard cc = new AsyncCuboidClipboard(es.getPlayer(), CuboidClipboard.loadSchematic(file));
        cc.paste(editSession, new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), false);
        bPlacer.removeListener(listener);
    }


    private static Vector getMin(Location l1, Location l2) {
        return new Vector(Math.min(l1.getBlockX(), l2.getBlockX()), Math.min(l1.getBlockY(), l2.getBlockY()), Math.min(l1.getBlockZ(), l2.getBlockZ()));
    }

    private static Vector getMax(Location l1, Location l2) {
        return new Vector(Math.max(l1.getBlockX(), l2.getBlockX()), Math.max(l1.getBlockY(), l2.getBlockY()), Math.max(l1.getBlockZ(), l2.getBlockZ()));
    }
}
