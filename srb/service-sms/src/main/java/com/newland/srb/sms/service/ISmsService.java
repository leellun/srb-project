package com.newland.srb.sms.service;

import java.util.Map;

public interface ISmsService {
    void send(String mobile, String templateCode, Map<String,Object> param);
}
