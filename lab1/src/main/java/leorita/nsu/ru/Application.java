package leorita.nsu.ru;

import java.net.InetAddress;
import java.util.UUID;

public class Application {
    private UUID appID;
    private InetAddress groupID;
    private static Application instance;
    private Application(InetAddress groupID){
        appID = UUID.randomUUID();
        this.groupID = groupID;
    }
    static Application getInstance(InetAddress groupID){
        if (instance == null) {
            instance = new Application(groupID);
        }
        return instance;
    }

    public UUID getAppID() {
        return appID;
    }

    public InetAddress getGroupID() {
        return groupID;
    }
}
