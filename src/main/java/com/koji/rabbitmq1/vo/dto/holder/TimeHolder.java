package com.koji.rabbitmq1.vo.dto.holder;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 시간 조건은 여러 테이블에 공통적으로 사용된다. 이 Holder 는 단독 사용이라기 보다는 searchable에 활용
 */
public interface TimeHolder {
    String getSearchStHm();

    void setSearchStHm(String searchStHm);

    String getSearchEdHm();

    void setSearchEdHm(String searchEdHm);

    /**
     * 파라미터 스트링의 시간대를 일괄 변경한다.
     *
     * @param chronoUnit MONTHS, DAYS, HOURS, MINUTES 만 구현
     */
    default void timeMode(ChronoUnit chronoUnit) {
        if (this.getSearchStHm().length() == 8) { //yyyyMMdd
            this.setSearchStHm(this.getSearchStHm() + "0000");
        } else if (this.getSearchStHm().length() == 6) {
            this.setSearchStHm(this.getSearchStHm() + "010000");
        } else if (this.getSearchStHm().length() == 10) { //yyyyMMddHH
            this.setSearchStHm(this.getSearchStHm() + "00");
        }
        if (this.getSearchEdHm() == null) {
            this.setSearchEdHm(this.getSearchStHm());
        } else if (this.getSearchEdHm().length() == 8) {
            this.setSearchEdHm(this.getSearchEdHm() + "2359");
        } else if (this.getSearchEdHm().length() == 6) { //yyyyMM
            LocalDateTime theLastMin = YearMonth.parse(this.getSearchEdHm(), DateTimeFormatter.ofPattern("yyyyMM")).atEndOfMonth().atTime(23, 59);
            this.setSearchEdHm(theLastMin.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        } else if (this.getSearchEdHm().length() == 10) { //yyyyMMddHH
            this.setSearchEdHm(this.getSearchEdHm() + "59");
        }

        DateTimeFormatter originFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startT = LocalDateTime.parse(this.getSearchStHm(), originFormat);
        LocalDateTime finalT = LocalDateTime.parse(this.getSearchEdHm(), originFormat);

        if (chronoUnit == ChronoUnit.MONTHS) {
            this.setSearchStHm(startT.format(DateTimeFormatter.ofPattern("yyyyMM")));
            this.setSearchEdHm(finalT.format(DateTimeFormatter.ofPattern("yyyyMM")));
        } else if (chronoUnit == ChronoUnit.DAYS) {
            this.setSearchStHm(startT.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            this.setSearchEdHm(finalT.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        } else if (chronoUnit == ChronoUnit.HOURS) {
            this.setSearchStHm(startT.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
            this.setSearchEdHm(finalT.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        } else if (chronoUnit == ChronoUnit.MINUTES) {
            this.setSearchStHm(startT.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
            this.setSearchEdHm(finalT.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        } else {
            throw new IllegalArgumentException("month, day, hour, minute 만 가능합니다");
        }
    }

}
