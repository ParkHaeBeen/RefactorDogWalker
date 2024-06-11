package com.project.core.domain.user;

import com.project.core.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "users",
      uniqueConstraints = {@UniqueConstraint(name = "users_email_unique",columnNames = {"user_email"})})
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_email",nullable = false)
  private String userEmail;

  @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
  @Column(name = "user_phone_number",nullable = false)
  private String userPhoneNumber;

  @Column(name = "user_lat",nullable = false)
  private Double userLat;

  @Column(name = "user_lnt",nullable = false)
  private Double userLnt;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "user_role",nullable = false)
  private Role userRole=Role.USER;

  @Column(name = "user_name",nullable = false)
  private String userName;

  public void modifyUserRole(final Role role){
    this.userRole = role;
  }
}
