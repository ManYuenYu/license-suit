/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 文件描述
 */

package org.dormi.learn.license;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Configuration
public class LicenseConfig {

    @Value("${license.product}")
    private String product;

    @Component
    public class FooBar implements ApplicationListener<ContextRefreshedEvent> {

        Thread t = new Thread(new LicenseManager(product));

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            t.start();
        }
    }
}

