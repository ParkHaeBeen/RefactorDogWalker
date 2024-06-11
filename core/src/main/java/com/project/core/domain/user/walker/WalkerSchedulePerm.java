package com.project.core.domain.user.walker;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "walker_schedule_perm")
public class WalkerSchedulePerm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_sc_id")
  private Long walkerScId;

  @Column(name = "walker_id",nullable = false)
  private Long walkerId;

  @Column(name = "unavailable_day",nullable = false)
  private String dayOfWeek;

  @Column(name = "unavailable_time_start",nullable = false)
  private Integer startTime;

  @Column(name = "unavailable_time_end",nullable = false)
  private Integer endTime;
}
