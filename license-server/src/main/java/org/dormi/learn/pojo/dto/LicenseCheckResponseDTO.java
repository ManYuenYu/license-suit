/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 与服务端进行通讯并得到结果
 */

package org.dormi.learn.pojo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.dormi.learn.utils.crypt.JsonSerializer;

public class LicenseCheckResponseDTO {
    @JsonProperty("s")
    private String serverTs;
    @JsonProperty("p")
    private Integer pass;
    @JsonProperty("i")
    private Integer interval;
    @JsonProperty("e")
    private String expiredDate;

    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

    public String getServerTs() {
        return serverTs;
    }

    public void setServerTs(String serverTs) {
        this.serverTs = serverTs;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

}

