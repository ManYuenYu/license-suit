/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.service;

import org.springframework.stereotype.Service;

@Service
public class ServerAddrService {

    public String getServerAddr() {
        return "192.168.3.191:8080";
    }
}
