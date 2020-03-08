/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.license;

import org.apache.commons.codec.binary.Base64;
import org.dormi.learn.utils.crypt.CustomCryptor;
import org.dormi.learn.utils.crypt.JsonSerializer;
import org.dormi.learn.utils.crypt.RSAUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LicenseFileUtils {

    public static String getFileName(LicenseFile licenseFile) {
        return licenseFile.getPlain().getCode().getProduct() + ".license.txt";
    }

    public static String toStringContent(LicenseFile licenseFile) {
        String json = licenseFile.toString();
        String base64String = Base64.encodeBase64String(json.getBytes(UTF_8));
        String customerEncode = CustomCryptor.encode(base64String);
        return customerEncode;
    }

    public static LicenseFilePlain fromStringContent(String content) {
        String base64String = CustomCryptor.decode(content);
        String plain = new String(Base64.decodeBase64(base64String));

        LicenseFile licenseFile = JsonSerializer.jsonToObj(plain, LicenseFile.class);
        String licenseCodeStr = new String(Base64.decodeBase64(RSAUtils.decryptByPublicKey(licenseFile.getContent(), licenseFile.getPublicKey())));
        LicenseCode licenseCode = JsonSerializer.jsonToObj(licenseCodeStr, LicenseCode.class);
        //
        LicenseFilePlain licenseFilePlain = new LicenseFilePlain();
        licenseFilePlain.setPublicKey(licenseFile.getPublicKey());
        licenseFilePlain.setCode(licenseCode);
        return licenseFilePlain;
    }
}
