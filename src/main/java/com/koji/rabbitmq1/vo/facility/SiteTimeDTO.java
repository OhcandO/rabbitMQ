package com.koji.rabbitmq1.vo.facility;

import com.koji.rabbitmq1.vo.dto.holder.SiteHolder;
import com.koji.rabbitmq1.vo.dto.holder.SiteTimeSearchable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 유수율, 야간최소유량 조회 등 지자체 및 시간 파라미터 처리를 위한 DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SiteTimeDTO extends SiteDTO implements SiteTimeSearchable {

    /**
     * 검색 시작 시간 'yyyymmddhh24mi'
     */
    @NotBlank(message = "시작일시분은 필수 입력값 입니다")
    private String searchStHm;

    /**
     * 검색 종료 시간 'yyyymmddhh24mi'
     */
    private String searchEdHm;

    public SiteTimeDTO(){}
    public SiteTimeDTO(SiteTimeSearchable origin){
        super(origin);
        this.searchStHm = origin.getSearchStHm();
        this.searchEdHm = origin.getSearchEdHm();
    }
    public SiteTimeDTO(SiteHolder site) {
        super(site);
    }
}
