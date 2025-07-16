package com.koji.rabbitmq1.vo.util;



import com.koji.rabbitmq1.vo.dto.holder.TimeHolder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DTO 와 직접 연관있는 interface 를 활용한 util 성 메서드 모음
 */
public class TimeUtil {

    public static final DateTimeFormatter yyyymmddhh24mi = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter yyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter hh24mi = DateTimeFormatter.ofPattern("HHmm");

    /**
     * 주어진 시작시간과 끝 시간에 대해, 각 시간 간격이 unitElementCount 가 되는 검색시간표 반환
     * @param param            시간범위 산정위한 파라미터 DTO
     * @param timeUnit 시간 간격의 단위. minutes, hours, days, months 만 지원                         
     * @param unitElementCount 개별 시간 간격의 개수
     * @return [시작_일시분, 끝_일시분] 배열의 리스트
     */
    public static List<String[]> getStartFinalTimeStr(TimeHolder param, ChronoUnit timeUnit, double unitElementCount) {
        List<String[]> returnList = new ArrayList<>();
        param.timeMode(ChronoUnit.MINUTES);
        LocalDateTime startTime = LocalDateTime.parse(param.getSearchStHm(), yyyymmddhh24mi);
        LocalDateTime finalTime = LocalDateTime.parse(param.getSearchEdHm(), yyyymmddhh24mi);

        if (startTime.isAfter(finalTime)) throw new IllegalArgumentException("끝 시간이 처음 시작시간보다 빠릅니다");

        if (startTime.isEqual(finalTime)) {
            String[] aa = new String[]{startTime.format(yyyymmddhh24mi), startTime.format(yyyymmddhh24mi)};
            returnList.add(aa);
        } else {
            long totalCount  = Duration.between(startTime, finalTime).getSeconds();
            if (timeUnit.equals(ChronoUnit.MINUTES)) {
                totalCount /= 60;
            } else if (timeUnit.equals(ChronoUnit.HOURS)){
                totalCount /= 60 * 60;
            } else if (timeUnit.equals(ChronoUnit.DAYS)){
                totalCount /= 60 * 60 * 24;
            } else if (timeUnit.equals(ChronoUnit.MONTHS)){
                totalCount /= 60 * 60 * 24 * 30; //대략.
            } else{
                throw new IllegalArgumentException("minute, hour, days, months 만 가능");
            }

            int chunksCount = (int) Math.ceil(totalCount / unitElementCount);

            for (int i = 0; i < chunksCount; i++) {

                LocalDateTime tempStart ;
                LocalDateTime tempEnd;

                if (timeUnit.equals(ChronoUnit.MINUTES)) {
                    tempStart = startTime.plusMinutes(i * (long) unitElementCount);
                    tempEnd = tempStart.plusMinutes((int) unitElementCount);
                } else if (timeUnit.equals(ChronoUnit.HOURS)){
                    tempStart = startTime.plusHours(i * (long) unitElementCount);
                    tempEnd = tempStart.plusHours((int) unitElementCount);
                } else if (timeUnit.equals(ChronoUnit.DAYS)){
                    tempStart = startTime.plusDays(i * (long) unitElementCount);
                    tempEnd = tempStart.plusDays((int) unitElementCount);
                } else {
                    tempStart = startTime.plusMonths(i * (long) unitElementCount);
                    tempEnd = tempStart.plusMonths((int) unitElementCount);
                }

                if (tempEnd.isAfter(finalTime)) tempEnd = finalTime;
                returnList.add(new String[]{
                        tempStart.format(yyyymmddhh24mi),
                        tempEnd.format(yyyymmddhh24mi)
                });
            }
        }

        return returnList;
    }

    /**
     * 시간 범위 내 정각 (00분) 리스트만 반환
     */
    public static List<String[]> getEveryOClock(TimeHolder param){
        List<String[]> returnList = new ArrayList<>();
        param.timeMode(ChronoUnit.MINUTES);
        LocalDateTime startTime = LocalDateTime.parse(param.getSearchStHm(), yyyymmddhh24mi);
        LocalDateTime finalTime = LocalDateTime.parse(param.getSearchEdHm(), yyyymmddhh24mi);

        if (startTime.isAfter(finalTime)) throw new IllegalArgumentException("끝 시간이 처음 시작시간보다 빠릅니다");

        for (LocalDateTime tempTime = startTime;
             tempTime.isBefore(finalTime) || tempTime.isEqual(finalTime);
             tempTime = tempTime.plusHours(1)
        ){
            LocalDateTime tempDateTime = tempTime.withMinute(0).withSecond(0).withNano(0);
            String[] aa = new String[]{tempDateTime.format(yyyymmddhh24mi), tempDateTime.format(yyyymmddhh24mi)};
            returnList.add(aa);
        }

        return returnList;
    }

    /**
     *
     * @param param 기준날짜 찾기위한 파라미터
     * @param partialHMGroup [ ["0900","1359"], ["1400","1859"] ] 등
     * @return [ ["202512250000","202512250859"], ["~1900","~2359"] ] 꼴
     */
    public static List<Map<String,String>> getTheLastGroupOfDay(String param, List<Map<String,String>> partialHMGroup){
        LocalDate baseDate = LocalDate.parse(param.substring(0,8), yyyymmdd);
        LocalTime st = LocalTime.MIN;
        LocalTime ed = LocalTime.MAX.withSecond(0).withNano(0); //시 분 까지

        List<Map<String,LocalTime>> sorted = partialHMGroup.stream()
                .map(tempMap -> {
                    Map<String, LocalTime> temp = new LinkedHashMap<>();
                    if (tempMap.get("from").length()==4){
                        temp.put("from", LocalTime.parse(tempMap.get("from"), hh24mi));
                        temp.put("to", LocalTime.parse(tempMap.get("to"), hh24mi));
                    } else if (tempMap.get("from").length()==12){
                        temp.put("from", LocalTime.parse(tempMap.get("from").substring(8), hh24mi));
                        temp.put("to", LocalTime.parse(tempMap.get("to").substring(8), hh24mi));
                    } else /*if (str.length==2)*/ {
                        temp.put("from", LocalTime.of(Integer.parseInt(tempMap.get("from")),0)) ;
                        temp.put("to", LocalTime.of(Integer.parseInt(tempMap.get("to")),0)) ;
                    }
                    return temp;})
                .sorted(Comparator.comparing(lt -> lt.get("from")))
                .collect(Collectors.toList());

        LocalTime partStart = sorted.get(0).get("from");
        LocalTime partEnd = sorted.get(sorted.size()-1).get("to");

        List<Map<String,String>> returnList = new ArrayList<>();

        if (st.isBefore(partStart)){
            LocalDateTime temp1 = baseDate.atTime(st);
            LocalDateTime temp2 = baseDate.atTime(partStart);

            returnList.add(new HashMap<String,String>(){{
                put("from",temp1.format(yyyymmddhh24mi));
                put("to", temp2.format(yyyymmddhh24mi));
            }});
        }

        if (ed.isAfter(partEnd)){
            LocalDateTime temp1 = baseDate.atTime(partEnd);
            LocalDateTime temp2 = baseDate.atTime(ed);
            returnList.add(new HashMap<String,String>(){{
                put("from",temp1.format(yyyymmddhh24mi));
                put("to", temp2.format(yyyymmddhh24mi));
            }});
        }

        return returnList;
    }

}
