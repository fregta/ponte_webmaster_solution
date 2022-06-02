package hu.ponte.hr.services;

import hu.ponte.hr.domain.Image;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
public class SignService {

    public SignService() {
    }

    public Signature addDigitalSignature(MultipartFile originalImage, Image imageToSave) throws SignatureException, IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        byte[] originalImageData = originalImage.getBytes();
        PrivateKey pvt = getPrivateKey("src/main/resources/config/keys/key.private");
        signature.initSign(pvt);
        signature.update(originalImageData);
        imageToSave.setData(originalImageData);
        return signature;
    }

    public String encodeDigitalSignature(Signature signature) throws SignatureException {
        byte[] signatureBytes = signature.sign();
        return new Base64().encodeToString(signatureBytes);
    }

    private PrivateKey getPrivateKey(String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(new File(filePath).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
