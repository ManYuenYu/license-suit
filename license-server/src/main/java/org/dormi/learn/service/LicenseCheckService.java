/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.service;

import org.apache.commons.codec.binary.Base64;
import org.dormi.learn.pojo.dto.LicenseCheckRequestDTO;
import org.dormi.learn.pojo.dto.LicenseCheckResponseDTO;
import org.dormi.learn.pojo.po.LicenseInfo;
import org.dormi.learn.pojo.po.LicenseInfoMapper;
import org.dormi.learn.utils.crypt.CustomCryptor;
import org.dormi.learn.utils.crypt.DateUtils;
import org.dormi.learn.utils.crypt.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class LicenseCheckService {

    @Autowired
    private LicenseInfoMapper licenseInfoMapper;

    public String check(String input) {
        // 解密
        String crypt1 = CustomCryptor.decode(input);
        String plain = new String(Base64.decodeBase64(crypt1.getBytes(UTF_8)));
        LicenseCheckRequestDTO requestDTO = JsonSerializer.jsonToObj(plain, LicenseCheckRequestDTO.class);
        LicenseInfo info = licenseInfoMapper.selectByAccountAndProduct(requestDTO.getAccount(), requestDTO.getProduct());


        Date now = new Date();
        int pass = 1;

        if (info == null || info.getEnabled() == 0 || info.getExpiredTime().before(now)) {
            pass = 0;
        } else if (!info.getPublicKey().equals(requestDTO.getPublicKey())) {
            pass = 0;
        }

        LicenseCheckResponseDTO responseDTO = new LicenseCheckResponseDTO();
        responseDTO.setPass(pass);
        responseDTO.setExpiredDate(DateUtils.toString(info.getExpiredTime()));
        responseDTO.setServerTs(DateUtils.toString(now));

        // base64 -> custome
        String base64String = Base64.encodeBase64String(responseDTO.toString().getBytes(UTF_8));
        return CustomCryptor.encode(base64String);
    }
}
