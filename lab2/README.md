# Lab 2

---

### Build

```
./gradlew build
```

### Run server

```
./gradlew run --args="-r"
```

### Run client

```
./gradlew run --args="-f=/path/to/file"
```

### About protocol


There are 6 types of packets:

*INIT*  - Client sends it to Server to initiate connection, it has name of file and checksum inside it

*ACK* - Server sends it to Client to accept connection

*DATA* - Client sends this packet to Server while transmitting file, it has piece of file inside it

*END* - Client sends this packet to Server to notify that file transmitting ended

*SUCCESS* - Server sends this packet to Client after receiving *END* packet to notify that file received successfully

*FAIL* - Server send this packet to Client after receiving *END* packet to notify that transmitting failed