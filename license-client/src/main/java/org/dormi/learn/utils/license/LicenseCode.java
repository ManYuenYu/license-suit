/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.license;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dormi.learn.utils.crypt.JsonSerializer;
import org.dormi.learn.utils.crypt.RSAUtils;

public class LicenseCode {

    @JsonProperty("a")
    private String account;

    @JsonProperty("p")
    private String product;

    @JsonProperty("e")
    private String expiredDate;

    @JsonProperty("s")
    private String serverAddr;

    public String encryptByPrivateKey(String privateKey) {
        return RSAUtils.encryptByPrivateKey(toString(), privateKey);
    }

    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }
}
