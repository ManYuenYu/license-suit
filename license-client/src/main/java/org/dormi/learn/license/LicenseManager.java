/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 授权控制，这里只有明文
 */

package org.dormi.learn.license;

import org.dormi.learn.utils.crypt.ClientUtils;
import org.dormi.learn.utils.crypt.DateUtils;
import org.dormi.learn.utils.license.LicenseFilePlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LicenseManager implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(LicenseManager.class);

    private final String serverAddrPattern = "http://{serverAddr}/license/api/v1/check";
    private LicenseFileReader parser;
    private LicenseFilePlain licenseFilePlain;


    private boolean first = true;
    /** 首次检测延时, 考虑需要等待服务稳定后再发起请求, 防止频繁重启 ～10min 10 * 60 */
    private final int firstCheckDelay = 10;

    private String clientUuid;
    /** 正常检测频率 ~ 1天 24 * 3600 */
    private final int routinFreqency = 1 * 60;

    /** 日常失败后重试时间间隔 */
    private int retryCount = 0;
    private int retryFrequency = 10;
    private boolean shouldRetry = false;

    /** 日常失败次数 */
    private int successCount = 0;
    private int routinFailCount = 0;
    private Date lastServerTs = null;
    private String publicKey = null;
    private String product = null;
    private Date expiredDate = null;
    private String serverAddr = null;

    public LicenseManager(String product) {
        parser = new LicenseFileReader(product);
    }

    @Override
    public void run() {
        checkAlways();
    }

    private void checkAlways() {
        while (true) {
            retryCount = 0;
            routinCheckAndHandleException();
            retryIfNecessary();

            try {
                TimeUnit.SECONDS.sleep(routinFreqency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void retryIfNecessary() {
        while (shouldRetry) {
            try {
                TimeUnit.SECONDS.sleep(retryFrequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("shouldRetry, count={}", retryCount);
            }
            routinCheckAndHandleException();
            retryCount++;
        }
    }

    private void routinCheckAndHandleException() {
        try {
            routinCheck();
            successCount++;
            if (logger.isDebugEnabled()) {
                logger.debug("passed, successCount=" + successCount);
            }
            shouldRetry = false;
        } catch (LicenseException ex) {
            logger.error(ex.getMessage());

            // 如果已经过期，不应本地校验，否则本地校验
            if (ex.getCode() == LicenseException.license_expired) {
                shouldRetry = true;
            } else {
                onAuthenticationFail();
                boolean localCheckPassed = localCheck();
                if (!localCheckPassed) {
                    shouldRetry = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            shouldRetry = true;
        }
    }

    private void routinCheck() throws LicenseException {
        if (logger.isDebugEnabled()) {
            logger.debug(" ========= routinCheck =========");
        }

        if (first) {
            if (logger.isDebugEnabled()) {
                logger.debug("first check");
            }
            first = false;
            try {
                TimeUnit.SECONDS.sleep(firstCheckDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        licenseFilePlain = parser.get();

        // license ok
        publicKey = licenseFilePlain.getPublicKey();
        product = licenseFilePlain.getCode().getProduct();
        expiredDate = DateUtils.fromString(licenseFilePlain.getCode().getExpiredDate());
        serverAddr = serverAddrPattern.replace("{serverAddr}", licenseFilePlain.getCode().getServerAddr());
        expiredDate = DateUtils.fromString(licenseFilePlain.getCode().getExpiredDate());
        clientUuid = ClientUtils.getUuid();

        checkOnce2Server();

    }


    private void checkOnce2Server() throws LicenseException {
        Date now = new Date();
        // make http call to server and check response
        LicenseCheckRequestDTO dto = new LicenseCheckRequestDTO();
        dto.setAccount(licenseFilePlain.getCode().getAccount());
        dto.setProduct(product);
        dto.setClientUuid(clientUuid);
        dto.setPublicKey(publicKey);
        dto.setClientTs(DateUtils.toString(now));
        if (logger.isDebugEnabled()) {
            logger.debug("LicenseCheckRequestDTO, dto=" + dto.toString());
        }

        LicenseCheckResponseDTO responseDTO = LicenseMessenger.checkToServer(serverAddr, dto);
        if (responseDTO == null) {
            throw new LicenseException(LicenseException.license_server_response_invalid, "license_server_response_invalid responseDTO=null");
        }

        if (responseDTO.getPass() != 1) {
            throw new LicenseException(LicenseException.license_expired, "license_expired");
        }

        // success
        routinFailCount = 0;
        if (responseDTO.getExpiredDate() != null) {
            expiredDate = DateUtils.fromString(responseDTO.getExpiredDate());
        }
        lastServerTs = DateUtils.fromString(responseDTO.getServerTs());
        onAuthenticationPass();

    }


    /** 授权失败 */
    private void onAuthenticationFail() {
        if (logger.isDebugEnabled()) {
            logger.debug("onAuthenticationFail");
        }
        // TODO filter...
    }

    /** 授权成功 */
    private void onAuthenticationPass() {
        if (logger.isDebugEnabled()) {
            logger.debug("onAuthenticationPass");
        }
        // TODO filter...
    }

    private boolean localCheck() {
        routinFailCount++;

        if (lastServerTs == null) {
            logger.warn("local check fail: lastServerTs == null");
            return false;
        }
        int second = routinFailCount * routinFreqency;
        Date now = DateUtils.dateAdd(lastServerTs, second);
        if (now.after(expiredDate)) {
            logger.warn("local check expired");
            return false;
        }
        logger.warn("local check passed");
        return true;
    }
}

