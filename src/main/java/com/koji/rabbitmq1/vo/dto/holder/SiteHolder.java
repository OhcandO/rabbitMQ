package com.koji.rabbitmq1.vo.dto.holder;

/**
 * Site 및 지자체 정보 포함 명시
 */
public interface SiteHolder {
    void setSiteSn(String siteSn);
    String getSiteSn();

    void setLocgovCd(String locgovCd);
    String getLocgovCd();
}
