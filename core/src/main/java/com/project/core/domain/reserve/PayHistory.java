package com.project.core.domain.reserve;

import com.project.core.domain.BaseEntity;
import com.project.core.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.core.domain.reserve.PayStatus.PAY_DONE;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pay_history")
public class PayHistory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pay_history_id")
  private Long payId;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User customer;


  @Column(name = "pay_price")
  private int payPrice;


  @Builder.Default
  @Column(name = "pay_status",nullable = false)
  @Enumerated(EnumType.STRING)
  private PayStatus payStatus= PAY_DONE;

  @Column(name = "pay_method",nullable = false)
  private String payMethod;

  @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
  @JoinColumn(name = "walker_reserve_service_id")
  private WalkerReserveServiceInfo walkerReserveInfo;

}
