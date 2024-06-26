package com.project.core.domain.reserve;

import com.project.core.domain.BaseEntity;
import com.project.core.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.core.domain.reserve.PayStatus.PAY_DONE;
import static com.project.core.domain.reserve.PayStatus.PAY_REFUND;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pay_history")
public class PayHistory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User customer;

  @Column(name = "price")
  private int price;

  @Builder.Default
  @Column(name = "status",nullable = false)
  @Enumerated(EnumType.STRING)
  private PayStatus status = PAY_DONE;

  @Column(name = "method",nullable = false)
  private String method;

  @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
  @JoinColumn(name = "walker_reserve_id")
  private WalkerReserve reserve;

  public void refund() {
    this.status = PAY_REFUND;
  }
}
