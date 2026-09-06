package com.example.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 * 消费者
 */
public class Consumer {
    // 队列的名称
    public static final String QUEUE_NAME = "hello";

    /**
     * 接收消息
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.170.74.237");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        // 声明一个接收消息的回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到消息：" + new String(message.getBody(), "UTF-8"));
        };

        // 声明一个取消消息的回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断");
        };

        /**
         * 消息者消费消息
         *
         * String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback) throws IOException;
         *  queue：消费哪个队列
         *  autoAck：消费成功之后是否要自动应答。true表示自动应答；false表示手动应答
         *  deliverCallback：消费者成功消费的回调
         *  cancelCallback：消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
