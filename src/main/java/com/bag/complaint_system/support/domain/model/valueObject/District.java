package com.bag.complaint_system.support.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum District {

  // Lima Centro
  LIMA("Lima Cercado", "Lima Centro"),
  BREÑA("Breña", "Lima Centro"),
  LA_VICTORIA("La Victoria", "Lima Centro"),
  RIMAC("Rimac", "Lima Centro"),

  // Lima Norte
  CARABAYLLO("Carabayllo", "Lima Norte"),
  COMAS("Comas", "Lima Norte"),
  INDEPENDENCIA("Independencia", "Lima Norte"),
  LOS_OLIVOS("Los Olivos", "Lima Norte"),
  PUENTE_PIEDRA("Puente Piedra", "Lima Norte"),
  SAN_MARTIN_DE_PORRES("San Martín de Porres", "Lima Norte"),

  // Lima Este
  ATE("Ate", "Lima Este"),
  CIENEGUILLA("Cieneguilla", "Lima Este"),
  EL_AGUSTINO("El Agustino", "Lima Este"),
  SAN_JUAN_DE_LURIGANCHO("San Juan de Lurigancho", "Lima Este"),
  SAN_LUIS("San Luis", "Lima Este"),
  SANTA_ANITA("Santa Anita", "Lima Este"),

  // Lima Sur
  BARRANCO("Barranco", "Lima Sur"),
  CHORRILLOS("Chorrillos", "Lima Sur"),
  PACHACAMAC("Pachacamac", "Lima Sur"),
  PUNTA_HERMOSA("Punta Hermosa", "Lima Sur"),
  PUNTA_NEGRA("Punta Negra", "Lima Sur"),
  SAN_JUAN_DE_MIRAFLORES("San Juan de Miraflores", "Lima Sur"),
  VILLA_EL_SALVADOR("Villa El Salvador", "Lima Sur"),
  VILLA_MARIA_DEL_TRIUNFO("Villa María del Triunfo", "Lima Sur"),

  // Lima Moderna
  JESUS_MARIA("Jesús María", "Lima Moderna"),
  LINCE("Lince", "Lima Moderna"),
  MAGDALENA_DEL_MAR("Magdalena del Mar", "Lima Moderna"),
  MIRAFLORES("Miraflores", "Lima Moderna"),
  PUEBLO_LIBRE("Pueblo Libre", "Lima Moderna"),
  SAN_BORJA("San Borja", "Lima Moderna"),
  SAN_ISIDRO("San Isidro", "Lima Moderna"),
  SAN_MIGUEL("San Miguel", "Lima Moderna"),
  SANTIAGO_DE_SURCO("Santiago de Surco", "Lima Moderna"),
  SURQUILLO("Surquillo", "Lima Moderna"),

  // Callao
  CALLAO("Callao", "Callao");

  private final String displayName;
  private final String zone;

  District(String displayName, String zone) {
    this.displayName = displayName;
    this.zone = zone;
  }
}
