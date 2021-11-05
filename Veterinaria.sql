CREATE DATABASE  IF NOT EXISTS `jparegistro02` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `jparegistro02`;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargo` (
  `idcargo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) NOT NULL,
  PRIMARY KEY (`idcargo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COMMENT='Catalogo para los cargos de los empleados';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (1,'Docente'),(2,'Vendedor'),(3,'Gerente'),(4,'Contador'),(5,'Programador'),(6,'Oficios Varios'),(7,'Secretaria'),(8,'Auxiliar limpieza'),(9,'Auxiliar contabilidad'),(10,'Auxiliar de mecanica'),(11,'Mecanico'),(12,'Mensajero'),(13,'Director');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamentos`
--

DROP TABLE IF EXISTS `departamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departamentos` (
  `Id_departamento` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamentos`
--

LOCK TABLES `departamentos` WRITE;
/*!40000 ALTER TABLE `departamentos` DISABLE KEYS */;
INSERT INTO `departamentos` VALUES (1,'Ventas'),(2,'Finanzas'),(3,'Informatica'),(4,'RRHH'),(5,'Mantenimiento');
/*!40000 ALTER TABLE `departamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleado` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Cargo` int(11) NOT NULL,
  `Departamento` int(11) NOT NULL,
  `Jefe` varchar(50) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `FK_depto_idx` (`Departamento`),
  KEY `FK_CARGO_idx` (`Cargo`),
  CONSTRAINT `FK_CARGO` FOREIGN KEY (`Cargo`) REFERENCES `cargo` (`idcargo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_depto` FOREIGN KEY (`Departamento`) REFERENCES `departamentos` (`Id_departamento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (33,'Alex',5,3,'Diego'),(34,'Manuel',2,1,'Alex'),(37,'Maria',8,5,'Gracia'),(38,'Paco',1,3,'Luis');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;


-- Dump completed on 2020-10-15 15:34:45
