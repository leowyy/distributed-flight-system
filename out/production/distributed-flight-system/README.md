# distributed-flight-system

```bash
javac -d target -cp .:lib/* common/*.java
javac -d target -cp .:lib/* client/*.java
javac -d target -cp .:lib/* server/*.java
java -classpath ./target client.UDPClient
java -classpath ./target server.UDPServer
```
