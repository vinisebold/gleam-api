-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.4.32-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para gleam_estoque_joias
CREATE DATABASE IF NOT EXISTS `gleam_estoque_joias` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `gleam_estoque_joias`;

-- Copiando estrutura para tabela gleam_estoque_joias.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.categoria: ~0 rows (aproximadamente)
DELETE FROM `categoria`;

-- Copiando estrutura para tabela gleam_estoque_joias.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.cliente: ~0 rows (aproximadamente)
DELETE FROM `cliente`;

-- Copiando estrutura para tabela gleam_estoque_joias.fornecedor
CREATE TABLE IF NOT EXISTS `fornecedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(255) DEFAULT NULL,
  `codigo_anel` varchar(255) DEFAULT NULL,
  `codigo_berloque` varchar(255) DEFAULT NULL,
  `codigo_bracelete` varchar(255) DEFAULT NULL,
  `codigo_brinco` varchar(255) DEFAULT NULL,
  `codigo_colar` varchar(255) DEFAULT NULL,
  `codigo_conjunto` varchar(255) DEFAULT NULL,
  `codigo_piercing` varchar(255) DEFAULT NULL,
  `codigo_pingente` varchar(255) DEFAULT NULL,
  `codigo_pulseira` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.fornecedor: ~0 rows (aproximadamente)
DELETE FROM `fornecedor`;

-- Copiando estrutura para tabela gleam_estoque_joias.item_venda
CREATE TABLE IF NOT EXISTS `item_venda` (
  `preco_unitario` decimal(10,2) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `id_produto` bigint(20) NOT NULL,
  `id_venda` bigint(20) NOT NULL,
  PRIMARY KEY (`id_produto`,`id_venda`),
  KEY `FK87qej2cxtg9b3px8smmpaq8nq` (`id_venda`),
  CONSTRAINT `FK87qej2cxtg9b3px8smmpaq8nq` FOREIGN KEY (`id_venda`) REFERENCES `venda` (`id`),
  CONSTRAINT `FK8gx9ywjid5ol0tghn01vyxf68` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.item_venda: ~0 rows (aproximadamente)
DELETE FROM `item_venda`;

-- Copiando estrutura para tabela gleam_estoque_joias.movimentacao_estoque
CREATE TABLE IF NOT EXISTS `movimentacao_estoque` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_movimentacao` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `tipo` tinyint(4) NOT NULL,
  `id_produto` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6proay5uteqs07vlpdp255jr3` (`id_produto`),
  CONSTRAINT `FK6proay5uteqs07vlpdp255jr3` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.movimentacao_estoque: ~0 rows (aproximadamente)
DELETE FROM `movimentacao_estoque`;

-- Copiando estrutura para tabela gleam_estoque_joias.produto
CREATE TABLE IF NOT EXISTS `produto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acabamento` tinyint(4) NOT NULL,
  `codigofornecedor` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `imagem` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `preco_custo` decimal(10,2) NOT NULL,
  `preco_venda` decimal(10,2) NOT NULL,
  `id_categoria` bigint(20) DEFAULT NULL,
  `id_fornecedor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK63u26lgnwst76otv5wyp2or49` (`codigofornecedor`),
  KEY `FKbb0k43mtsufg8bfhq0gyaxhhm` (`id_categoria`),
  KEY `FKg0kbs9pp5getbcfp892wf3y1c` (`id_fornecedor`),
  CONSTRAINT `FKbb0k43mtsufg8bfhq0gyaxhhm` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FKg0kbs9pp5getbcfp892wf3y1c` FOREIGN KEY (`id_fornecedor`) REFERENCES `fornecedor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.produto: ~0 rows (aproximadamente)
DELETE FROM `produto`;

-- Copiando estrutura para tabela gleam_estoque_joias.venda
CREATE TABLE IF NOT EXISTS `venda` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_venda` datetime(6) NOT NULL,
  `status` enum('CANCELADA','CONCLUIDA','PENDENTE') NOT NULL,
  `valor_total` decimal(10,2) NOT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnbsmyglw8h9b98hi7b1s6n6ya` (`id_cliente`),
  CONSTRAINT `FKnbsmyglw8h9b98hi7b1s6n6ya` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela gleam_estoque_joias.venda: ~0 rows (aproximadamente)
DELETE FROM `venda`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
