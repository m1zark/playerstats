package com.m1zark.pixelmonstats.enums;

import com.pixelmonmod.pixelmon.RandomHelper;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum StatTypes {
    CATCH (1, "Catch"),
    DEFEAT (2, "Defeat"),
    LEVEL (3, "Level"),
    BREED (4, "Breed"),
    EVOLVE (5, "Evolve"),
    COLLECT (6, "Collect"),
    SHRINE (7, "Activate");

    private final int id;
    private final String task;

    StatTypes(int id, String name) {
        this.id = id;
        this.task = name;
    }

    public static StatTypes getTaskFromID(int index) {
        for(StatTypes task : StatTypes.values()) {
            if(task.getId() == index) return task;
        }

        return null;
    }

    public static Optional<StatTypes> getTaskFromName(String name) {
        for(StatTypes task : StatTypes.values()) {
            if(task.getTask().toLowerCase().equals(name.toLowerCase())) return Optional.of(task);
        }

        return Optional.empty();
    }

    public static StatTypes getRandomTask() {
        return StatTypes.getTaskFromID(RandomHelper.getRandomNumberBetween(1,7));
    }
}
