package ru.nsu.leorita.rdt;

public interface TransferPublisher {
    void notifySubscribers(ReceivedMessage message);

    void addSubscriber(Subscriber subscriber);

    interface Subscriber {
        void update(ReceivedMessage message);
    }

}
