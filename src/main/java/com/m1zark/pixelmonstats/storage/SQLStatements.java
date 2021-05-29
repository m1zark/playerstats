package com.m1zark.pixelmonstats.storage;

import com.google.common.collect.Lists;
import com.m1zark.pixelmonstats.utils.PStats;
import com.google.gson.Gson;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public abstract class SQLStatements {
    private String mainTable;
    private Gson gson = new Gson();

    public SQLStatements(String mainTable) {
        this.mainTable = mainTable;
    }

    public void createTables() {
        try(Connection connection = DataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + this.mainTable + "` (ID INTEGER NOT NULL AUTO_INCREMENT, PlayerUUID CHAR(36), Data LONGTEXT, PRIMARY KEY(ID))")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTables() {
        try(Connection connection = DataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE `" + this.mainTable + "`")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayerData(UUID uuid) {
        try {
            try(Connection connection = DataSource.getConnection()) {
                try(ResultSet results = connection.prepareStatement("SELECT * FROM `" + this.mainTable + "` WHERE playerUUID = '" + uuid + "'").executeQuery()) {
                    if(!results.next()) {
                        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + this.mainTable + "` (PlayerUUID, Data) VALUES (?, ?)")) {
                            statement.setString(1, uuid.toString());
                            statement.setString(2, gson.toJson(new PStats(uuid.toString(),0,0,0,0,0,0,0,0,0,new int[EnumSpecies.values().length + 1],new int[EnumSpecies.values().length + 1])));
                            statement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerData(UUID uuid, PStats data) {
        try {
            try(Connection connection = DataSource.getConnection()) {
                try(PreparedStatement updatePlayer = connection.prepareStatement("UPDATE `" + this.mainTable + "` SET Data = '" + gson.toJson(data) + "' WHERE PlayerUUID = '" + uuid + "'")) {
                    updatePlayer.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PStats getPlayerData(UUID uuid) {
        try(Connection connection = DataSource.getConnection()) {
            try(ResultSet results = connection.prepareStatement("SELECT * FROM `" + this.mainTable + "` WHERE PlayerUUID='" + uuid + "'").executeQuery()) {
                if (results.next()) return gson.fromJson(results.getString("Data"), PStats.class);

                return new PStats(uuid.toString(),0,0,0,0,0,0,0,0,0,new int[EnumSpecies.values().length + 1],new int[EnumSpecies.values().length + 1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PStats> getAllPlayerData() {
        List<PStats> statsList = Lists.newArrayList();

        try(Connection connection = DataSource.getConnection()) {
            try(ResultSet results = connection.prepareStatement("SELECT * FROM `" + this.mainTable + "`").executeQuery()) {
                while(results.next()) statsList.add(gson.fromJson(results.getString("Data"), PStats.class));
            }

            return statsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return statsList;
        }
    }
}
