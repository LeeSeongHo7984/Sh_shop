package kr.inhatc.spring.utils.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity { // abstract는 객체를 못만듬

    @CreatedDate // 작성된 날
    @Column(updatable = false) // 업데이트 시 시간 수정 불가능
    private LocalDateTime regTime;

    @LastModifiedDate // 마지막 수정일 관리
    private LocalDateTime updateTime;
}
