package com.koji.rabbitmq1.stream.vo;

import com.koji.rabbitmq1.vo.dto.holder.SiteHolder;
import com.koji.rabbitmq1.vo.dto.holder.SiteTimeSearchable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Batch 시작을 전달하는 메시지
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class SiteTimeMessageVO extends CommonMessageVO implements SiteTimeSearchable {

    String siteSn;
    String locgovCd;
    String searchStHm;
    String searchEdHm;

    public SiteTimeMessageVO(SiteHolder origin) {
        this.siteSn = origin.getSiteSn();
        this.locgovCd = origin.getLocgovCd();
    }
}
