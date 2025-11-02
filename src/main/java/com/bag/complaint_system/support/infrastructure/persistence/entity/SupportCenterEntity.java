package com.bag.complaint_system.support.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "support_centers")
public class SupportCenterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 200)
  private String name;

  @Column(name = "street", nullable = false, length = 300)
  private String street;

  @Enumerated(EnumType.STRING)
  @Column(name = "district", nullable = false, length = 50)
  private DistrictEntity district;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "email", nullable = false, length = 150)
  private String email;

  @Column(name = "schedule", nullable = false, length = 200)
  private String schedule;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public enum DistrictEntity {
    // Lima Centro
    LIMA,
    BREÑA,
    LA_VICTORIA,
    RIMAC,

    // Lima Norte
    CARABAYLLO,
    COMAS,
    INDEPENDENCIA,
    LOS_OLIVOS,
    PUENTE_PIEDRA,
    SAN_MARTIN_DE_PORRES,

    // Lima Este
    ATE,
    CIENEGUILLA,
    EL_AGUSTINO,
    SAN_JUAN_DE_LURIGANCHO,
    SAN_LUIS,
    SANTA_ANITA,

    // Lima Sur
    BARRANCO,
    CHORRILLOS,
    PACHACAMAC,
    PUNTA_HERMOSA,
    PUNTA_NEGRA,
    SAN_JUAN_DE_MIRAFLORES,
    VILLA_EL_SALVADOR,
    VILLA_MARIA_DEL_TRIUNFO,

    // Lima Moderna
    JESUS_MARIA,
    LINCE,
    MAGDALENA_DEL_MAR,
    MIRAFLORES,
    PUEBLO_LIBRE,
    SAN_BORJA,
    SAN_ISIDRO,
    SAN_MIGUEL,
    SANTIAGO_DE_SURCO,
    SURQUILLO,

    // Callao
    CALLAO
  }
}
