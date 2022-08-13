package com.newland.srb.sms.receiver;

import com.newland.srb.rabbitmq.constant.MQConst;
import com.newland.srb.base.dto.SmsDTO;
import com.newland.srb.sms.service.ISmsService;
import com.newland.srb.sms.utils.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Author: leell
 * Date: 2022/8/13 13:36:31
 */
@Component
@Slf4j
public class SmsReceiver {

    @Autowired
    private ISmsService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConst.QUEUE_SMS_ITEM, durable = "true"),
            exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
            key = {MQConst.ROUTING_SMS_ITEM}
    ))
    public void send(SmsDTO smsDTO) {
        log.info("SmsReceiver消息监听。。。。。。");
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", smsDTO.getMessage());
        try{
            smsService.send(smsDTO.getMobile(), SmsProperties.TEMPLATE_CODE, param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
