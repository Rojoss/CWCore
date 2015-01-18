package com.clashwars.cwcore.scoreboard;

public enum Criteria {
    /**
     * Score is only changed by commands, and not by game events such as death.
     * This is useful for event flags, state mappings, and currencies.
     */
    DUMMY("dummy", true),

    /**
     * Score is only changed by commands, and not by game events such as death.
     * The /trigger command can be used by a player to set or increment/decrement their own score in an objective with this criteria.
     * The /trigger command will fail if the objective has not been "enabled" for the player using it, and the objective will be disabled for the player after they use the /trigger command on it.
     * Note that the /trigger command can be used by ordinary players even if Cheats are off and they are not an Operator.
     * This is useful for player input via /tellraw interfaces.
     */
    TRIGGER("trigger", true),

    /**
     * Score increments automatically for a player when they die.
     */
    DEATH_COUNT("deathCount", true),

    /**
     * Score increments automatically for a player when they kill another player.
     */
    PLAYER_KILL_COUNT("playerKillCount", true),

    /**
     * Score increments automatically for a player when they kill another player or a mob.
     */
    TOTAL_KILL_COUNT("totalKillCount", true),

    /**
     * Ranges from 0 to 20 on a normal player; represents the amount of half-hearts the player has.
     * May appear as 0 for players before their health has changed for the first time.
     * Extra hearts and absorption hearts also count to the health score, meaning that with Attributes/Modifiers or the Health Boost or Absorption status effects, health can far surpass 20.
     */
    HEALTH("health", false);




    private String id = "";
    private boolean editable = false;

    Criteria(String id, boolean editable) {
        this.id = id;
        this.editable = editable;
    }

    public String getID() {
        return id;
    }

    public boolean isEditable() {
        return editable;
    }

    public static Criteria fromString(String str) {
        for (Criteria c : values()) {
            if (c.toString().equalsIgnoreCase(str)) {
                return c;
            }
            if (c.getID().equalsIgnoreCase(str)) {
                return c;
            }
        }
        return null;
    }
}
