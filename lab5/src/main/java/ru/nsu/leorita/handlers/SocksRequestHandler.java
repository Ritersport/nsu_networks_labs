package ru.nsu.leorita.handlers;

import ru.nsu.leorita.models.Connection;
import ru.nsu.leorita.socks.SocksResponse;
import ru.nsu.leorita.dns.DnsService;
import ru.nsu.leorita.socks.SocksParser;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import static ru.nsu.leorita.handlers.ConnectHandler.connectToTarget;

public class SocksRequestHandler extends SocksHandler {
    private static final byte DOMAIN_NAME_TYPE = 0x03;

    private static final int NO_ERROR = 0;

    public SocksRequestHandler(Connection connection) {
        super(connection);
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        var outputBuffer = getConnection().getOutputBuffer();
//        outputBuffer.clear();

        read(selectionKey);
        var request = SocksParser.parseRequest(outputBuffer);
        if (request == null)
            return;

        var parseError = request.getParseError();

        if(parseError != NO_ERROR){
            onError(selectionKey, parseError);
            return;
        }

        if(request.getAddressType() == DOMAIN_NAME_TYPE){
            DnsService dnsService = DnsService.getInstance();
            dnsService.resolveName(request,selectionKey);
            return;
        }

        connectToTarget(selectionKey, request.getAddress());
    }

    public static void onError(SelectionKey selectionKey, byte error) {
        var handler = (Handler) selectionKey.attachment();
        var connection = handler.getConnection();

        putErrorResponseIntoBuf(selectionKey, connection, error);
        selectionKey.attach(new SocksErrorHandler(connection));
    }

    public static void putErrorResponseIntoBuf(SelectionKey selectionKey, Connection connection,  byte error) {
        var response = new SocksResponse();
        response.setReply(error);

        var inputBuff = connection.getInputBuffer();
        inputBuff.put(response.toByteBufferWithoutAddress());
        connection.getOutputBuffer().clear();
        selectionKey.interestOpsOr(SelectionKey.OP_WRITE);
    }
}
