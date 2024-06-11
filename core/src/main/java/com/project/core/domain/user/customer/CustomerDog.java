package com.project.core.domain.user.customer;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "customer_dog")
public class CustomerDog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id",nullable = false)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "img_url",nullable = false)
  private String imgUrl;

  @Column(name = "birth_date",nullable = false)
  private LocalDateTime birth;

  @Column(name = "name",nullable = false)
  private String name;

  @Column(name = "type",nullable = false)
  private String type;

  @Column(name = "description",nullable = false)
  private String description;


}
