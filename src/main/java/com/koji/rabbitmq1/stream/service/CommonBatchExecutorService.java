package com.koji.rabbitmq1.stream.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * DB에 대량 데이터를 입력하는 공통로직
 */
@Slf4j
public class CommonBatchExecutorService {

//    private final SqlSessionFactory batchSqlSessionFactory;

//    public CommonBatchExecutorService(SqlSessionFactory batchSqlSessionFactory) {
//        this.batchSqlSessionFactory = batchSqlSessionFactory;
//    }

    /**
     * 모든 Mapper에 공통적으로 사용할 수 있는 배치 처리 메서드
     *
     * @param mapperClass 매퍼 인터페이스 타입 (예: TagColtMapper.class)
     * @param sourceList  처리할 데이터 리스트
     * @param batchSize   한 번에 query 수행하는 단위
     * @param operation   매퍼와 데이터 항목을 받아 실제 작업을 수행하는 BiConsumer (예: TagColtMapper::insertTag)
     * @param <M>         매퍼 타입
     * @param <T>         데이터 타입
     * @return 처리 건수
     */
    public <M, T> int executeBatch(
            Class<M> mapperClass, List<T> sourceList, int batchSize, BiConsumer<M, T> operation
    ) {
        int savedCount = 0;
        try (SqlSession batchSession = batchSqlSessionFactory.openSession(ExecutorType.BATCH)) {
            M mapper = batchSession.getMapper(mapperClass);
            for (int i = 0; i < sourceList.size(); i++) {
                operation.accept(mapper, sourceList.get(i));
                if ((i + 1) % batchSize == 0) {
                    batchSession.flushStatements();
                }
                savedCount++;
            }
            batchSession.flushStatements();
            batchSession.commit();
        } catch (Exception e) {
            // 예외 로깅 및 필요 시 롤백
            log.error(e.getMessage(), e);
        }
        return savedCount;
    }
}
