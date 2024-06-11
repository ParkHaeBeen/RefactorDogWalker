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
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "price", nullable = false)
  @Builder.Default
  private Long price=0L;

  @Column(name = "status",nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private AdjustStatus status=AdjustStatus.ADJUST_NOT_YET;

  @Column(name = "period_start",nullable = false)
  private LocalDate start;

  @Column(name = "period_end",nullable = false)
  private LocalDate end;

  @OneToMany(mappedBy = "walkerAdjust",fetch = FetchType.LAZY)
  @Builder.Default
  private List <WalkerAdjustDetail> adjustDetailList=new ArrayList <>();
}
