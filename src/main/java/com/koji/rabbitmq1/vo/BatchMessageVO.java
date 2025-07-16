package com.koji.rabbitmq1.vo;

import kr.co.mo.cmservice.vo.dto.holder.SiteHolder;
import kr.co.mo.cmservice.vo.dto.searchable.SiteTimeSearchable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Batch 시작을 전달하는 메시지
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchMessageVO extends CommonMessageVO implements SiteTimeSearchable {

    String siteSn;
    String locgovCd;
    String searchStHm;
    String searchEdHm;

    public BatchMessageVO() {
    }

    public BatchMessageVO(SiteHolder origin) {
        this.siteSn = origin.getSiteSn();
        this.locgovCd = origin.getLocgovCd();
    }
}
