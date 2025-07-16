package com.koji.rabbitmq1.vo.facility;

import com.koji.rabbitmq1.vo.dto.holder.SiteHolder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 */
@Data
public class SiteDTO implements SiteHolder {

    /**
     * 사이트 일련번호
     */
    @NotBlank(message = "사이트 일련번호는 필수입니다.")
    private String siteSn;

    /**
     * 특정 지자체 코드 e.g. '45180'
     */
    @NotBlank(message = "블록 코드는 필수입니다.")
    private String locgovCd;

    public SiteDTO(){}
    public SiteDTO(String siteSn, String locgovCd) {
        this.siteSn = siteSn;
        this.locgovCd = locgovCd;
    }
    public SiteDTO(SiteHolder site) {
        this.siteSn = site.getSiteSn();
        this.locgovCd = site.getLocgovCd();
    }


}
