package com.project.core.domain.adjust;

import com.project.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "walker_adjust")
public class WalkerAdjust extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_adjust_id")
  private Long walkerAdjustId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "walker_adjust_date", nullable = false)
  private LocalDate walkerAdjustDate;

  @Column(name = "walker_ttlprice", nullable = false)
  @Builder.Default
  private Long walkerTtlPrice=0L;

  @Column(name = "walker_adjust_status",nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private AdjustStatus walkerAdjustStatus=AdjustStatus.ADJUST_NOT_YET;

  @Column(name = "walker_adjust_period_start",nullable = false)
  private LocalDate walkerAdjustPeriodStart;

  @Column(name = "walker_adjust_period_end",nullable = false)
  private LocalDate walkerAdjustPeriodEnd;

  @OneToMany(mappedBy = "walkerAdjust",fetch = FetchType.LAZY)
  @Builder.Default
  private List <WalkerAdjustDetail> adjustDetailList=new ArrayList <>();

}
