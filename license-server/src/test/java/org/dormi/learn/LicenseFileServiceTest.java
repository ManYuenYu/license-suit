/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn;

import org.dormi.learn.pojo.dto.CreateLicenseDTO;
import org.dormi.learn.service.LicenseFileService;
import org.dormi.learn.service.ServerAddrService;
import org.dormi.learn.utils.crypt.RSAUtils;
import org.dormi.learn.utils.license.LicenseCode;
import org.dormi.learn.utils.license.LicenseFile;
import org.dormi.learn.utils.license.LicenseFilePlain;
import org.dormi.learn.utils.license.LicenseFileUtils;
import org.junit.Test;

import java.util.Map;

public class LicenseFileServiceTest {

    @Test
    public void test1() {
        ServerAddrService serverAddrService = new ServerAddrService();
        LicenseFileService licenseFileService = new LicenseFileService(serverAddrService);

        // 生成license
        CreateLicenseDTO dto = new CreateLicenseDTO();
        dto.setAccount("oppo");
        dto.setProduct("spaas");
        dto.setExpiredTime("2020-12-31 23:59:59");

        Map<String, String> keyPair = RSAUtils.generateRSAKeyPairs();
        String publicKey = keyPair.get("publicKey");
        String privateKey = keyPair.get("privateKey");


        LicenseCode licenseCode = new LicenseCode();
        licenseCode.setAccount(dto.getAccount());
        licenseCode.setExpiredDate(dto.getExpiredTime());
        licenseCode.setProduct(dto.getProduct());
        licenseCode.setServerAddr(serverAddrService.getServerAddr());

        LicenseFilePlain plainFile = new LicenseFilePlain();
        plainFile.setCode(licenseCode);
        plainFile.setPublicKey(publicKey);

        LicenseFile licenseFile = new LicenseFile(plainFile, privateKey);

        // 测试
        System.out.println(LicenseFileUtils.getFileName(licenseFile));
        String content = LicenseFileUtils.toStringContent(licenseFile);
        System.out.println(content);

        // 还原
        System.out.println("还原");
        LicenseFilePlain file2 = LicenseFileUtils.fromStringContent(content);
        System.out.println(file2);
    }
}
