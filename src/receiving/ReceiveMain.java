package receiving;

import net.i2p.client.I2PSession;
import net.i2p.client.streaming.I2PServerSocket;
import net.i2p.client.streaming.I2PSocketManager;
import net.i2p.client.streaming.I2PSocketManagerFactory;
import net.i2p.data.PrivateKeyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReceiveMain implements Runnable {

    private Thread t;
    private String userAddress;

    public String getUserAddress() {
        return userAddress;
    }

    @Override
    public void run() {
        InputStream file = null;

        try {
            PrivateKeyFile data = new PrivateKeyFile(new File("./destination.mal"));
            data.createIfAbsent();
            file = new FileInputStream("./destination.mal");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            //This is where to change the address and port to reflect your i2cp host.
            I2PSocketManager manager = I2PSocketManagerFactory.createManager(file, "1.1.1.33", 7654, null);
            I2PServerSocket serverSocket = manager.getServerSocket();
            I2PSession session = manager.getSession();

            //Print the base64 string, the regular string would look like garbage.
            userAddress = session.getMyDestination().toBase64();

            while (!session.isClosed()) {
                try {
                    new ReceiveThread(serverSocket.accept()).start();
                } catch (Exception e) {
                    System.out.println("Timeout");
                }
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
