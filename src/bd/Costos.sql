CREATE DATABASE IF NOT EXISTS bd_costos;
USE bd_costos;

CREATE TABLE productos (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(30) NOT NULL,
  costo_total DOUBLE NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE tipo_costo (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE unidades_medida (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(30) NOT NULL,
  simbolo VARCHAR(10) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE insumos (
  id INT NOT NULL AUTO_INCREMENT,
  tipo SET('materia_prima','receta') NOT NULL,
  nombre VARCHAR(30) NOT NULL,
  unidad_medida INT NOT NULL,
  cantidad DOUBLE NOT NULL,
  costo DOUBLE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_insumos_unidad FOREIGN KEY (unidad_medida)
    REFERENCES unidades_medida(id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE costo_detalle (
  id INT NOT NULL AUTO_INCREMENT,
  id_producto INT NOT NULL,
  id_tipo_costo INT NOT NULL,
  monto DOUBLE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_costo_producto FOREIGN KEY (id_producto)
    REFERENCES productos(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_costo_tipo FOREIGN KEY (id_tipo_costo)
    REFERENCES tipo_costo(id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE detalle_receta (
  id_detalle INT NOT NULL AUTO_INCREMENT,
  id_receta INT NOT NULL,
  id_insumo INT NOT NULL,
  cantidad DOUBLE NOT NULL,
  id_unidad_medida INT NOT NULL,
  PRIMARY KEY (id_detalle),
  CONSTRAINT fk_detalle_insumo FOREIGN KEY (id_insumo)
    REFERENCES insumos(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_detalle_unidad FOREIGN KEY (id_unidad_medida)
    REFERENCES unidades_medida(id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE insumos_de_productos (
  id INT NOT NULL AUTO_INCREMENT,
  id_producto INT NOT NULL,
  id_insumo INT NOT NULL,
  cantidad DOUBLE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_insumo_producto FOREIGN KEY (id_producto)
    REFERENCES productos(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_insumo_insumo FOREIGN KEY (id_insumo)
    REFERENCES insumos(id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;
