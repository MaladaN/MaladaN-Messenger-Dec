package receiving.receiveables;

public class EncryptedFileSpan implements java.io.Serializable {

    private byte[] serializedEncryptedFileSpan;

    public EncryptedFileSpan(byte[] serializedEncryptedFileSpan) {
        this.serializedEncryptedFileSpan = serializedEncryptedFileSpan;
    }

    public byte[] getSerializedEncryptedFileSpan() {
        return serializedEncryptedFileSpan;
    }
}
