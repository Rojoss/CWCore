package com.clashwars.cwcore.scoreboard;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.scoreboard.data.BoardData;
import com.clashwars.cwcore.scoreboard.data.ObjectiveData;
import com.clashwars.cwcore.scoreboard.data.TeamData;
import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.*;

public class CWBoard {

    public static Map<String, CWBoard> boards = new HashMap<String, CWBoard>();
    public static Scoreboard emptyBoard = getEmptyBoard();

    private CWCore cwc = CWCore.inst();
    private ScoreboardManager bm = Bukkit.getScoreboardManager();

    private BoardData boardData;
    private Scoreboard scoreboard;

    /**
     * Get or create a CWBoard.
     * If the board doesn't exist internally it will check if it exisits in config.
     * If it also doesn't exisit in config it will return a new scoreboard.
     */
    public static CWBoard get(String ID) {
        if (boards.containsKey(ID)) {
            return boards.get(ID);
        } else if (CWCore.inst().getBoardCfg().hasBoard(ID)) {
            BoardData boardData = CWCore.inst().getBoardCfg().getBoard(ID);
            return new CWBoard(ID, boardData);
        } else {
            return new CWBoard(ID, new BoardData(ID));
        }
    }

    /**
     * Checks if there is a board with the given board identifier name.
     * If checkConfig is true it will also check if the board exists in config if it doesn't exists internally.
     */
    public static boolean hasBoard(String ID, boolean checkConfig) {
        if (boards.containsKey(ID)) {
            return true;
        }
        if (checkConfig && CWCore.inst().getBoardCfg().hasBoard(ID)) {
            return true;
        }
        return false;
    }

    /**
     * Get a empty scoreboard to be used to clear a scoreboard.
     */
    public static Scoreboard getEmptyBoard() {
        if (emptyBoard == null) {
            CWBoard cwb = CWBoard.get("empty");
            cwb.register();
            //cwb.addObjective("empty-side", "", Criteria.DUMMY, DisplaySlot.SIDEBAR, true);
            //cwb.addObjective("empty-tab", "", Criteria.DUMMY, DisplaySlot.PLAYER_LIST, true);
            //cwb.addObjective("empty-name", "", Criteria.DUMMY, DisplaySlot.BELOW_NAME, true);
            cwb.show(true);
            emptyBoard = cwb.scoreboard;
        }
        return emptyBoard;
    }

    /**
     * The constructor.
     * Use CWBoard.get("SomeID") to get a board.
     */
    private CWBoard(String ID, final BoardData boardData) {
        this.boardData = boardData;
        boardData.setID(ID);
        boards.put(ID, this);
    }

    //##########################################################################################################################
    //####################################################### SCOREBOARD #######################################################
    //##########################################################################################################################

    /**
     * Checks if the scoreboard is registered or not.
     */
    public boolean isRegistered() {
        return scoreboard != null;
    }

    /**
     * Register the scoreboard by creating the actual Scoreboard.
     * It will load all data out of the BoardData in to the scoreboard.
     * It won't do anything if the board is already registred.
     */
    public void register() {
        if (scoreboard != null) {
            return;
        }
        if (boardData == null) {
            boardData = new BoardData();
        }

        if (!boards.containsKey(boardData.getID())) {
            boards.put(boardData.getID(), this);
        }

        scoreboard = bm.getNewScoreboard();

        HashMap<String, TeamData> teams = boardData.getTeams();
        for (TeamData team : teams.values()) {
            team.getTeam(scoreboard);
        }

        HashMap<String, ObjectiveData> objectives = boardData.getObjectives();
        for (ObjectiveData objective : objectives.values()) {
            objective.getObjective(scoreboard, boardData);
        }
    }

    /**
     * Unregister a board.
     * The board will still stay in config and all data will be saved.
     * Use delete() to delete the board completely.
     */
    public void unregister() {
        if (scoreboard == null) {
            return;
        }

        if (boardData != null && boardData.isVisible()) {
            hide(null);
        }

        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }

