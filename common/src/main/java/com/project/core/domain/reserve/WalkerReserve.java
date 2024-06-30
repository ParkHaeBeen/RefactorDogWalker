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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "walker_reserve")
public class WalkerReserve extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private User customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "walker_id", nullable = false)
  private User walker;

  @Column(name = "date", nullable = false)
  private LocalDateTime date;

  @Column(name = "time_unit", nullable = false)
  private Integer timeUnit;

  @Builder.Default
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private WalkerServiceStatus status= WALKER_CHECKING;

  @Column(name = "price", nullable = false)
  private Integer price;

  public void changeStatus(final WalkerServiceStatus status) {
    this.status = status;
  }
}
