package com.project.core.domain.user.walker;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "walker_schedule_temporary")
public class WalkerScheduleTemp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_sc_temp_id")
  private Long walkerScTempId;

  @Column(name = "walker_id",nullable = false)
  private Long walkerId;

  @Column(name = "unavailable_date",nullable = false)
  private LocalDate dateTime;

}
