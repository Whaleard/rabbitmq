package com.example.rabbitmq.eight;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * 生产者（消息被拒绝案例）
 */
public class RejectProductor {

    /**
     * 正常情况交换机名称
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", null, message.getBytes());
        }
    }
}
