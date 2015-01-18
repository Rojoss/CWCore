package com.clashwars.cwcore.scoreboard;

import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class CWBoard {

    public static Map<String, CWBoard> boards = new HashMap<String, CWBoard>();
    public static CWBoard activeBoard;
    public static Map<UUID, Scoreboard> prevBoards = new HashMap<UUID, Scoreboard>();

    private ScoreboardManager bm = Bukkit.getScoreboardManager();

    private String ID;
    private boolean global;
    private boolean visible;
    private Scoreboard board;

    private HashMap<String, Team> teams = new HashMap<String, Team>();
    private List<UUID> players = new ArrayList<UUID>();



    /**
     * Get or create a CWBoard.
     * @param ID The identifier of the board.
     * @return CWBoard
     */
    public static CWBoard get(String ID) {
        if (boards.containsKey(ID)) {
            return boards.get(ID);
        } else {
            CWBoard board = new CWBoard(ID);
            boards.put(ID, board);
            return board;
        }
    }

    /**
     * The constructor.
     * Use CWBoard.get("SomeID") to get a board.
     * @param ID The board ID
     */
    private CWBoard(String ID) {
        this.ID = ID;
    }

    //##########################################################################################################################
    //####################################################### SCOREBOARD #######################################################
    //##########################################################################################################################

    /**
     * Initialize the scoreboard.
     * @return TRUE if it registered, FALSE if it was already registered.
     */
    public boolean init() {
        return init(false);
    }

    /**
     * Initialize the scoreboard.
     * @param global If this is true when showing the board all players will see it including new players that join.
     *               If it's set to false you have to manually set who can see the board.
     * @return TRUE if it registered, FALSE if it was already registered.
     */
    public boolean init(boolean global) {
        if (board != null) {
            return false;
        }

        board = bm.getNewScoreboard();
        this.global = global;

        return true;
    }

    /**
     * Unregister a board and remove all data.
     */
    public void unregister() {
        hide();

        for (String team : teams.keySet()) {
            teams.get(team).unregister();
        }
        teams.clear();

        if (activeBoard.getID() == getID()) {
            activeBoard = null;
        }

        getTeamList();

        board = null;
    }

    /**
     * Get the Bukkit Scoreboard instance.
     * @return Scoreboard
     */
    public Scoreboard getBoard() {
        return board;
    }



    //##########################################################################################################################
    //########################################################## TEAMS #########################################################
    //##########################################################################################################################

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team) {
        return addTeam(team, "", "", team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @param prefix Prefix which is added in front of the player his name.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team, String prefix) {
        return addTeam(team, prefix, "", team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @param prefix Prefix which is added in front of the player his name.
     * @param suffix Suffix which is added after the player his name.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team, String prefix, String suffix) {
        return addTeam(team, prefix, suffix, team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @param prefix Prefix which is added in front of the player his name.
     * @param suffix Suffix which is added after the player his name.
     * @param friendlyFire If true team members can damage eachother and if false not.
     * @param seeInvis If true team members can see eachother in ghost form when they are invisible.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team, String prefix, String suffix, boolean friendlyFire, boolean seeInvis) {
        return addTeam(team, prefix, suffix, team, friendlyFire, seeInvis, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @param prefix Prefix which is added in front of the player his name.
     * @param suffix Suffix which is added after the player his name.
     * @param name The display name of the team.
     * @param friendlyFire If true team members can damage eachother and if false not.
     * @param seeInvis If true team members can see eachother in ghost form when they are invisible.
     * @param nameTagVisible Can be set to different modes which sets who can't see the nametag from the player.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team, String prefix, String suffix, String name, boolean friendlyFire, boolean seeInvis, NameTagVisibility nameTagVisible) {
        return addTeam(team, prefix, suffix, name, friendlyFire, seeInvis, nameTagVisible, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will return false and won't get recreated.
     * @param team The ID of the team.
     * @param prefix Prefix which is added in front of the player his name.
     * @param suffix Suffix which is added after the player his name.
     * @param name The display name of the team.
     * @param friendlyFire If true team members can damage eachother and if false not.
     * @param seeInvis If true team members can see eachother in ghost form when they are invisible.
     * @param nameTagVisible Can be set to different modes which sets who can't see the nametag from the player.
     * @param players A list of players that will be added to the team.
     * @return TRUE if added, FALSE if the team already exists.
     */
    public boolean addTeam(String team, String prefix, String suffix, String name, boolean friendlyFire, boolean seeInvis, NameTagVisibility nameTagVisible, List<OfflinePlayer> players) {
        if (teams.containsKey(team)) {
            return false;
        }
        Team t = board.registerNewTeam(team);
        t.setPrefix(CWUtil.integrateColor(prefix));
        t.setSuffix(CWUtil.integrateColor(suffix));
        t.setDisplayName(CWUtil.integrateColor(name));
        t.setAllowFriendlyFire(friendlyFire);
        t.setCanSeeFriendlyInvisibles(seeInvis);
        t.setNameTagVisibility(nameTagVisible);
        if (players != null) {
            for (OfflinePlayer player : players) {
                t.addPlayer(player);
            }
        }
        teams.put(team, t);
        return true;
    }

    /**
     * Remove a team from the board and unregister it.
     * @param team The team identifier.
     */
    public void removeTeam(String team) {
        if (!teams.containsKey(team)) {
            return;
        }
        getTeam(team).unregister();
        teams.remove(team);
    }

    /**
     * Get a team by it's identifier.
     * @param team The team identifier.
     * @return Team or null
     */
    public Team getTeam(String team) {
        return teams.get(team);
    }

    /**
     * Check if the board has a team with the given identifier.
     * @param team The team identifier.
     * @return TRUE if it has it, FALSE if it doesn't have it.
     */
    public boolean hasTeam(String team) {
        return teams.containsKey(team);
    }

    /**
     * Get the map with all teams from this board.
     * @return Map with all team id's and teams.
     */
    public Map<String, Team> getTeams() {
        return teams;
    }

    /**
     * Get a list with all teams from this board.
     * @return List with all teams.
     */
    public List<Team> getTeamList() {
        List<Team> teams = new ArrayList<Team>();
        for (Team team : this.teams.values()) {
            teams.add(team);
        }
        return teams;
    }


    //##########################################################################################################################
    //######################################################## OBJECTIVES ######################################################
    //##########################################################################################################################

    /**
     * Add a new objective to the scoreboard.
     * If the objective already exists it will return null.
     * @param name The identifier of the objective.
     * @return Objective or null.
     */
    public Objective addObjective(String name) {
        return addObjective(name, Criteria.DUMMY, null, false);
    }

    /**
     * Add a new objective to the scoreboard.
     * If the objective already exists it will return null.
     * @param name The identifier of the objective.
     * @param criteria The Criteria for the objective.
     * @return Objective or null.
     */
    public Objective addObjective(String name, Criteria criteria) {
        return addObjective(name, criteria, null, false);
    }

    /**
     * Add a new objective to the scoreboard.
     * If the objective already exists it will return null.
     * @param name The identifier of the objective.
     * @param criteria The Criteria for the objective.
     * @param slot The DisplaySlot for the objective.
     * @param force If set to true and the slot is already used with another objective it will first clear the previous objective else it wont get added.
     * @return Objective or null.
     */
    public Objective addObjective(String name, Criteria criteria, DisplaySlot slot, boolean force) {
        if (board.getObjective(name) != null) {
            return null;
        }

        Objective obj = board.registerNewObjective(name, criteria.getID());
        if (slot != null) {
            if (force) {
                board.clearSlot(slot);
            }
            obj.setDisplaySlot(slot);
        }

        return obj;
    }

    /**
     * Get a objective by it's ID.
     * @param objective The string ID of the objective
     * @return Objective
     */
    public Objective getObjective(String objective) {
        return board.getObjective(objective);
    }

    /**
     * Get a objective from a display slot.
     * @param slot The displayslot to return the objective from.
     * @return Objective
     */
    public Objective getObjective(DisplaySlot slot) {
        return board.getObjective(slot);
    }

    /**
     * Get a Set with all objectives this board has.
     * @return Set with Objectives
     */
    public Set<Objective> getObjectives() {
        return board.getObjectives();
    }

    /**
     * Get a Score object.
     * @param objective The objective string ID.
     * @param player The score player key (doesn't have to be a actual player)
     * @return Score
     */
    public Score getScore(String objective, String player) {
        return board.getObjective(objective).getScore(player);
    }

    /**
     * Get a Score object.
     * @param slot The objective display slot.
     * @param player The score player key (doesn't have to be a actual player)
     * @return Score
     */
    public Score getScore(DisplaySlot slot, String player) {
        return board.getObjective(slot).getScore(player);
    }



    //##########################################################################################################################
    //########################################################## UTILS #########################################################
    //##########################################################################################################################

    /**
     * Get the ID of this board.
     * @return The ID of the board.
     */
    public String getID() {
        return ID;
    }

    /**
     * Check if the board is shown or not.
     * It does not mean if it's visible that players can actually see it.
     * This depends if it's global or not and if the player is added.
     * @return TRUE if visible, FALSE if not.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the board to global.
     * When the board is global all players can see the board.
     * @param global Set if the board should be global or not.
     */
    public void setGlobal(boolean global) {
        this.global = global;
    }

    /**
     * Check if a board is global or not.
     * @return TRUE of it's global, FALSE if it isn't.
     */
    public boolean isGlobal() {
        return global;
    }

    /**
     * Add a player to the scoreboard.
     * All players added will be able to see the scoreboard if it's shown.
     * this has no effect when the board is set global.
     * @param player The Player to add.
     */
    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId());
    }

    /**
     * Add a player to the scoreboard.
     * All players added will be able to see the scoreboard if it's shown.
     * this has no effect when the board is set global.
     * @param uuid The UUID of the player to add.
     */
    public void addPlayer(UUID uuid) {
        if (!players.contains(uuid)) {
            players.add(uuid);
        }
    }

    /**
     * Check if the specified player can see this board or not.
     * This ignored the global flag so do a check for that separate.
     * @param player The Player to check.
     * @return TRUE if the player can see it, FALSE if not.
     */
    public boolean canSee(Player player) {
        return canSee(player.getUniqueId());
    }

    /**
     * Check if the specified player can see this board or not.
     * This ignored the global flag so do a check for that separate.
     * @param uuid The UUID of the player to check.
     * @return TRUE if the player can see it, FALSE if not.
     */
    public boolean canSee(UUID uuid) {
        return players.contains(uuid);
    }

    /**
     * Get all players who can see this board.
     * @return
     */
    public List<UUID> getPlayers() {
        return players;
    }


    /**
     * Show this board to players.
     * If the board is global it will show it to all online players.
     * If the board isn't global it will show the board to all players which were added with addPlayer()
     */
    public void show() {
        if (visible) {
            return;
        }
        visible = true;
        activeBoard = this;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (global || players.contains(player.getUniqueId())) {
                prevBoards.put(player.getUniqueId(), player.getScoreboard());
                player.setScoreboard(board);
            }
        }
    }

    /**
     * Hide this board.
     * It will try and set the scoreboard that the player had before this one was shown.
     * It's important to first hide and then show and not the other way around.
     * If you first show the board and then hide another board it might also hide the new board.
     */
    public void hide() {
        if (!visible) {
            return;
        }
        visible = false;
        activeBoard = this;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (global || players.contains(player.getUniqueId())) {
                player.setScoreboard(prevBoards.get(player.getUniqueId()));
            }
        }
    }


    /**
     * Update the board by showing it to all players.
     * Normally it's not required to call this but in some cases it might be required.
     * If force is true it will set the board again even if the player already has this board shown.
     * @param force If set to true it will set the board of the player again even if the player already has this board shown.
     */
    public void update(boolean force) {
        if (!isVisible()) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isGlobal()) {
                if (!player.getScoreboard().equals(getBoard()) || force) {
                    prevBoards.put(player.getUniqueId(), player.getScoreboard());
                    player.setScoreboard(board);
                }
            } else {
                if (players.contains(player.getUniqueId())) {
                    if (!player.getScoreboard().equals(getBoard()) || force) {
                        prevBoards.put(player.getUniqueId(), player.getScoreboard());
                        player.setScoreboard(board);
                    }
                }
            }
        }
    }


}
