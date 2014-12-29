package com.clashwars.cwcore.cuboid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.List;

public class CuboidEditor {

    private Cuboid cuboid;

    public CuboidEditor(Cuboid cuboid) {
        this.cuboid = cuboid;
    }



    public void fill(Material mat) {
        fill(mat, (byte)0);
    }

    public void fill(Material mat, byte data) {
        List<Block> blocks = cuboid.getBlocks();
        for (Block block : blocks) {
            block.setType(mat);
            block.setData(data);
        }
    }


    public void replace(Material from, Material to) {
        replace(from, to, (byte)0);
    }

    public void replace(Material from, Material to, byte dataTo) {
        List<Block> blocks = cuboid.getBlocks();
        for (Block block : blocks) {
            if (block.getType() == from) {
                block.setType(to);
                block.setData(dataTo);
            }
        }
    }


    public void walls (Material mat) {
        walls(mat, (byte)0, false);
    }

    public void walls(Material mat, byte data, boolean ceilAndRoof) {
        Location loc;
        List<Block> blocks = cuboid.getBlocks();
        for (Block block : blocks) {
            loc = block.getLocation();
            if (loc.getBlockX() == cuboid.getMinX() || loc.getBlockX() == cuboid.getMaxX() || loc.getBlockZ() == cuboid.getMinZ() || loc.getBlockZ() == cuboid.getMaxZ()
                    || (ceilAndRoof && (loc.getBlockY() == cuboid.getMinY() || loc.getBlockY() == cuboid.getMaxY())) ) {
                block.setType(mat);
                block.setData(data);
            }
        }
    }


    public void replacewalls(Material from, Material to) {
        replacewalls(from, to, (byte) 0, false);
    }

    public void replacewalls(Material from, Material to, byte dataTo, boolean ceilAndRoof) {
        Location loc;
        List<Block> blocks = cuboid.getBlocks();
        for (Block block : blocks) {
            if (block.getType() == from) {
                loc = block.getLocation();
                if (loc.getBlockX() == cuboid.getMinX() || loc.getBlockX() == cuboid.getMaxX() || loc.getBlockZ() == cuboid.getMinZ() || loc.getBlockZ() == cuboid.getMaxZ()
                        || (ceilAndRoof && (loc.getBlockY() == cuboid.getMinY() || loc.getBlockY() == cuboid.getMaxY())) ) {
                    block.setType(to);
                    block.setData(dataTo);
                }
            }
        }
    }

}
