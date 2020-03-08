/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.service;

import org.dormi.learn.pojo.dto.CreateLicenseDTO;
import org.dormi.learn.pojo.po.LicenseInfo;
import org.dormi.learn.pojo.po.LicenseInfoMapper;
import org.dormi.learn.utils.crypt.DateUtils;
import org.dormi.learn.utils.crypt.RSAUtils;
import org.dormi.learn.utils.license.LicenseCode;
import org.dormi.learn.utils.license.LicenseFile;
import org.dormi.learn.utils.license.LicenseFilePlain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class LicenseFileService {

    private ServerAddrService serverAddrService;

    @Autowired
    public LicenseFileService(ServerAddrService serverAddrService) {
        this.serverAddrService = serverAddrService;
    }

    @Autowired
    private LicenseInfoMapper licenseInfoMapper;

    public LicenseFile getLicenseFile(CreateLicenseDTO dto) {
        Map<String, String> keyPair = RSAUtils.generateRSAKeyPairs();
        String publicKey = keyPair.get("publicKey");
        String privateKey = keyPair.get("privateKey");

        Date now = new Date();

        LicenseCode licenseCode = new LicenseCode();
        licenseCode.setAccount(dto.getAccount());
        licenseCode.setExpiredDate(dto.getExpiredTime());
        licenseCode.setProduct(dto.getProduct());
        licenseCode.setServerAddr(serverAddrService.getServerAddr());

        LicenseFilePlain plainFile = new LicenseFilePlain();
        plainFile.setCode(licenseCode);
        plainFile.setPublicKey(publicKey);

        LicenseFile licenseFile = new LicenseFile(plainFile, privateKey);

        LicenseInfo licenseInfo = new LicenseInfo();
        licenseInfo.setAccount(dto.getAccount());
        licenseInfo.setProduct(dto.getProduct());
        licenseInfo.setExpiredTime(DateUtils.fromString(dto.getExpiredTime()));
        licenseInfo.setPrivateKey(privateKey);
        licenseInfo.setPublicKey(publicKey);
        //
        licenseInfo.setCreatedTime(now);
        licenseInfo.setUpdatedTime(now);
        licenseInfo.setEnabled(1);
        licenseInfoMapper.insert(licenseInfo);

        return licenseFile;
    }
}
