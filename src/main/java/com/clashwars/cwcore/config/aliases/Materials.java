package com.clashwars.cwcore.config.aliases;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.*;

public class Materials extends EasyConfig {

    public Map<String, ArrayList<String>> materials = new HashMap<String, ArrayList<String>>();

    public Materials(String fileName) {
        this.setFile(fileName);
    }

    /**
     * Update the material list with all available bukkit materials.
     */
    public void update() {
        for (Material mat : Material.values()) {
            String matName = mat.toString();

            //Get material name based on data value like DIRT with data value 1 is Podzol.
            if (mat == Material.DIRT) {
                updateMaterial(matName, null);
                updateMaterial(matName + "-1", "Podzol");
            } else if (mat == Material.WOOD) {
                updateMaterial(matName, "OakWood");
                updateMaterial(matName + "-1", "SpruceWood");
                updateMaterial(matName + "-2", "BirchWood");
                updateMaterial(matName + "-3", "JungleWood");
                updateMaterial(matName + "-4", "AcaciaWood");
                updateMaterial(matName + "-5", "DarkOakWood");
            } else if (mat == Material.SAPLING) {
                updateMaterial(matName, "OakSapling");
                updateMaterial(matName + "-1", "SpruceSapling");
                updateMaterial(matName + "-2", "BirchSapling");
                updateMaterial(matName + "-3", "JungleSapling");
                updateMaterial(matName + "-4", "AcaciaSapling");
                updateMaterial(matName + "-5", "DarkOakSapling");
            } else if (mat == Material.LOG) {
                updateMaterial(matName, "OakLog");
                updateMaterial(matName + "-1", "SpruceLog");
                updateMaterial(matName + "-2", "BirchLog");
                updateMaterial(matName + "-3", "JungleLog");
            } else if (mat == Material.LOG_2) {
                updateMaterial(matName, "AcaciaLog");
                updateMaterial(matName + "-1", "DarkOakLog");
            } else if (mat == Material.LEAVES) {
                updateMaterial(matName, "OakLeaves");
                updateMaterial(matName + "-1", "SpruceLeaves");
                updateMaterial(matName + "-2", "BirchLeaves");
                updateMaterial(matName + "-3", "JungleLeaves");
            } else if (mat == Material.LEAVES_2) {
                updateMaterial(matName, "AcaciaLeaves");
                updateMaterial(matName + "-1", "DarkOakLeaves");
            } else if (mat == Material.SPONGE) {
                updateMaterial(matName, "DrySponge");
                updateMaterial(matName + "-1", "WetSponge");
            } else if (mat == Material.SANDSTONE) {
                updateMaterial(matName, null);
                updateMaterial(matName + "-1", "ChiseledSandstone");
                updateMaterial(matName + "-2", "SmoothSandstone");
            } else if (mat == Material.LONG_GRASS) {
                updateMaterial(matName, "Shrub");
                updateMaterial(matName + "-1", "TallGrass");
                updateMaterial(matName + "-2", "Fern");
            } else if (mat == Material.WOOL) {
                updateMaterial(matName, "WhiteWool");
                updateMaterial(matName + "-1", "OrangeWool");
                updateMaterial(matName + "-2", "MagentaWool");
                updateMaterial(matName + "-3", "LightBlueWool");
                updateMaterial(matName + "-4", "YellowWool");
                updateMaterial(matName + "-5", "LimeWool");
                updateMaterial(matName + "-6", "PinkWool");
                updateMaterial(matName + "-7", "GrayWool");
                updateMaterial(matName + "-8", "LightGrayWool");
                updateMaterial(matName + "-9", "CyanWool");
                updateMaterial(matName + "-10", "PurpleWool");
                updateMaterial(matName + "-11", "BlueWool");
                updateMaterial(matName + "-12", "BrownWool");
                updateMaterial(matName + "-13", "GreenWool");
                updateMaterial(matName + "-14", "RedWool");
                updateMaterial(matName + "-15", "BlackWool");
            } else if (mat == Material.RED_ROSE) {
                updateMaterial(matName, "Rose");
                updateMaterial(matName + "-1", "Orchid");
                updateMaterial(matName + "-2", "Allium");
                updateMaterial(matName + "-4", "RedTulip");
                updateMaterial(matName + "-5", "OrangeTulip");
                updateMaterial(matName + "-6", "WhiteTulip");
                updateMaterial(matName + "-7", "PinkTulip");
                updateMaterial(matName + "-8", "OxeyeDaisy");
            } else if (mat == Material.DOUBLE_STEP) {
                updateMaterial(matName, "DoubleStoneSlab");
                updateMaterial(matName + "-1", "DoubleSandstoneSlab");
                updateMaterial(matName + "-2", "DoubleWoodSlab");
                updateMaterial(matName + "-3", "DoubleCobblestoneSlab");
                updateMaterial(matName + "-4", "DoubleBrickSlab");
                updateMaterial(matName + "-5", "DoubleStoneBrickSlab");
                updateMaterial(matName + "-6", "DoubleNetherBrickSlab");
                updateMaterial(matName + "-7", "DoubleQuartzSlab");
                updateMaterial(matName + "-8", "DoubleSmoothStoneSlab");
                updateMaterial(matName + "-9", "DoubleSmoothSandstoneSlab");
            } else if (mat == Material.STEP) {
                updateMaterial(matName, "StoneSlab");
                updateMaterial(matName + "-1", "SandstoneSlab");
                updateMaterial(matName + "-2", "WoodSlab");
                updateMaterial(matName + "-3", "CobblestoneSlab");
                updateMaterial(matName + "-4", "BrickSlab");
                updateMaterial(matName + "-5", "StoneBrickSlab");
                updateMaterial(matName + "-6", "NetherBrickSlab");
                updateMaterial(matName + "-7", "QuartzSlab");
            } else if (mat == Material.STAINED_GLASS) {
                updateMaterial(matName, "WhiteGlass");
                updateMaterial(matName + "-1", "OrangeGlass");
                updateMaterial(matName + "-2", "MagentaGlass");
                updateMaterial(matName + "-3", "LightBlueGlass");
                updateMaterial(matName + "-4", "YellowGlass");
                updateMaterial(matName + "-5", "LimeGlass");
                updateMaterial(matName + "-6", "PinkGlass");
                updateMaterial(matName + "-7", "GrayGlass");
                updateMaterial(matName + "-8", "LightGrayGlass");
                updateMaterial(matName + "-9", "CyanGlass");
                updateMaterial(matName + "-10", "PurpleGlass");
                updateMaterial(matName + "-11", "BlueGlass");
                updateMaterial(matName + "-12", "BrownGlass");
                updateMaterial(matName + "-13", "GreenGlass");
                updateMaterial(matName + "-14", "RedGlass");
                updateMaterial(matName + "-15", "BlackGlass");
            } else if (mat == Material.STAINED_GLASS_PANE) {
                updateMaterial(matName, "WhiteGlassPane");
                updateMaterial(matName + "-1", "OrangeGlassPane");
                updateMaterial(matName + "-2", "MagentaGlassPane");
                updateMaterial(matName + "-3", "LightBlueGlassPane");
                updateMaterial(matName + "-4", "YellowGlassPane");
                updateMaterial(matName + "-5", "LimeGlassPane");
                updateMaterial(matName + "-6", "PinkGlassPane");
                updateMaterial(matName + "-7", "GrayGlassPane");
                updateMaterial(matName + "-8", "LightGrayGlassPane");
                updateMaterial(matName + "-9", "CyanGlassPane");
                updateMaterial(matName + "-10", "PurpleGlassPane");
                updateMaterial(matName + "-11", "BlueGlassPane");
                updateMaterial(matName + "-12", "BrownGlassPane");
                updateMaterial(matName + "-13", "GreenGlassPane");
                updateMaterial(matName + "-14", "RedGlassPane");
                updateMaterial(matName + "-15", "BlackGlassPane");
            } else if (mat == Material.SMOOTH_BRICK) {
                updateMaterial(matName, "StoneBrick");
                updateMaterial(matName + "-1", "MossyStoneBrick");
                updateMaterial(matName + "-2", "CrackedStoneBrick");
                updateMaterial(matName + "-3", "ChiseledStoneBrick");
            } else if (mat == Material.WOOD_STEP) {
                updateMaterial(matName, "OakSlab");
                updateMaterial(matName + "-1", "SpruceSlab");
                updateMaterial(matName + "-2", "BirchSlab");
                updateMaterial(matName + "-3", "JungleSlab");
                updateMaterial(matName + "-4", "AcaciaSlab");
                updateMaterial(matName + "-5", "DarkOakSlab");
            } else if (mat == Material.COBBLE_WALL) {
                updateMaterial(matName, "CobblestoneWall");
                updateMaterial(matName + "-1", "MossyCobblestoneWall");
            } else if (mat == Material.ANVIL) {
                updateMaterial(matName, null);
                updateMaterial(matName + "-1", "DamagedAnvil");
                updateMaterial(matName + "-2", "VeryDamagedAnvil");
            } else if (mat == Material.QUARTZ_BLOCK) {
                updateMaterial(matName, "QuartzBlock");
                updateMaterial(matName + "-1", "ChiseledQuartz");
                updateMaterial(matName + "-2", "PillarQuartz");
            } else if (mat == Material.STAINED_CLAY) {
                updateMaterial(matName, "WhiteClay");
                updateMaterial(matName + "-1", "OrangeClay");
                updateMaterial(matName + "-2", "MagentaClay");
                updateMaterial(matName + "-3", "LightBlueClay");
                updateMaterial(matName + "-4", "YellowClay");
                updateMaterial(matName + "-5", "LimeClay");
                updateMaterial(matName + "-6", "PinkClay");
                updateMaterial(matName + "-7", "GrayClay");
                updateMaterial(matName + "-8", "LightGrayClay");
                updateMaterial(matName + "-9", "CyanClaye");
                updateMaterial(matName + "-10", "PurpleClay");
                updateMaterial(matName + "-11", "BlueClay");
                updateMaterial(matName + "-12", "BrownClay");
                updateMaterial(matName + "-13", "GreenClay");
                updateMaterial(matName + "-14", "RedClay");
                updateMaterial(matName + "-15", "BlackClay");
            } else if (mat == Material.CARPET) {
                updateMaterial(matName, "WhiteCarpet");
                updateMaterial(matName + "-1", "OrangeCarpet");
                updateMaterial(matName + "-2", "MagentaCarpet");
                updateMaterial(matName + "-3", "LightBlueCarpet");
                updateMaterial(matName + "-4", "YellowCarpet");
                updateMaterial(matName + "-5", "LimeCarpet");
                updateMaterial(matName + "-6", "PinkCarpet");
                updateMaterial(matName + "-7", "GrayCCarpet");
                updateMaterial(matName + "-8", "LightGrayCarpet");
                updateMaterial(matName + "-9", "CyanCarpet");
                updateMaterial(matName + "-10", "PurpleCarpet");
                updateMaterial(matName + "-11", "BlueCarpet");
                updateMaterial(matName + "-12", "BrownCarpet");
                updateMaterial(matName + "-13", "GreenCarpet");
                updateMaterial(matName + "-14", "RedCarpet");
                updateMaterial(matName + "-15", "BlackCarpet");
            } else if (mat == Material.DOUBLE_PLANT) {
                updateMaterial(matName, "Sunflower");
                updateMaterial(matName + "-1", "Lilac");
                updateMaterial(matName + "-2", "DoubleTallGrass");
                updateMaterial(matName + "-3", "LargeFern");
                updateMaterial(matName + "-4", "RoseBush");
                updateMaterial(matName + "-5", "Peony");
            } else if (mat == Material.RAW_FISH) {
                updateMaterial(matName, "RawFish");
                updateMaterial(matName + "-1", "RawSalmon");
                updateMaterial(matName + "-2", "Clownfish");
                updateMaterial(matName + "-3", "Pufferfish");
            } else if (mat == Material.COOKED_FISH) {
                updateMaterial(matName, "CookedFish");
                updateMaterial(matName + "-1", "CookedSalmon");
            } else if (mat == Material.INK_SACK) {
                updateMaterial(matName, "InkSack");
                updateMaterial(matName + "-1", "RedDye");
                updateMaterial(matName + "-2", "GreenDye");
                updateMaterial(matName + "-3", "CocoaBean");
                updateMaterial(matName + "-4", "LapisLazuli");
                updateMaterial(matName + "-5", "PurpleDye");
                updateMaterial(matName + "-6", "CyanDye");
                updateMaterial(matName + "-7", "LightGrayDye");
                updateMaterial(matName + "-8", "GrayDye");
                updateMaterial(matName + "-9", "PinkDye");
                updateMaterial(matName + "-10", "LimeDye");
                updateMaterial(matName + "-11", "YellowDye");
                updateMaterial(matName + "-12", "LightBlueDye");
                updateMaterial(matName + "-13", "MagentaDye");
                updateMaterial(matName + "-14", "OrangeDye");
                updateMaterial(matName + "-15", "Bonemeal");

            //TODO: Potions

            } else if (mat == Material.MONSTER_EGG) {
                updateMaterial(matName, "MobEgg");
                updateMaterial(matName + "-50", "CreeperEgg");
                updateMaterial(matName + "-51", "SkeletonEgg");
                updateMaterial(matName + "-52", "SpiderEgg");
                updateMaterial(matName + "-54", "ZombieEgg");
                updateMaterial(matName + "-55", "SlimeEgg");
                updateMaterial(matName + "-56", "GhastEgg");
                updateMaterial(matName + "-57", "ZombiePigmanEgg");
                updateMaterial(matName + "-58", "EndermanEgg");
                updateMaterial(matName + "-59", "CaveSpiderEgg");
                updateMaterial(matName + "-60", "SilverfishEgg");
                updateMaterial(matName + "-61", "BlazeEgg");
                updateMaterial(matName + "-62", "MagmaCubeEgg");
                updateMaterial(matName + "-65", "BatEgg");
                updateMaterial(matName + "-66", "WitchEgg");
                updateMaterial(matName + "-67", "EndermiteEgg");
                updateMaterial(matName + "-68", "GuardianEgg");
                updateMaterial(matName + "-90", "PigEgg");
                updateMaterial(matName + "-91", "SheepEgg");
                updateMaterial(matName + "-92", "CowEgg");
                updateMaterial(matName + "-93", "ChickenEgg");
                updateMaterial(matName + "-94", "SquidEgg");
                updateMaterial(matName + "-95", "WolfEgg");
                updateMaterial(matName + "-96", "MooshroomEgg");
                updateMaterial(matName + "-98", "OcelotEgg");
                updateMaterial(matName + "-100", "HorseEgg");
                updateMaterial(matName + "-101", "RabbitEgg");
                updateMaterial(matName + "-120", "VillagerEgg");
            } else if (mat == Material.SKULL_ITEM) {
                updateMaterial(matName, "SkeletonSkull");
                updateMaterial(matName + "-1", "WitherSkull");
                updateMaterial(matName + "-2", "ZombieSkull");
                updateMaterial(matName + "-3", "SteveSkull");
                updateMaterial(matName + "-4", "CreeperSkull");
            } else {
                //For all materials that don't need a custom name based on data.
                updateMaterial(mat.toString(), null);
            }
        }
        save();
    }

