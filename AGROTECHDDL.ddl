-- Generado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   en:        2025-04-27 13:54:21 CLT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE CLIENTE 
    ( 
     rut       NUMBER  NOT NULL , 
     ncliente  NUMBER  NOT NULL , 
     nombre    VARCHAR2 (20)  NOT NULL , 
     appaterno VARCHAR2 (20)  NOT NULL , 
     apmaterno VARCHAR2 (20) , 
     correo    VARCHAR2 (70)  NOT NULL , 
     telefono  VARCHAR2 (15)  NOT NULL , 
     direccion VARCHAR2 (60)  NOT NULL 
    ) 
;

ALTER TABLE CLIENTE 
    ADD CONSTRAINT CLIENTE_PK PRIMARY KEY ( rut ) ;

CREATE TABLE DETALLE_PEDIDO 
    ( 
     id           NUMBER  NOT NULL , 
     fecha_inicio DATE , 
     fecha_fin    DATE , 
     estado       VARCHAR2 (15)  NOT NULL , 
     PEDIDO_id    NUMBER  NOT NULL , 
     PEDIDO_rut   VARCHAR2 (9)  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX DETALLE_PEDIDO__IDX ON DETALLE_PEDIDO 
    ( 
     PEDIDO_id ASC , 
     PEDIDO_rut ASC 
    ) 
;

ALTER TABLE DETALLE_PEDIDO 
    ADD CONSTRAINT DETALLE_PEDIDO_PK PRIMARY KEY ( id ) ;

CREATE TABLE EMPLEADO 
    ( 
     rut       VARCHAR2 (9)  NOT NULL , 
     nempleado NUMBER  NOT NULL , 
     nombre    VARCHAR2 (40)  NOT NULL , 
     appaterno VARCHAR2 (20)  NOT NULL , 
     apmaterno VARCHAR2 (20) , 
     telefono  VARCHAR2 (15)  NOT NULL , 
     direccion VARCHAR2 (60)  NOT NULL 
    ) 
;

ALTER TABLE EMPLEADO 
    ADD CONSTRAINT EMPLEADO_PK PRIMARY KEY ( rut ) ;

CREATE TABLE EQUIPO 
    ( 
     id                    NUMBER  NOT NULL , 
     nombre                VARCHAR2 (30)  NOT NULL , 
     estado                VARCHAR2 (10)  NOT NULL , 
     precio_venta          NUMBER , 
     precio_arriendo       NUMBER , 
     PEDIDO_id             NUMBER , 
     PEDIDO_EMPLEADO_rut   VARCHAR2 (9) , 
     PROVEEDOR_rut_empresa VARCHAR2 (20)  NOT NULL 
    ) 
;

ALTER TABLE EQUIPO 
    ADD CONSTRAINT EQUIPO_PK PRIMARY KEY ( id, PROVEEDOR_rut_empresa ) ;

CREATE TABLE INCIDENCIA 
    ( 
     id                 NUMBER  NOT NULL , 
     descripcion        VARCHAR2 (50)  NOT NULL , 
     fecha_reporte      DATE  NOT NULL , 
     EQUIPO_id          NUMBER  NOT NULL , 
     EQUIPO_rut_empresa VARCHAR2 (20)  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX INCIDENCIA__IDX ON INCIDENCIA 
    ( 
     EQUIPO_id ASC , 
     EQUIPO_rut_empresa ASC 
    ) 
;

ALTER TABLE INCIDENCIA 
    ADD CONSTRAINT INCIDENCIA_PK PRIMARY KEY ( id ) ;

CREATE TABLE MANTENIMIENTO 
    ( 
     id                    NUMBER  NOT NULL , 
     fecha_inicio          DATE  NOT NULL , 
     fecha_termino         DATE , 
     estado                VARCHAR2 (15)  NOT NULL , 
     TIPO_MANTENIMIENTO_id NUMBER  NOT NULL , 
     INCIDENCIA_id         NUMBER  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX MANTENIMIENTO__IDX ON MANTENIMIENTO 
    ( 
     INCIDENCIA_id ASC 
    ) 
;

ALTER TABLE MANTENIMIENTO 
    ADD CONSTRAINT MANTENIMIENTO_PK PRIMARY KEY ( id, TIPO_MANTENIMIENTO_id ) ;

CREATE TABLE PEDIDO 
    ( 
     id                NUMBER  NOT NULL , 
     fecha_solicitud   DATE  NOT NULL , 
     DETALLE_PEDIDO_id NUMBER  NOT NULL , 
     CLIENTE_rut       NUMBER  NOT NULL , 
     EMPLEADO_rut      VARCHAR2 (9)  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX PEDIDO__IDX ON PEDIDO 
    ( 
     DETALLE_PEDIDO_id ASC 
    ) 
;

ALTER TABLE PEDIDO 
    ADD CONSTRAINT PEDIDO_PK PRIMARY KEY ( id, EMPLEADO_rut ) ;

CREATE TABLE PROVEEDOR 
    ( 
     rut_empresa VARCHAR2 (20)  NOT NULL , 
     nombre      VARCHAR2 (30)  NOT NULL , 
     telefono    VARCHAR2 (15)  NOT NULL , 
     correo      VARCHAR2 (80)  NOT NULL 
    ) 
;

ALTER TABLE PROVEEDOR 
    ADD CONSTRAINT PROVEEDOR_PK PRIMARY KEY ( rut_empresa ) ;

CREATE TABLE RESENA 
    ( 
     id         NUMBER  NOT NULL , 
     puntuacion NUMBER  NOT NULL , 
     comentario VARCHAR2 (150) , 
     PEDIDO_id  NUMBER  NOT NULL , 
     PEDIDO_rut VARCHAR2 (9)  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX RESENA__IDX ON RESENA 
    ( 
     PEDIDO_id ASC , 
     PEDIDO_rut ASC 
    ) 
;

ALTER TABLE RESENA 
    ADD CONSTRAINT RESENA_PK PRIMARY KEY ( id ) ;

CREATE TABLE TIPO_MANTENIMIENTO 
    ( 
     id          NUMBER  NOT NULL , 
     descripcion VARCHAR2 (30)  NOT NULL 
    ) 
;

ALTER TABLE TIPO_MANTENIMIENTO 
    ADD CONSTRAINT TIPO_MANTENIMIENTO_PK PRIMARY KEY ( id ) ;

ALTER TABLE DETALLE_PEDIDO 
    ADD CONSTRAINT DETALLE_PEDIDO_PEDIDO_FK FOREIGN KEY 
    ( 
     PEDIDO_id,
     PEDIDO_rut
    ) 
    REFERENCES PEDIDO 
    ( 
     id,
     EMPLEADO_rut
    ) 
;

ALTER TABLE EQUIPO 
    ADD CONSTRAINT EQUIPO_PEDIDO_FK FOREIGN KEY 
    ( 
     PEDIDO_id,
     PEDIDO_EMPLEADO_rut
    ) 
    REFERENCES PEDIDO 
    ( 
     id,
     EMPLEADO_rut
    ) 
;

ALTER TABLE EQUIPO 
    ADD CONSTRAINT EQUIPO_PROVEEDOR_FK FOREIGN KEY 
    ( 
     PROVEEDOR_rut_empresa
    ) 
    REFERENCES PROVEEDOR 
    ( 
     rut_empresa
    ) 
;

ALTER TABLE INCIDENCIA 
    ADD CONSTRAINT INCIDENCIA_EQUIPO_FK FOREIGN KEY 
    ( 
     EQUIPO_id,
     EQUIPO_rut_empresa
    ) 
    REFERENCES EQUIPO 
    ( 
     id,
     PROVEEDOR_rut_empresa
    ) 
;

ALTER TABLE MANTENIMIENTO 
    ADD CONSTRAINT MANTENIMIENTO_INCIDENCIA_FK FOREIGN KEY 
    ( 
     INCIDENCIA_id
    ) 
    REFERENCES INCIDENCIA 
    ( 
     id
    ) 
;

ALTER TABLE PEDIDO 
    ADD CONSTRAINT PEDIDO_CLIENTE_FK FOREIGN KEY 
    ( 
     CLIENTE_rut
    ) 
    REFERENCES CLIENTE 
    ( 
     rut
    ) 
;

ALTER TABLE PEDIDO 
    ADD CONSTRAINT PEDIDO_DETALLE_PEDIDO_FK FOREIGN KEY 
    ( 
     DETALLE_PEDIDO_id
    ) 
    REFERENCES DETALLE_PEDIDO 
    ( 
     id
    ) 
;

ALTER TABLE PEDIDO 
    ADD CONSTRAINT PEDIDO_EMPLEADO_FK FOREIGN KEY 
    ( 
     EMPLEADO_rut
    ) 
    REFERENCES EMPLEADO 
    ( 
     rut
    ) 
;

ALTER TABLE RESENA 
    ADD CONSTRAINT RESENA_PEDIDO_FK FOREIGN KEY 
    ( 
     PEDIDO_id,
     PEDIDO_rut
    ) 
    REFERENCES PEDIDO 
    ( 
     id,
     EMPLEADO_rut
    ) 
;

ALTER TABLE MANTENIMIENTO 
    ADD CONSTRAINT TIPO_MANTENIMIENTO_FK FOREIGN KEY 
    ( 
     TIPO_MANTENIMIENTO_id
    ) 
    REFERENCES TIPO_MANTENIMIENTO 
    ( 
     id
    ) 
;



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            10
-- CREATE INDEX                             5
-- ALTER TABLE                             20
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
