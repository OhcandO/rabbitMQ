package com.koji.rabbitmq1.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * packageName    : com.koji.rabbitmq1.jpa.entity
 * fileName       : MessageLog
 * date           : 2025-08-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-08-06                           최초 생성
 */
@Entity
@Table(name = "th_zz_msg_log", schema = "")
public class MessageLog {
    @Id
    @Column(name = "msg_id", columnDefinition = "uuid")
    private UUID msgId;

    @Column(name = "msg_dc")
    private String msgDc;

    @Column(name = "msg_stat")
    private String msgStat;

    @Column(name = "svc_isu_nm")
    private String svcIsuNm;

    @Column(name = "msg_isu_dt")
    private LocalDateTime msgIsuDt;

    @Column(name = "svc_recptn_nm")
    private String svcRecptnNm;

    @Column(name = "msg_recptn_dt")
    private LocalDateTime msgRecptnDt;

    @Column(name = "msg_compt_dt")
    private LocalDateTime msgComptDt;

    @Column(name = "regist_dt")
    private LocalDateTime registDt;

    @Column(name = "updt_dt")
    private LocalDateTime updtDt;

    @Column(name = "parm_site_sn")
    private Integer parmSiteSn;

    @Column(name = "parm_locgov_cd")
    private String parmLocgovCd;

    @Column(name = "parm_strt_dt")
    private LocalDateTime parmStrtDt;

    @Column(name = "parm_end_dt")
    private LocalDateTime parmEndDt;
}
