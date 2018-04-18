package receiving.receiveables;

public class PreKeyBundleRequest implements java.io.Serializable {
    private String base64RequesterUsername;

    public PreKeyBundleRequest(String base64RequesterUsername) {
        this.base64RequesterUsername = base64RequesterUsername;
    }

    public String getBase64RequesterUsername() {
        return base64RequesterUsername;
    }
}
