package ru.nsu.leorita.model.mappers;

import ru.nsu.leorita.model.GameConfig;
import ru.nsu.leorita.serializer.SnakesProto;

public class ConfigMapper {
    public static GameConfig toDomen(SnakesProto.GameConfig configProto) {
        return new GameConfig(configProto.getWidth(), configProto.getHeight(), configProto.getFoodStatic(), configProto.getStateDelayMs(), null);
    }
    public static SnakesProto.GameConfig toProtobuf(GameConfig config) {
        return SnakesProto.GameConfig.newBuilder()
                .setWidth(config.getWidth())
                .setHeight(config.getHeight())
                .setFoodStatic(config.getFoodStatic())
                .setStateDelayMs(config.getStateDelayMs())
                .build();
    }
}
