-- cmn.th_zz_msg_log definition

-- Drop table

-- DROP TABLE cmn.th_zz_msg_log;

CREATE TABLE cmn.th_zz_msg_log (
	msg_id uuid NOT NULL, -- 메시지_ID
	msg_dc text NULL, -- 메시지_설명
	msg_stat varchar NULL, -- 메시지_상태(CG013)
	svc_isu_nm varchar NULL, -- 서비스_발행_명
	msg_isu_dt timestamp NULL, -- 메시지_발행_일시
	svc_recptn_nm varchar NULL, -- 서비스_수신_명
	msg_recptn_dt timestamp NULL, -- 메시지_수신_일시
	msg_compt_dt timestamp NULL, -- 메시지_완료_일시
	regist_dt timestamp NULL, -- 등록_일시
	updt_dt timestamp NULL, -- 수정_일시
	parm_site_sn int4 NULL, -- 파라미터_사이트_일련번호
	parm_locgov_cd varchar NULL, -- 파라미터_지자체_코드
	parm_strt_dt timestamp NULL, -- 파라미터_시작_일시
	parm_end_dt timestamp NULL, -- 파라미터_종료_일시
	CONSTRAINT th_zz_msg_log_pkey PRIMARY KEY (msg_id)
);
COMMENT ON TABLE cmn.th_zz_msg_log IS 'BATCH 관련 MQ로그';

-- Column comments

COMMENT ON COLUMN cmn.th_zz_msg_log.msg_id IS '메시지_ID';
COMMENT ON COLUMN cmn.th_zz_msg_log.msg_dc IS '메시지_설명';
COMMENT ON COLUMN cmn.th_zz_msg_log.msg_stat IS '메시지_상태(CG013)';
COMMENT ON COLUMN cmn.th_zz_msg_log.svc_isu_nm IS '서비스_발행_명';
COMMENT ON COLUMN cmn.th_zz_msg_log.msg_isu_dt IS '메시지_발행_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.svc_recptn_nm IS '서비스_수신_명';
COMMENT ON COLUMN cmn.th_zz_msg_log.msg_recptn_dt IS '메시지_수신_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.msg_compt_dt IS '메시지_완료_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.regist_dt IS '등록_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.updt_dt IS '수정_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.parm_site_sn IS '파라미터_사이트_일련번호';
COMMENT ON COLUMN cmn.th_zz_msg_log.parm_locgov_cd IS '파라미터_지자체_코드';
COMMENT ON COLUMN cmn.th_zz_msg_log.parm_strt_dt IS '파라미터_시작_일시';
COMMENT ON COLUMN cmn.th_zz_msg_log.parm_end_dt IS '파라미터_종료_일시';