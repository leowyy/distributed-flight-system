package server;

import java.net.InetAddress;

public class ClientRecord {
    private InetAddress clientAddress;
    private int clientPort;
    private int id;

    public ClientRecord(InetAddress clientAddress, int clientPort, int id) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof ClientRecord) {
            ClientRecord s = (ClientRecord)obj;
            return clientAddress.equals(s.clientAddress) && clientPort == s.clientPort && id == s.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return clientAddress.hashCode() + clientPort * 31 + id * 31;
    }
}
