package com.project.core.domain.user.walker;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "walker_schedule_temp")
public class WalkerScheduleTemp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id",nullable = false)
  private Long walkerId;

  @Column(name = "unavailable_at",nullable = false)
  private LocalDate unAvailAt;
}
