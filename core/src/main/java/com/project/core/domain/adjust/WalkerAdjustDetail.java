package com.project.core.domain.adjust;

import com.project.core.domain.reserve.PayHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.core.domain.adjust.AdjustDetailStatus.ADJUST_NOT_YET;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "walker_adjust_detail")
public class WalkerAdjustDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_adjust_detail_id")
  private Long walkerAdjustDetailId;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "walker_adjust_id", nullable = false)
  private WalkerAdjust walkerAdjust;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "pay_history_id", nullable = false)
  private PayHistory payHistory;

  @Column(name = "walker_adjust_price",nullable = false)
  private Integer walkerAdjustPrice;

  @Column(name = "walker_adjust_status", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private AdjustDetailStatus adjustDetailStatus= ADJUST_NOT_YET;

}