    private void updateMaterial(String matName, String defaultAlias) {
        if (!materials.containsKey(matName)) {
            ArrayList<String> aliases = new ArrayList<String>();

            if (defaultAlias == null) {
                String name = "";
                String[] split = matName.split("_");
                for (String str : split) {
                    name += CWUtil.capitalize(str);
                }
                aliases.add(name);
            } else {
                aliases.add(defaultAlias);
            }

            materials.put(matName, aliases);
        }
    }

    /**
     * Get a material by alias.
     * @param alias
     * @return Material
     */
    public Material getMaterial(String alias) {
        for (String matName : materials.keySet()) {
            if (alias.equalsIgnoreCase(matName) || alias.equalsIgnoreCase(matName.replace("_", ""))) {
                return Material.valueOf(matName);
            }
            for (String s : materials.get(matName)) {
                if (s.equalsIgnoreCase(alias)) {
                    String[] split = matName.split("-");
                    if (split != null && split.length > 0) {
                        return Material.valueOf(split[0]);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get material data by alias.
     * @param alias
     * @return material data as int. -1 if no data found.
     */
    public int getMaterialData(String alias) {
        for (String matName : materials.keySet()) {
            for (String s : materials.get(matName)) {
                if (s.equalsIgnoreCase(alias)) {
                    String[] split = matName.split("-");
                    if (split.length > 1) {
                        return CWUtil.getInt(split[1]);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Get a display name for the specified material.
     * It will use the first value in the aliases config.
     * @param material
     * @param data specify -1 for no data check.
     * @return Display name for material
     */
    public String getDisplayName(Material material, int data) {
        String matName = material.toString();
        if (data > 0) {
            matName += "-" + data;
        }
        if (!materials.containsKey(matName) || materials.get(matName).size() < 1) {
            String name = "";
            String[] split = material.toString().split("_");
            for (String str : split) {
                name += CWUtil.capitalize(str);
            }
            return name;
        }
        return materials.get(matName).get(0);
    }
}
