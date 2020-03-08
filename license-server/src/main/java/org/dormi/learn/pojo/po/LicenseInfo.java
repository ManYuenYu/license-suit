package org.dormi.learn.pojo.po;

import org.dormi.learn.pojo.base.BasePO;

import java.util.Date;

public class LicenseInfo extends BasePO {

    private String account;

    private String product;

    private Date expiredTime;

    private Integer maxNode;

    private String publicKey;

    private String privateKey;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getMaxNode() {
        return maxNode;
    }

    public void setMaxNode(Integer maxNode) {
        this.maxNode = maxNode;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey == null ? null : publicKey.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

}