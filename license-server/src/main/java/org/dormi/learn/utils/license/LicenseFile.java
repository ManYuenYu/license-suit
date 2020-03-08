/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.license;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dormi.learn.utils.crypt.JsonSerializer;
import org.dormi.learn.utils.crypt.RSAUtils;

public class LicenseFile {

    @JsonIgnore
    private LicenseFilePlain plain;

    @JsonProperty("p")
    private String publicKey;

    @JsonProperty("c")
    private String content;

    public LicenseFile(LicenseFilePlain plainFile, String priavteKey) {
        this.publicKey = plainFile.getPublicKey();
        this.content = RSAUtils.encryptByPrivateKey(plainFile.getCode().toString(), priavteKey);
        this.plain = plainFile;
    }

    public LicenseFile() {
    }

    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

    public LicenseFilePlain getPlain() {
        return plain;
    }

    public void setPlain(LicenseFilePlain plain) {
        this.plain = plain;
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
