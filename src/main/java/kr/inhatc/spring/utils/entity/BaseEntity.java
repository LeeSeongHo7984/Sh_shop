package kr.inhatc.spring.utils.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity { // BaseTimeEntity 를 상속 받음

    @CreatedBy // 작성한 사람 정보
    @Column(updatable = false) // 업데이트 시 시간 수정 불가능
    private String createBy;

    @LastModifiedBy // 마지막 수정일 관리
    private String modifiedBy;
}
