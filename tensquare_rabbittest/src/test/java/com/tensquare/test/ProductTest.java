package com.tensquare.test;

import com.tenquare.rabbit.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public void sendMsg1(){
        rabbitTemplate.convertAndSend("itcast","测试直接模式");
    }

    /**
     * 分裂模式
     */
    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("chuanzhi","","测试分裂模式");
    }

    /**
     * 主题模式
     */
    @Test
    public void sendMsg3(){
        rabbitTemplate.convertAndSend("topic84","good.log","测试主题模式");
    }
}