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
  @Column(name = "id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "walker_adjust_id", nullable = false)
  private WalkerAdjust walkerAdjust;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "pay_history_id", nullable = false, unique = true)
  private PayHistory payHistory;

  @Column(name = "price",nullable = false)
  private Integer price;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private AdjustDetailStatus status= ADJUST_NOT_YET;

}
