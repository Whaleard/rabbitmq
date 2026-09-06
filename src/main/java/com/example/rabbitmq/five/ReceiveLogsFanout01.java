package com.example.rabbitmq.five;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 消息接收
 */
public class ReceiveLogsFanout01 {

    // 交换机名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 声明一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息，把接收到的消息打印在屏幕上");

        // 接收消息回调
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            System.out.println("ReceiveLogsFanout01接收到消息：" + new String(delivery.getBody(), "UTF-8"));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
