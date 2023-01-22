package ru.nsu.leorita.services;

import java.nio.channels.SelectionKey;

public class DnsMap {
    private SelectionKey selectionKey;

    private short targetPort;

    public DnsMap(SelectionKey selectionKey, short targetPort) {
        this.selectionKey = selectionKey;
        this.targetPort = targetPort;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public short getTargetPort() {
        return targetPort;
    }
}
