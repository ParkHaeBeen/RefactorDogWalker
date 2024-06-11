package com.project.core.domain.walkerservice;

import com.project.core.domain.reserve.WalkerReserveServiceInfo;
import jakarta.persistence.*;
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
@NoArgsConstructor
public class WalkerServiceRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_service_route_id",nullable = false)
  private Long routeId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "walker_reserve_service_id")
  private WalkerReserveServiceInfo reserveInfo;

  @Column(name = "walker_route", nullable = false)
  private Geometry routes;

  @CreatedDate
  private LocalDateTime createdAt;
}
