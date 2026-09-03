package com.example.rabbitmq.two;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 工作线程（消费者）
 */
public class Worker01 {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    /**
     * 接收消息
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        // 消息接收后的回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到消息：" + new String(message.getBody(), "UTF-8"));
        };

        // 消息接收被取消时的回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断");
        };

        System.out.println("C1等待接收消息...");
        // 消息的接收
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
