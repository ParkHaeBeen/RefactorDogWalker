package com.project.core.domain.user.walker;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Table(name = "walker_schedule_perm")
public class WalkerSchedulePerm {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id",nullable = false)
  private Long walkerId;

  @Column(name = "unavailable_day",nullable = false)
  private String dayOfWeek;

  @Column(name = "unavailable_time_start",nullable = false)
  private Integer startTime;

  @Column(name = "unavailable_time_end",nullable = false)
  private Integer endTime;
}
