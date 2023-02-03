## Instant Drop Server
Signaling Server for Instant Drop
### Tech Stack
- Java
- Spring Boot
- WebSocket
- Protocol Buffer

## Starting SprintBoot Server
```
$ gradle bootRun
```

## Generating auto-generated types by protobuf
```
$ protoc --java_out=./src/main/java/ ./src/main/java/wsjava/signaling/wsjava.proto
```
