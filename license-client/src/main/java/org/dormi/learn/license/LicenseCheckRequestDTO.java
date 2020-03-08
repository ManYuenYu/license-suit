/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 与服务端进行通讯并得到结果
 */

package org.dormi.learn.license;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.dormi.learn.utils.crypt.JsonSerializer;

public class LicenseCheckRequestDTO {

    @JsonProperty("k")
    private String publicKey;

    @JsonProperty("u")
    private String clientUuid;

    @JsonProperty("t")
    private String clientTs;

    @JsonProperty("a")
    private String account;

    @JsonProperty("p")
    private String product;

    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getClientTs() {
        return clientTs;
    }

    public void setClientTs(String clientTs) {
        this.clientTs = clientTs;
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
}

