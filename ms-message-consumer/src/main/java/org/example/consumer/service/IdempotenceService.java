package org.example.consumer.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.enums.RabbitConsumeStatusEnum;
import org.example.commons.enums.RedisKeyEnum;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 幂等性消费
 *
 */
@Slf4j
@Service
public class IdempotenceService implements ChannelAwareMessageListener {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 记录消费次数
     */
    private int n = 0;

    @Override
    @RabbitListener(queues = "direct.queue", ackMode = "MANUAL")
    public void onMessage(Message message, Channel channel) throws Exception {
        JSONObject entries = JSONUtil.parseObj(new String(message.getBody()));
        String redisKey = RedisKeyEnum.MQ_STATUS.getKey() + entries.get("id");
        Integer status = redisTemplate.opsForValue().get(redisKey);
        try {
            //只有待消费和消费失败的能进行消费
            if (RabbitConsumeStatusEnum.getNeedExecuteList().contains(status)) {
                //记录开始消费
                redisTemplate.opsForValue().set(redisKey, RabbitConsumeStatusEnum.BEGIN.getCode());
                // 处理消息逻辑
                processMessage(entries);
                System.out.println("执行成功了：" + entries.get("id"));
                //记录消费成功
                redisTemplate.opsForValue().set(redisKey, RabbitConsumeStatusEnum.SUCCESS.getCode());
                // 成功处理后手动确认消息
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception e) {
            // 处理失败，可以选择重新入队列（取决于业务需求）
            if (shouldRequeueOnFailure()) {
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                channel.basicNack(deliveryTag, false, true);
                System.out.println("执行失败了：" + entries.get("id"));
                //记录消费失败
                redisTemplate.opsForValue().set(redisKey, RabbitConsumeStatusEnum.FAIL.getCode());
            } else {
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                channel.basicReject(deliveryTag, false);
            }
        }
    }

    /**
     * 根据业务需求决定是否重新入队列
     * @return
     */
    private boolean shouldRequeueOnFailure() {
        return true;
    }

    /**
     * 消费逻辑
     *
     * @param entries
     * @throws Exception
     */
    private void processMessage(JSONObject entries) throws Exception {
        n++;
        //模拟MQ消费时长
        Thread.sleep(4000);
        //消费
        System.out.println("Processing id: " + RedisKeyEnum.MQ_STATUS.getKey() + entries.get("id"));
        System.out.println("Processing message: " + entries.get("message"));
        System.out.println("第" + n + "次消费");
    }

}
