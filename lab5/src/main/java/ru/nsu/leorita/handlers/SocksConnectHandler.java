package ru.nsu.leorita.handlers;

import ru.nsu.leorita.model.Connection;
import ru.nsu.leorita.protocol.ConnectRequest;
import ru.nsu.leorita.protocol.ConnectResponse;
import ru.nsu.leorita.protocol.Parser;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class SocksConnectHandler extends SocksHandler{
    private static final byte NO_AUTHENTICATION = 0x00;

    private static final int SOCKS_VERSION = 0x05;

    private static final byte NO_COMPARABLE_METHOD = (byte) 0xFF;

    public SocksConnectHandler(Connection connection) {
        super(connection);
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        Connection connection = getConnection();
        var outputBuffer = connection.getOutputBuffer();
        read(selectionKey);

        ConnectRequest connectRequest = Parser.parseConnect(outputBuffer);
        if(connectRequest == null)
            return;

        ConnectResponse connectResponse = new ConnectResponse();
        if(!checkRequest(connectRequest))
            connectResponse.setMethod(NO_COMPARABLE_METHOD);

        var inputBuffer = connection.getInputBuffer();
        inputBuffer.put(connectResponse.toBytes());

        selectionKey.interestOpsOr(SelectionKey.OP_WRITE);
        selectionKey.attach(new SocksRequestHandler(connection));
        connection.getOutputBuffer().clear();
    }

    private boolean checkRequest(ConnectRequest connectRequest){
        return connectRequest.getVersion() == SOCKS_VERSION
                && checkMethods(connectRequest.getMethods());
    }

    private static boolean checkMethods(byte[] methods){
        for(var method : methods){
            if(method == NO_AUTHENTICATION)
                return true;
        }

        return false;
    }
}
