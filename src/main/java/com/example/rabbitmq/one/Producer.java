package com.example.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 */
public class Producer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    /**
     * 发送消息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置工厂ip，连接RabbitMQ的队列
        factory.setHost("10.170.74.237");
        // 设置用户名
        factory.setUsername("admin");
        // 设置密码
        factory.setPassword("123456");
        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成队列
         *
         * Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
         *                                  Map<String, Object> arguments) throws IOException;
         *  queue：队列名称
         *  durable：队列里面的消息是否持久化。true表示持久化到磁盘；false表示不持久化，存储在内存中
         *  exclusive：该队列是否只供一个消费者进行消费，是否进行消息共享。true表示可以多个消费者同时消费，false表示只能一个消费者消费
         *  autoDelete：最后一个消费者端断开连接以后，该队列是否自动删除。true表示自动删除；false表示不自动删除
         *  arguments：其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发消息
        String message = "hello world";

        /**
         * 发布消息
         *
         * void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
         *  exchange：交换机名称
         *  routingKey：路由的key值
         *  props：消息属性
         *  body：消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送成功");
    }
}
