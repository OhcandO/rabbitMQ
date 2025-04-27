package com.koji.rabbitmq1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : WorkQueueConsumer
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@Component
@Slf4j
public class WorkQueueConsumer {
     public void workqueueTask(String msg){
         String[] msgParts = msg.split("\\|");
         String originMsg = msgParts[0];
         int duration = Integer.parseInt(msgParts[1]);

         log.info("[&&] Received: {}/ with duration: {} (ms)", originMsg, duration);

         try{
             log.warn("--Sleep for {} ms", duration);
             Thread.sleep(duration);
         }catch (InterruptedException e){
             Thread.currentThread().interrupt();
         }

         log.info("[&&] Message treats complete : {}",originMsg);
     }
}
