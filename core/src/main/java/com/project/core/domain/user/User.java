package com.project.core.domain.user;

import com.project.core.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users",
      uniqueConstraints = {@UniqueConstraint(name = "users_email_unique",columnNames = {"user_email"})})
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "email",nullable = false)
  private String email;

  @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
  @Column(name = "phone_number",nullable = false)
  private String phoneNumber;

  @Column(name = "lat",nullable = false)
  private Double lat;

  @Column(name = "lnt",nullable = false)
  private Double lnt;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "role",nullable = false)
  private Role role=Role.USER;

  @Column(name = "name",nullable = false)
  private String name;

}
