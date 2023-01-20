package ru.nsu.leorita.client.ui;

import ru.nsu.leorita.model.GameAnnouncement;
import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.model.GameState;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.List;

public interface View {
    void updateGameList(AbstractMap<Long, GameAnnouncement> games);
    void drawNewGameList();
    void repaintField(GameState state, GameConfig config);
    void clearPlayField();
    void showError(String message);
    void shutdown();
}
