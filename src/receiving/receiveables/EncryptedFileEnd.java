package receiving.receiveables;

public class EncryptedFileEnd implements java.io.Serializable {

    private byte[] serializedEncryptedFileEnd;

    public EncryptedFileEnd(byte[] serializedEncryptedFileEnd) {
        this.serializedEncryptedFileEnd = serializedEncryptedFileEnd;
    }

    public byte[] getSerializedEncryptedFileEnd() {
        return serializedEncryptedFileEnd;
    }
}
