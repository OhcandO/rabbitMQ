/**
 * DTO의 개별 요소를 정의하는 인터페이스. 단독으로 쓰이기 보다는 searchable 의 인터페이스로 사용.
 * Master table 의 Primary key 를 담당하는 경우로 사용 (e.g. SiteHolder, TagHolder, FacilityHolder)<br/>
 * Detail table 및 DTO전반에 걸쳐 자주 사용되는 속성일 때 사용 (e.g. TimeHoler, TagTypeHolder)<br/>
 */
package com.koji.rabbitmq1.vo.dto.holder;