package crypto;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.ecc.Curve;
import org.whispersystems.libsignal.state.PreKeyBundle;

public class ResponsePreKeyBundle implements java.io.Serializable {
    //got this.
    private int registrationId;
    private PreKeyPublic preKey;
    private int signedPreKeyId;
    private byte[] signedPreKeyPublic;
    private byte[] signedPreKeySignature;
    private byte[] identityKey;
    private String base64Address;
    private String theirBase64Address;
    //deviceId not present, only one device.

    public ResponsePreKeyBundle(int registrationId, PreKeyPublic preKey, int signedPreKeyId, byte[] signedPreKeyPublic,
                                byte[] signedPreKeySignature, byte[] identityKey, String ourBase64Address,
                                String theirBase64Address) {
        this.registrationId = registrationId;
        this.preKey = preKey;
        this.signedPreKeyId = signedPreKeyId;
        this.signedPreKeyPublic = signedPreKeyPublic;
        this.signedPreKeySignature = signedPreKeySignature;
        this.identityKey = identityKey;
        this.base64Address = ourBase64Address;
        this.theirBase64Address = theirBase64Address;
    }

    public PreKeyBundle getPreKeyBundle() {
        try {
            return new PreKeyBundle(registrationId, 0, preKey.getPrekeyId(), preKey.getPreKeyPublic(), this.signedPreKeyId, Curve.decodePoint(this.signedPreKeyPublic, 0), this.signedPreKeySignature, new IdentityKey(identityKey, 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBase64Address() {
        return base64Address;
    }

    public String getReciepent() {
        return theirBase64Address;
    }
}
