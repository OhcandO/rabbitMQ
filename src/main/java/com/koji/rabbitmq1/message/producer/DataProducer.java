package com.koji.rabbitmq1.message.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koji.rabbitmq1.vo.CommandMessage;
import com.koji.rabbitmq1.vo.MyData;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.koji.rabbitmq1.service
 * fileName       : DataProducer
 * date           : 2025-06-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-14                           최초 생성
 */
@Service
@RequiredArgsConstructor
public class DataProducer {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    private final int BATCH_SIZE = 1872;

    public void sendData(List<MyData> allData){

        int total = allData.size();
        int batchCount = (int)Math.ceil((double) total /BATCH_SIZE);

        for (int i = 0; i < batchCount; i++) {

            int start = i*BATCH_SIZE;
            int end = Math.min(start + BATCH_SIZE, total);

            List<MyData> eachList = new ArrayList<>(allData.subList(start, end));
            try{
                String json = objectMapper.writeValueAsString(eachList);
                streamBridge.send("output-out-0",json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
    }

   // 2. Command 메시지 전송
   public void sendCommand(CommandMessage message) {
       streamBridge.send("commandProducer-out-0", message);
   }

   // 3. Broadcast 메시지 전송
   public void sendBroadcast(String broadcastMsg) {
       streamBridge.send("broadcastProducer-out-0", broadcastMsg);
   }

}
