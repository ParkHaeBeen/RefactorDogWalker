package com.project.core.domain.walkerservice;

import com.project.core.domain.reserve.WalkerReserve;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@Table(name = "walker_service_route")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkerServiceRoute {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id",nullable = false)
  private Long id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "walker_reserve_id")
  private WalkerReserve reserve;

  @Column(name = "route", nullable = false)
  private Geometry routes;

  @CreatedDate
  private LocalDateTime createdAt;
}
