-- MariaDB dump 10.17  Distrib 10.4.8-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: Hospital
-- ------------------------------------------------------
-- Server version	10.4.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Area`
--

DROP TABLE IF EXISTS `Area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Area` (
  `id_area` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_modulo` int(11) NOT NULL,
  PRIMARY KEY (`id_area`),
  KEY `fk_Area_Modulo1_idx` (`id_modulo`),
  CONSTRAINT `fk_Area_Modulo1` FOREIGN KEY (`id_modulo`) REFERENCES `Modulo` (`id_modulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Area`
--

LOCK TABLES `Area` WRITE;
/*!40000 ALTER TABLE `Area` DISABLE KEYS */;
INSERT INTO `Area` VALUES (1,'administracion',1),(2,'Recursos Humanos',2),(3,'farmacia',3),(4,'Medicos',2),(5,'Enfermeros',2);
/*!40000 ALTER TABLE `Area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cirugia`
--

DROP TABLE IF EXISTS `Cirugia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cirugia` (
  `id_cirugia` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `realizada` tinyint(1) DEFAULT 0,
  `id_tarifa` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  PRIMARY KEY (`id_cirugia`,`id_paciente`),
  KEY `fk_Cirugia_TarifasDeCirugias1_idx` (`id_tarifa`),
  KEY `fk_Cirugia_Paciente1_idx` (`id_paciente`),
  CONSTRAINT `fk_Cirugia_Paciente1` FOREIGN KEY (`id_paciente`) REFERENCES `Paciente` (`id_paciente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cirugia_TarifasDeCirugias1` FOREIGN KEY (`id_tarifa`) REFERENCES `Cirugias_Disponibles` (`id_tarfia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cirugia`
--

LOCK TABLES `Cirugia` WRITE;
/*!40000 ALTER TABLE `Cirugia` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cirugia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cirugia_tiene_Empleado`
--

DROP TABLE IF EXISTS `Cirugia_tiene_Empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cirugia_tiene_Empleado` (
  `id_cirugia` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `cui_persona` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_cirugia`),
  KEY `INDEX_EMPLEADO` (`id_empleado`,`cui_persona`),
  KEY `INDEX_CIRUGIA` (`id_cirugia`,`id_paciente`),
  CONSTRAINT `FK_CIRUGIA` FOREIGN KEY (`id_cirugia`, `id_paciente`) REFERENCES `Cirugia` (`id_cirugia`, `id_paciente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EMPLEADO` FOREIGN KEY (`id_empleado`, `cui_persona`) REFERENCES `Empleado` (`id_empleado`, `cui_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cirugia_tiene_Empleado`
--

LOCK TABLES `Cirugia_tiene_Empleado` WRITE;
/*!40000 ALTER TABLE `Cirugia_tiene_Empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cirugia_tiene_Empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cirugia_tiene_Medico_Especialista`
--

DROP TABLE IF EXISTS `Cirugia_tiene_Medico_Especialista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cirugia_tiene_Medico_Especialista` (
  `Cirugia_id_cirugia` int(11) NOT NULL,
  `MedicoEspecialista_id_medico_especialista` int(11) NOT NULL,
  `MedicoEspecialista_cui_persona` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`Cirugia_id_cirugia`,`MedicoEspecialista_id_medico_especialista`,`MedicoEspecialista_cui_persona`),
  KEY `fk_Cirugia_has_MedicoEspecialista_MedicoEspecialista1_idx` (`MedicoEspecialista_id_medico_especialista`,`MedicoEspecialista_cui_persona`),
  KEY `fk_Cirugia_has_MedicoEspecialista_Cirugia1_idx` (`Cirugia_id_cirugia`),
  CONSTRAINT `fk_Cirugia_has_MedicoEspecialista_Cirugia1` FOREIGN KEY (`Cirugia_id_cirugia`) REFERENCES `Cirugia` (`id_cirugia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cirugia_has_MedicoEspecialista_MedicoEspecialista1` FOREIGN KEY (`MedicoEspecialista_id_medico_especialista`, `MedicoEspecialista_cui_persona`) REFERENCES `Medico_Especialista` (`id_medico_especialista`, `cui_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cirugia_tiene_Medico_Especialista`
--

LOCK TABLES `Cirugia_tiene_Medico_Especialista` WRITE;
/*!40000 ALTER TABLE `Cirugia_tiene_Medico_Especialista` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cirugia_tiene_Medico_Especialista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cirugias_Disponibles`
--

DROP TABLE IF EXISTS `Cirugias_Disponibles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cirugias_Disponibles` (
  `id_tarfia` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `costo_al_hospital` double NOT NULL,
  `tarifa_de_especialista` double NOT NULL DEFAULT 0,
  `precio_al_cliente` double NOT NULL,
  PRIMARY KEY (`id_tarfia`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cirugias_Disponibles`
--

LOCK TABLES `Cirugias_Disponibles` WRITE;
/*!40000 ALTER TABLE `Cirugias_Disponibles` DISABLE KEYS */;
INSERT INTO `Cirugias_Disponibles` VALUES (1,'Operacion de riñon',3000,4000,10000),(2,'Cirugia de riñon',2500,0,5000),(3,'Cirugia de corazon',3000,3000,10000);
/*!40000 ALTER TABLE `Cirugias_Disponibles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Constantes`
--

DROP TABLE IF EXISTS `Constantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Constantes` (
  `id_constante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `monto` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_constante`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Aca se puede agregar la cantidad de dias de vacaciones de empleado\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Constantes`
--

LOCK TABLES `Constantes` WRITE;
/*!40000 ALTER TABLE `Constantes` DISABLE KEYS */;
INSERT INTO `Constantes` VALUES (1,'consulta general',100);
/*!40000 ALTER TABLE `Constantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consulta`
--

DROP TABLE IF EXISTS `Consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Consulta` (
  `id_consulta` int(11) NOT NULL AUTO_INCREMENT,
  `precio_de_consulta` double NOT NULL,
  `id_registro_monetario` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  PRIMARY KEY (`id_consulta`,`id_registro_monetario`),
  KEY `fk_Consulta_Paciente1_idx` (`id_paciente`),
  KEY `fk_Consulta_Medico1_idx` (`id_empleado`),
  KEY `fk_Consulta_Pago1_idx` (`id_registro_monetario`),
  KEY `FK_EMPLEADO_IDX` (`id_empleado`),
  CONSTRAINT `FK_MEDICO` FOREIGN KEY (`id_empleado`) REFERENCES `Empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Consulta_Paciente1` FOREIGN KEY (`id_paciente`) REFERENCES `Paciente` (`id_paciente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Consulta_Pago1` FOREIGN KEY (`id_registro_monetario`) REFERENCES `Registro_Monetario` (`id_registro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consulta`
--

LOCK TABLES `Consulta` WRITE;
/*!40000 ALTER TABLE `Consulta` DISABLE KEYS */;
INSERT INTO `Consulta` VALUES (4,100,13,3,18),(6,100,46,3,18),(7,100,48,3,18),(8,100,49,3,18),(9,100,50,3,18),(10,100,51,3,18),(11,100,52,3,18),(12,100,53,3,18),(13,100,54,3,18),(14,100,55,3,18),(15,100,56,3,18),(16,100,57,3,18),(17,100,58,3,18),(18,100,59,3,18),(19,100,60,3,18),(20,100,61,3,18),(21,100,62,3,18),(22,100,63,3,18),(23,100,64,3,18),(24,100,65,3,18),(25,100,66,3,18),(29,100,71,3,18),(30,100,72,3,18),(31,100,73,3,18),(32,100,74,3,18),(33,100,75,3,18),(34,100,76,3,18),(35,100,77,3,18),(36,100,78,3,18),(37,100,79,3,18),(38,100,80,3,18),(39,100,81,3,18),(40,100,82,3,18),(41,100,83,3,18),(42,100,84,3,18),(43,100,85,3,18),(44,100,86,3,18),(45,100,87,3,18),(46,100,88,3,18),(47,100,89,3,18),(48,100,90,3,18),(49,100,91,3,18),(50,100,92,3,18),(51,100,93,3,18),(52,100,94,3,18),(53,100,95,3,18),(54,100,96,3,18),(55,100,97,3,18),(56,100,98,3,18),(57,100,99,3,18),(58,100,100,3,18),(59,100,101,3,18),(60,100,102,3,18),(61,100,103,3,18),(62,100,104,3,18),(63,100,105,3,18),(64,100,106,3,18),(106,100,148,3,18);
/*!40000 ALTER TABLE `Consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consulta_tiene_Medicamentos`
--

DROP TABLE IF EXISTS `Consulta_tiene_Medicamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Consulta_tiene_Medicamentos` (
  `id_consulta` int(11) NOT NULL,
  `id_medicamento` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 0,
  `suministrado` tinyint(1) NOT NULL DEFAULT 0,
  KEY `fk_Consulta_has_Medicamento_Medicamento1_idx` (`id_medicamento`),
  KEY `fk_Consulta_has_Medicamento_Consulta1_idx` (`id_consulta`),
  CONSTRAINT `fk_Consulta_has_Medicamento_Consulta1` FOREIGN KEY (`id_consulta`) REFERENCES `Consulta` (`id_consulta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Consulta_has_Medicamento_Medicamento1` FOREIGN KEY (`id_medicamento`) REFERENCES `Medicamento` (`id_medicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consulta_tiene_Medicamentos`
--

LOCK TABLES `Consulta_tiene_Medicamentos` WRITE;
/*!40000 ALTER TABLE `Consulta_tiene_Medicamentos` DISABLE KEYS */;
INSERT INTO `Consulta_tiene_Medicamentos` VALUES (6,2,1,0),(6,2,1,0),(8,1,2,0),(9,1,2,0),(9,2,2,0),(9,3,1,0),(10,1,2,0),(10,2,2,0),(10,3,1,0),(11,1,2,0),(11,2,21,0),(11,3,3,0),(12,1,2,0),(13,1,2,0),(14,1,2,0),(15,1,2,0),(16,1,2,0),(17,1,2,0),(18,1,2,0),(19,1,2,0),(19,2,3,0),(19,3,4,0),(20,1,2,0),(20,2,3,0),(20,3,4,0),(21,1,2,0),(21,2,3,0),(21,3,4,0),(22,1,3,0),(22,2,2,0),(23,1,3,0),(23,2,2,0),(24,1,3,0),(24,2,2,0),(25,1,3,0),(25,2,2,0);
/*!40000 ALTER TABLE `Consulta_tiene_Medicamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contrato`
--

DROP TABLE IF EXISTS `Contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contrato` (
  `id_contrato` int(11) NOT NULL AUTO_INCREMENT,
  `salario` double NOT NULL,
  `fecha_inicial` date NOT NULL,
  `fecha_final` date DEFAULT NULL,
  `id_empleado` int(11) NOT NULL,
  PRIMARY KEY (`id_contrato`),
  KEY `FK_USUARIO_idx` (`id_empleado`),
  CONSTRAINT `FK_USUARIO` FOREIGN KEY (`id_empleado`) REFERENCES `Empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contrato`
--

LOCK TABLES `Contrato` WRITE;
/*!40000 ALTER TABLE `Contrato` DISABLE KEYS */;
INSERT INTO `Contrato` VALUES (7,0.01,'2019-01-01',NULL,8),(8,45000,'2019-11-14',NULL,10),(9,7000,'2019-11-14',NULL,13),(10,90000,'2019-11-28',NULL,14),(11,87000,'2019-11-15',NULL,16),(12,0.01,'2019-01-01',NULL,17),(13,5000,'2019-01-01',NULL,18),(14,7500,'2019-11-20',NULL,19),(15,3500,'2019-09-06',NULL,20);
/*!40000 ALTER TABLE `Contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Credenciales`
--

DROP TABLE IF EXISTS `Credenciales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credenciales` (
  `id_credenciales` int(11) NOT NULL AUTO_INCREMENT,
  `correo_electronico` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `id_area` int(11) NOT NULL,
  PRIMARY KEY (`id_credenciales`,`id_empleado`,`id_area`),
  UNIQUE KEY `correo_electronico_UNIQUE` (`correo_electronico`),
  KEY `fk_Credenciales_Empleado1_idx` (`id_empleado`),
  KEY `fk_Credenciales_Area1_idx` (`id_area`),
  CONSTRAINT `fk_Credenciales_Area1` FOREIGN KEY (`id_area`) REFERENCES `Area` (`id_area`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Credenciales_Empleado1` FOREIGN KEY (`id_empleado`) REFERENCES `Empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Credenciales`
--

LOCK TABLES `Credenciales` WRITE;
/*!40000 ALTER TABLE `Credenciales` DISABLE KEYS */;
INSERT INTO `Credenciales` VALUES (1,'mail@mail.com','3',10,2),(2,'fuentesjosue83@gmail.com','adsf',13,2),(4,'kevinmail','password',14,1),(5,'farmacia@mail.com','password',16,3),(6,'farmacia2@mail.com','password',17,3),(7,'medico1@medicos.com','medico1',19,4),(8,'enfermera1@enfermera.com','enfermera1',20,5);
/*!40000 ALTER TABLE `Credenciales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Empleado`
--

DROP TABLE IF EXISTS `Empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Empleado` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `porcentaje_igss` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `porcentaje_irtra` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `activo` tinyint(4) NOT NULL DEFAULT 1,
  `fecha_de_vacaciones` date DEFAULT NULL,
  `ejecucion_de_vacaciones` tinyint(4) NOT NULL DEFAULT 0,
  `cui_persona` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_area` int(11) NOT NULL,
  PRIMARY KEY (`id_empleado`,`cui_persona`),
  UNIQUE KEY `idEmpleado_UNIQUE` (`id_empleado`),
  KEY `fk_Empleado_Persona1_idx` (`cui_persona`),
  KEY `fk_Empleado_Area1_idx` (`id_area`),
  CONSTRAINT `FK_AREA_EMPLEADO` FOREIGN KEY (`id_area`) REFERENCES `Area` (`id_area`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PERSONA` FOREIGN KEY (`cui_persona`) REFERENCES `Persona` (`cui`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Empleado`
--

LOCK TABLES `Empleado` WRITE;
/*!40000 ALTER TABLE `Empleado` DISABLE KEYS */;
INSERT INTO `Empleado` VALUES (8,'1','2',1,'2019-01-01',0,'123413241234',1),(10,'0','0',1,'2019-11-06',0,'2',2),(13,'5','10',1,'2019-11-14',0,'1341234',2),(14,'1','1',1,'2019-11-15',0,'1234',2),(16,'0','0',1,'2019-11-10',0,'765342',1),(17,'0','0',1,'2019-01-01',0,'46574567',3),(18,'1','1',1,'2019-01-01',0,'91320874',3),(19,'10','5',1,'2019-11-21',0,'245213241324',4),(20,'3','3',1,'2019-11-05',0,'321412309478',5);
/*!40000 ALTER TABLE `Empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Habitacion`
--

DROP TABLE IF EXISTS `Habitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Habitacion` (
  `id_habitacion` int(11) NOT NULL AUTO_INCREMENT,
  `esta_ocupada` tinyint(4) DEFAULT 0,
  `esta_habilitada` double DEFAULT 1,
  `precio_de_mantenimiento` double DEFAULT NULL,
  PRIMARY KEY (`id_habitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Habitacion`
--

LOCK TABLES `Habitacion` WRITE;
/*!40000 ALTER TABLE `Habitacion` DISABLE KEYS */;
INSERT INTO `Habitacion` VALUES (1,1,1,34);
/*!40000 ALTER TABLE `Habitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Internado`
--

DROP TABLE IF EXISTS `Internado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Internado` (
  `id_internado` int(11) NOT NULL AUTO_INCREMENT,
  `id_paciente` int(11) NOT NULL,
  `inicio` datetime NOT NULL,
  `fin` datetime DEFAULT NULL,
  `id_habitacion` int(11) NOT NULL,
  PRIMARY KEY (`id_internado`,`id_paciente`,`id_habitacion`),
  KEY `fk_Internados_Paciente1_idx` (`id_paciente`),
  KEY `fk_Internado_Habitacion1_idx` (`id_habitacion`),
  CONSTRAINT `fk_Internado_Habitacion1` FOREIGN KEY (`id_habitacion`) REFERENCES `Habitacion` (`id_habitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Internados_Paciente1` FOREIGN KEY (`id_paciente`) REFERENCES `Paciente` (`id_paciente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Internado`
--

LOCK TABLES `Internado` WRITE;
/*!40000 ALTER TABLE `Internado` DISABLE KEYS */;
INSERT INTO `Internado` VALUES (3,3,'2019-11-13 00:00:00','2019-11-14 00:00:00',1),(4,3,'2019-11-13 00:00:00','2019-11-14 00:00:00',1),(5,3,'2019-11-08 00:00:00','2019-11-14 00:00:00',1),(6,3,'2019-11-14 00:00:00','2019-11-14 00:00:00',1),(7,3,'2019-11-07 00:00:00','2019-11-14 00:00:00',1),(8,3,'2020-01-22 00:00:00',NULL,1);
/*!40000 ALTER TABLE `Internado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Internado_tiene_Empleado`
--

DROP TABLE IF EXISTS `Internado_tiene_Empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Internado_tiene_Empleado` (
  `id_internado` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  KEY `fk_Internado_has_Empleado_Empleado1_idx` (`id_empleado`),
  KEY `fk_Internado_has_Empleado_Internado1_idx` (`id_internado`,`id_paciente`),
  CONSTRAINT `fk_Internado_has_Empleado_Empleado1` FOREIGN KEY (`id_empleado`) REFERENCES `Empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Internado_has_Empleado_Internado1` FOREIGN KEY (`id_internado`, `id_paciente`) REFERENCES `Internado` (`id_internado`, `id_paciente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Internado_tiene_Empleado`
--

LOCK TABLES `Internado_tiene_Empleado` WRITE;
/*!40000 ALTER TABLE `Internado_tiene_Empleado` DISABLE KEYS */;
INSERT INTO `Internado_tiene_Empleado` VALUES (5,3,20),(5,3,19),(7,3,20),(7,3,19),(8,3,20),(8,3,19);
/*!40000 ALTER TABLE `Internado_tiene_Empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Internado_tiene_Medicamento`
--

DROP TABLE IF EXISTS `Internado_tiene_Medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Internado_tiene_Medicamento` (
  `id_internado` int(11) NOT NULL,
  `id_medicamento` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 0,
  `cantidad_suministrada` int(11) NOT NULL DEFAULT 0,
  `cantidad_cancelada` int(11) NOT NULL DEFAULT 0,
  KEY `fk_Internado_has_Medicamento_Medicamento1_idx` (`id_medicamento`),
  KEY `fk_Internado_has_Medicamento_Internado1_idx` (`id_internado`),
  CONSTRAINT `FK_INTERNADO` FOREIGN KEY (`id_internado`) REFERENCES `Internado` (`id_internado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Internado_has_Medicamento_Medicamento1` FOREIGN KEY (`id_medicamento`) REFERENCES `Medicamento` (`id_medicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Internado_tiene_Medicamento`
--

LOCK TABLES `Internado_tiene_Medicamento` WRITE;
/*!40000 ALTER TABLE `Internado_tiene_Medicamento` DISABLE KEYS */;
INSERT INTO `Internado_tiene_Medicamento` VALUES (5,1,2,0,0),(7,1,2,0,0),(8,1,2,0,0),(8,1,3,0,0),(8,2,3,0,0);
/*!40000 ALTER TABLE `Internado_tiene_Medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medicamento`
--

DROP TABLE IF EXISTS `Medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Medicamento` (
  `id_medicamento` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `costo` double NOT NULL,
  `precio` double NOT NULL,
  `cant_existencia` int(11) DEFAULT 0,
  `cant_minima` int(11) NOT NULL DEFAULT 10,
  PRIMARY KEY (`id_medicamento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medicamento`
--

LOCK TABLES `Medicamento` WRITE;
/*!40000 ALTER TABLE `Medicamento` DISABLE KEYS */;
INSERT INTO `Medicamento` VALUES (1,'Amoxicilina',23,32,9,10),(2,'Tabcin',1.3,2,100,100),(3,'Ibuprofeno',2,3,100,100);
/*!40000 ALTER TABLE `Medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medico_Especialista`
--

DROP TABLE IF EXISTS `Medico_Especialista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Medico_Especialista` (
  `id_medico_especialista` int(11) NOT NULL AUTO_INCREMENT,
  `no_de_colegiado` int(11) NOT NULL,
  `cui_persona` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_medico_especialista`,`cui_persona`),
  KEY `fk_MedicoEspecialista_Persona1_idx` (`cui_persona`),
  CONSTRAINT `fk_MedicoEspecialista_Persona1` FOREIGN KEY (`cui_persona`) REFERENCES `Persona` (`cui`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medico_Especialista`
--

LOCK TABLES `Medico_Especialista` WRITE;
/*!40000 ALTER TABLE `Medico_Especialista` DISABLE KEYS */;
INSERT INTO `Medico_Especialista` VALUES (1,1234,'13412432',1),(2,2354,'19320874',1);
/*!40000 ALTER TABLE `Medico_Especialista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modulo`
--

DROP TABLE IF EXISTS `Modulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modulo` (
  `id_modulo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modulo`
--

LOCK TABLES `Modulo` WRITE;
/*!40000 ALTER TABLE `Modulo` DISABLE KEYS */;
INSERT INTO `Modulo` VALUES (1,'Administracion'),(2,'Empleados'),(3,'Farmacia');
/*!40000 ALTER TABLE `Modulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Paciente`
--

DROP TABLE IF EXISTS `Paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Paciente` (
  `id_paciente` int(11) NOT NULL AUTO_INCREMENT,
  `nit` varchar(13) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `internado` tinyint(1) NOT NULL DEFAULT 0,
  `saldo` double NOT NULL DEFAULT 0,
  `cui` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_paciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paciente`
--

LOCK TABLES `Paciente` WRITE;
/*!40000 ALTER TABLE `Paciente` DISABLE KEYS */;
INSERT INTO `Paciente` VALUES (3,'12341324',0,0,'8915372');
/*!40000 ALTER TABLE `Paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Persona`
--

DROP TABLE IF EXISTS `Persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Persona` (
  `cui` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` mediumtext COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`cui`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Persona`
--

LOCK TABLES `Persona` WRITE;
/*!40000 ALTER TABLE `Persona` DISABLE KEYS */;
INSERT INTO `Persona` VALUES ('1234','adf','asdf'),('123413241234','Kevin','d'),('1341234','Journal','2'),('13412432','Medico Especialista 1','Zona 13'),('19320874','Medico Especialista 2','zona 9'),('2','Diana','g'),('245213241324','Medico 1 ','direccion2 '),('321412309478','Enfermera1','direccion3'),('46574567','Journal','dir'),('765342','Journal','dir'),('8915372','Paciente1','direccion1'),('91320874','Farmacia 2','dir');
/*!40000 ALTER TABLE `Persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Registro_Monetario`
--

DROP TABLE IF EXISTS `Registro_Monetario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Registro_Monetario` (
  `id_registro` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto` double NOT NULL COMMENT 'Si es Verdadero, es un ingreso, si es falso, es un egreso. ',
  `fecha` date NOT NULL,
  `tipo` tinyint(4) NOT NULL,
  `id_area` int(11) NOT NULL,
  PRIMARY KEY (`id_registro`),
  KEY `fk_Registro_Monetario_Area1_idx` (`id_area`),
  CONSTRAINT `fk_Registro_Monetario_Area1` FOREIGN KEY (`id_area`) REFERENCES `Area` (`id_area`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Registro_Monetario`
--

LOCK TABLES `Registro_Monetario` WRITE;
/*!40000 ALTER TABLE `Registro_Monetario` DISABLE KEYS */;
INSERT INTO `Registro_Monetario` VALUES (1,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(2,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(3,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(4,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(5,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(6,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(7,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(8,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(9,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(10,'Compra de 10 unidades de Amoxicilina',230,'2019-11-05',0,3),(11,'Compra de 20 unidades de Amoxicilina',460,'2019-11-05',0,3),(12,'Compra de 100 unidades de Tabcin',130,'2019-11-05',0,3),(13,'Compra de -10 unidades de Tabcin',-13,'2019-11-05',0,3),(46,'consulta ordinaria a cliente',100,'2019-11-09',1,3),(48,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(49,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(50,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(51,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(52,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(53,'consulta ordinaria a cliente',100,'2019-11-09',1,3),(54,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(55,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(56,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(57,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(58,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(59,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(60,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(61,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(62,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(63,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(64,'consulta ordinaria a cliente',100,'2019-11-10',1,3),(65,'consulta ordinaria a cliente',100,'2019-11-10',1,3),(66,'consulta ordinaria a cliente',100,'2019-11-10',1,3),(67,'consulta ordinaria a cliente',100,'2019-11-10',1,3),(71,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(72,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(73,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(74,'consulta ordinaria a cliente',100,'2019-11-14',1,3),(75,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(76,'consulta ordinaria a cliente',100,'2019-11-16',1,3),(77,'consulta ordinaria a cliente',100,'2019-11-16',1,3),(78,'consulta ordinaria a cliente',100,'2019-11-16',1,3),(79,'consulta ordinaria a cliente',100,'2019-11-16',1,3),(80,'consulta ordinaria a cliente',100,'2019-11-16',1,3),(81,'consulta ordinaria a cliente',100,'2019-11-05',1,3),(82,'consulta ordinaria a cliente',100,'2019-11-15',1,3),(83,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(84,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(85,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(86,'consulta ordinaria a cliente',100,'2019-11-08',1,3),(87,'consulta ordinaria a cliente',100,'2019-11-29',1,3),(88,'consulta ordinaria a cliente',100,'2019-11-29',1,3),(89,'consulta ordinaria a cliente',100,'2019-11-29',1,3),(90,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(91,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(92,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(93,'consulta ordinaria a cliente',100,'2019-11-20',1,3),(94,'consulta ordinaria a cliente',100,'2019-11-14',1,3),(95,'consulta ordinaria a cliente',100,'2019-11-14',1,3),(96,'consulta ordinaria a cliente',100,'2019-10-29',1,3),(97,'consulta ordinaria a cliente',100,'2019-10-29',1,3),(98,'consulta ordinaria a cliente',100,'2019-11-07',1,3),(99,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(100,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(101,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(102,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(103,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(104,'consulta ordinaria a cliente',100,'2019-11-14',1,3),(105,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(106,'consulta ordinaria a cliente',100,'2019-11-13',1,3),(148,'consulta ordinaria a cliente',100,'2020-01-22',1,3);
/*!40000 ALTER TABLE `Registro_Monetario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Registros_Medicamento`
--

DROP TABLE IF EXISTS `Registros_Medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Registros_Medicamento` (
  `id_venta` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `costo_actual_medicamento` double NOT NULL,
  `precio_actual_medicamento` double NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 0,
  `tipo_operacion` tinyint(1) NOT NULL DEFAULT 0,
  `id_medicamento` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `id_registro` int(11) NOT NULL,
  PRIMARY KEY (`id_venta`,`id_registro`),
  KEY `fk_VentaDeMedicamento_Medicamento1_idx` (`id_medicamento`),
  KEY `fk_VentaDeMedicamento_Empleado1_idx` (`id_empleado`),
  KEY `fk_VentaDeMedicamento_Pago1_idx` (`id_registro`),
  CONSTRAINT `fk_VentaDeMedicamento_Empleado1` FOREIGN KEY (`id_empleado`) REFERENCES `Empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VentaDeMedicamento_Medicamento1` FOREIGN KEY (`id_medicamento`) REFERENCES `Medicamento` (`id_medicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VentaDeMedicamento_Pago1` FOREIGN KEY (`id_registro`) REFERENCES `Registro_Monetario` (`id_registro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Registros_Medicamento`
--

LOCK TABLES `Registros_Medicamento` WRITE;
/*!40000 ALTER TABLE `Registros_Medicamento` DISABLE KEYS */;
INSERT INTO `Registros_Medicamento` VALUES (1,'2019-11-05',23,32,10,0,1,16,5),(2,'2019-11-05',23,32,10,0,1,16,6),(3,'2019-11-05',23,32,10,0,1,16,7),(4,'2019-11-05',23,32,10,0,1,16,8),(5,'2019-11-05',23,32,10,0,1,16,9),(6,'2019-11-05',23,32,20,0,1,16,10),(7,'2019-11-05',1.3,2,100,0,2,16,11),(8,'2019-11-05',1.3,2,-10,0,2,16,12);
/*!40000 ALTER TABLE `Registros_Medicamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-17 10:22:40
