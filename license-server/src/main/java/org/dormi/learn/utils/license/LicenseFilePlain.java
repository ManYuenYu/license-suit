/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.license;

import org.dormi.learn.utils.crypt.JsonSerializer;

public class LicenseFilePlain {

    private String publicKey;

    private LicenseCode code;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public LicenseCode getCode() {
        return code;
    }

    public void setCode(LicenseCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

}
