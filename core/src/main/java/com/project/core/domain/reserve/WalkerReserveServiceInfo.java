package com.project.core.domain.reserve;

import com.project.core.domain.BaseEntity;
import com.project.core.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import static com.project.core.domain.reserve.WalkerServiceStatus.WALKER_CHECKING;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "walker_reserve_service",uniqueConstraints={
    @UniqueConstraint(
        name="unique_walker_datetime",
        columnNames={"walker_id", "walker_service_date"}
    )
})
public class WalkerReserveServiceInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "walker_reserve_service_id")
  private Long reserveId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id",nullable = false)
  private User customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "walker_id",nullable = false)
  private User walker;

  @Column(name = "walker_service_date",nullable = false)
  private LocalDateTime serviceDateTime;

  @Column(name = "walker_service_time_unit",nullable = false)
  private Integer timeUnit;

  @Builder.Default
  @Column(name = "walker_service_status",nullable = false)
  @Enumerated(EnumType.STRING)
  private WalkerServiceStatus status= WALKER_CHECKING;

  @Column(name = "walker_reserve_service_price")
  private Integer servicePrice;

  public void modifyStatus(final WalkerServiceStatus walkerServiceStatus){
    this.status=walkerServiceStatus;
  }

}
