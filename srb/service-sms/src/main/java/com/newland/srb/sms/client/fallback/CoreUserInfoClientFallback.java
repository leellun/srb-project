package com.newland.srb.sms.client.fallback;

import com.newland.srb.sms.client.CoreUserInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author: leell
 * Date: 2022/8/11 19:39:10
 */
@Service
@Slf4j
public class CoreUserInfoClientFallback implements CoreUserInfoClient {
    @Override
    public boolean checkMobile(String mobile) {
        log.error("远程调用失败，服务熔断");
        return false;
    }

}
