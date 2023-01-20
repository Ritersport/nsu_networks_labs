package ru.nsu.leorita.client.ui;

import ru.nsu.leorita.model.GameAnnouncement;
import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.model.GameState;

import java.util.AbstractMap;

public interface View {
    void updateGameList(AbstractMap<Long, GameAnnouncement> games);

    void drawNewGameList();

    void repaintField(GameState state, GameConfig config, int localId);

    void clearPlayField();

    void showError(String message);

    void shutdown();
}