        for (Objective objective : scoreboard.getObjectives()) {
            objective.unregister();
        }
        scoreboard = null;
    }

    /**
     * Delete the scoreboard completely.
     * This will remove the scoreboard from the config!
     */
    public void delete() {
        if (isRegistered()) {
            unregister();
        }
        cwc.getBoardCfg().removeBoard(boardData.getID());
        boards.remove(boardData.getID());
        boardData = null;
    }

    /**
     * Save the board data to config.
     */
    public void save() {
        cwc.getBoardCfg().setBoard(boardData.getID(), boardData);
    }

    /**
     * Get the Bukkit Scoreboard instance.
     * If there is none it will first register the scoreboard.
     */
    public Scoreboard getBukkitBoard() {
        if (scoreboard == null) {
            register();
        }
        return scoreboard;
    }

    /**
     * Get the BoardData that holds all the data for the scoreboard.
     */
    public BoardData getData() {
        return boardData;
    }



    //##########################################################################################################################
    //########################################################## TEAMS #########################################################
    //##########################################################################################################################

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team) {
        return addTeam(team, "", "", team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team, String prefix) {
        return addTeam(team, prefix, "", team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team, String prefix, String suffix) {
        return addTeam(team, prefix, suffix, team, true, true, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team, String prefix, String suffix, boolean friendlyFire, boolean seeInvis) {
        return addTeam(team, prefix, suffix, team, friendlyFire, seeInvis, NameTagVisibility.ALWAYS, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team, String prefix, String suffix, String name, boolean friendlyFire, boolean seeInvis, NameTagVisibility nameTagVisible) {
        return addTeam(team, prefix, suffix, name, friendlyFire, seeInvis, nameTagVisible, null);
    }

    /**
     * Add a team to the board.
     * If the team already exists it will update it with all the specified data.
     */
    public boolean addTeam(String team, String prefix, String suffix, String displayName, boolean friendlyFire, boolean seeInvis, NameTagVisibility nameTagVisible, List<UUID> players) {
        Team t = getBukkitBoard().getTeam(team);
        if (t == null) {
            t = getBukkitBoard().registerNewTeam(team);
        }
        t.setPrefix(CWUtil.integrateColor(prefix));
        t.setSuffix(CWUtil.integrateColor(suffix));
        t.setDisplayName(CWUtil.integrateColor(displayName));
        t.setAllowFriendlyFire(friendlyFire);
        t.setCanSeeFriendlyInvisibles(seeInvis);
        t.setNameTagVisibility(nameTagVisible);
        if (players != null) {
            for (UUID uuid : players) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer != null) {
                    t.addPlayer(offlinePlayer);
                }
            }
        }
        boardData.setTeam(team, new TeamData(team, displayName, prefix, suffix, friendlyFire, seeInvis, nameTagVisible, players));
        save();
        return true;
    }

    /**
     * Add the specified player to the specified team if it exists.
     */
    public void joinTeam(String team, OfflinePlayer player) {
        if (boardData.hasTeam(team)) {
            TeamData teamData = boardData.getTeam(team);
            teamData.addPlayer(player.getUniqueId());
            boardData.setTeam(team, teamData);
            getBukkitBoard().getTeam(team).addPlayer(player);
            save();
        }
    }

    /**
     * Remove the specified player from the specified team if it exists.
     */
    public void leaveTeam(String team, OfflinePlayer player) {
        if (boardData.hasTeam(team)) {
            TeamData teamData = boardData.getTeam(team);
            teamData.removePlayer(player.getUniqueId());
            boardData.setTeam(team, teamData);
            getBukkitBoard().getTeam(team).removePlayer(player);
            save();
        }
    }

    /**
     * Remove a team from the board and unregister it.
     * If fromConfig is true it will also remove the team out of the BoardData
     */
    public void removeTeam(String team, boolean fromConfig) {
        Team t = getBukkitBoard().getTeam(team);
        if (t != null) {
            t.unregister();
        }
        if (fromConfig) {
            boardData.removeTeam(team);
            save();
        }
    }

    /**
     * Get a team by it's identifier.
     * If it doesn't exist it will return null.
     * @deprecated This team should not be modified!
     */
    @Deprecated
    public Team getTeam(String team) {
        return getBukkitBoard().getTeam(team);
    }

    /**
     * Check if the board has a team with the given identifier.
     */
    public boolean hasTeam(String team) {
        return getTeam(team) != null;
    }

    /**
     * Get the map with all teams from this board.
     */
    public Set<Team> getTeams() {
        return getBukkitBoard().getTeams();
    }

    /**
     * Get a list with all teams from this board.
     * @return List with all teams.
     * @deprecated These team values should not be modified!
     */
    @Deprecated
    public List<Team> getTeamList() {
        return new ArrayList<Team>(getTeams());
    }


    //##########################################################################################################################
    //######################################################## OBJECTIVES ######################################################
    //##########################################################################################################################

    /**
     * Add a new objective to the scoreboard and return it.
     * If it already exists it will return that objective.
     * It will also update the ObjectiveData with all specified values.
     */
    public Objective addObjective(String name) {
        return addObjective(name, name, Criteria.DUMMY, null, false);
    }

    /**
     * Add a new objective to the scoreboard and return it.
     * If it already exists it will return that objective.
     * It will also update the ObjectiveData with all specified values.
     */
    public Objective addObjective(String name, Criteria criteria) {
        return addObjective(name, name, criteria, null, false);
    }

    /**
     * Add a new objective to the scoreboard and return it.
     * If it already exists it will return that objective.
     * It will also update the ObjectiveData with all specified values.
     */
    public Objective addObjective(String name, String displayName) {
        return addObjective(name, displayName, Criteria.DUMMY, null, false);
    }

    /**
     * Add a new objective to the scoreboard and return it.
     * If it already exists it will return that objective.
     * It will also update the ObjectiveData with all specified values.
     */
    public Objective addObjective(String name, String displayName, Criteria criteria) {
        return addObjective(name, displayName, criteria, null, false);
    }

    /**
     * Add a new objective to the scoreboard and return it.
     * If it already exists it will return that objective.
     * It will also update the ObjectiveData with all specified values.
     * It will place the objective in the given DisplaySlot for the scoreboard.
     * If force is set to false it won't set it if there is already a objective in the specified slot.
     */
    public Objective addObjective(String name, String displayName, Criteria criteria, DisplaySlot slot, boolean force) {
        Objective objective = getBukkitBoard().getObjective(name);
        if (objective == null) {
            objective = getBukkitBoard().registerNewObjective(name, criteria.getID());
        }

        if (slot != null) {
            if (force) {
                getBukkitBoard().clearSlot(slot);
            }
            objective.setDisplaySlot(slot);
        }
        if (slot == DisplaySlot.SIDEBAR) {
            boardData.setSidebarObjective(name);
        }
        if (slot == DisplaySlot.BELOW_NAME) {
            boardData.setNameObjective(name);
        }
        if (slot == DisplaySlot.PLAYER_LIST) {
            boardData.setTablistObjective(name);
        }

        objective.setDisplayName(CWUtil.integrateColor(displayName));

        boardData.setObjective(name, new ObjectiveData(name, displayName, criteria));
        save();
        return objective;
    }

    /**
     * Get a objective by it's ID.
     * Will return null if there is no objective with this name.
     * @deprecated This objective should not be directly modified!
     */
    @Deprecated
    public Objective getObjective(String objective) {
        return getBukkitBoard().getObjective(objective);
    }

    /**
     * Get the objective in the specified display slot.
     * If there is no objective in the specified slot it will return null.
     * @deprecated This objective should not be directly modified!
     */
    @Deprecated
    public Objective getObjective(DisplaySlot slot) {
        return getBukkitBoard().getObjective(slot);
    }

    /**
     * Get a Set with all objectives this board has.
     * @deprecated These objectives should not be directly modified!
     */
    @Deprecated
    public Set<Objective> getObjectives() {
        return getBukkitBoard().getObjectives();
    }

    /**
     * Get a Score value from the specified objective.
     * If the objective doesn't exist it will return -1
     */
    public int getScore(String objective, String entry) {
        if (getObjective(objective) == null) {
            return -1;
        }
        return getBukkitBoard().getObjective(objective).getScore(entry).getScore();
    }

    /**
     * Get a Score value from the objective in the specified display slot.
     * If there is no objective in the specified slot it will return -1
     */
    public int getScore(DisplaySlot slot, String entry) {
        if (getObjective(slot) == null) {
            return -1;
        }
        return getObjective(slot).getScore(entry).getScore();
    }

    /**
     * Set the score value of the specified entry and objective.
     * If the objective doesn't exist it won't set the score.
     */
    public void setScore(String objective, String entry, int value) {
        if (getObjective(objective) == null) {
            return;
        }
        getObjective(objective).getScore(entry).setScore(value);
        ObjectiveData objectiveData = boardData.getObjective(objective);
        objectiveData.setScore(entry, value);
        boardData.setObjective(objective, objectiveData);
        save();
    }

    /**
     * Set the score value of the specified entry and slot.
     * If there is no objective in the specified slot it won't set the score.
     */
    public void setScore(DisplaySlot slot, String entry, int value) {
        if (getObjective(slot) == null) {
            return;
        }
        getObjective(slot).getScore(entry).setScore(value);
        ObjectiveData objectiveData = boardData.getObjective(getObjective(slot).getName());
        objectiveData.setScore(entry, value);
        boardData.setObjective(objectiveData.getName(), objectiveData);
        save();
    }

    /**
     * Reset all scores for the specified entry.
     */
    public void resetScore(String entry) {
        getBukkitBoard().resetScores(entry);
        for (ObjectiveData objective : boardData.getObjectives().values()) {
            objective.removeScore(entry);
            boardData.setObjective(objective.getName(), objective);
        }
        save();
    }

    /**
     * Get a Set with all the entries in the scoreboard.
     */
    public Set<String> getEntries() {
        return getBukkitBoard().getEntries();
    }



    //##########################################################################################################################
    //########################################################## UTILS #########################################################
    //##########################################################################################################################

    /**
     * Check if the board is shown or not.
     * If it's visible it doesn't mean players can see it.
     * Only players added to the board will be able to see it.
     */
    public boolean isVisible() {
        return boardData.isVisible();
    }

    /**
     * Add a player to the scoreboard.
     * All players added will be able to see the scoreboard if it's shown.
     * If the board is already visible it will directly show the board to the player.
     */
    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId());
    }

    /**
     * Add a player to the scoreboard.
     * All players added will be able to see the scoreboard if it's shown.
     * If the board is already visible it will directly show the board to the player.
     */
    public void addPlayer(UUID uuid) {
        if (!boardData.hasPlayer(uuid)) {
            cwc.getPlayerCfg().setScoreboard(uuid, boardData.getID());
            boardData.addPlayer(uuid);
            save();
            if (isVisible()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    player.setScoreboard(getBukkitBoard());
                }
            }
        }
    }

    /**
     * Remove a player from the scoreboard.
     * If the board is visible it will clear the board for the player.
     */
    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }

    /**
     * Remove a player from the scoreboard.
     * This has no effect when the board is set global.
     */
    public void removePlayer(UUID uuid) {
        if (boardData.hasPlayer(uuid)) {
            cwc.getPlayerCfg().removeScoreboard(uuid);
            boardData.removePlayer(uuid);
            save();
            if (isVisible()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    player.setScoreboard(getEmptyBoard());
                }
            }

        }
    }

    /**
     * Check if the specified player has been added to this board or not.
     */
    public boolean hasPlayer(Player player) {
        return hasPlayer(player.getUniqueId());
    }

    /**
     * Check if the specified player has been added to this board or not.
     */
    public boolean hasPlayer(UUID uuid) {
        return boardData.hasPlayer(uuid);
    }

    /**
     * Get all players who are added to this board.
     */
    public List<UUID> getPlayers() {
        return boardData.getPlayers();
    }


    /**
     * Show this board to all players that have been added to this board.
     * If the board is already visible this won't do anything.
     * If override is set to false it won't change the scoreboard of a player if he already has one set.
     */
    public void show(boolean override) {
        if (!override && boardData.isVisible()) {
            return;
        }
        boardData.setVisible(true);
        save();

        Scoreboard scoreboard = getBukkitBoard();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (boardData.hasPlayer(player.getUniqueId())) {
                if ((player.getScoreboard() == null || player.getScoreboard().getObjective("empty-side") != null) || override) {
                    player.setScoreboard(scoreboard);
                }
            }
        }
    }

    /**
     * Hide this board for all players that have been added to this board.
     * If the board is already hidden this won't do anything.
     * If a Scoreboard is provided it will set the player his scoreboard to the provided scoreboard.
     * If it's null it will set the scoreboard to a empty one.
     */
    public void hide(Scoreboard newBoard) {
        if (!boardData.isVisible()) {
            return;
        }
        boardData.setVisible(false);
        save();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (boardData.hasPlayer(player.getUniqueId())) {
                player.setScoreboard(newBoard == null ? getEmptyBoard() : newBoard);
            }
        }
    }


}
