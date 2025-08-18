-- Criar o banco de dados (se ele ainda não existir)
CREATE DATABASE IF NOT EXISTS `gleam_estoque_joias`;
USE `gleam_estoque_joias`;

-- Tabela: cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cpf` VARCHAR(255) DEFAULT NULL,
  `descricao` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `nome` VARCHAR(255) DEFAULT NULL,
  `telefone` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Tabela: fornecedor
CREATE TABLE IF NOT EXISTS `fornecedor` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cnpj` VARCHAR(255) DEFAULT NULL,
  `codigo_anel` VARCHAR(255) DEFAULT NULL,
  `codigo_berloque` VARCHAR(255) DEFAULT NULL,
  `codigo_bracelete` VARCHAR(255) DEFAULT NULL,
  `codigo_brinco` VARCHAR(255) DEFAULT NULL,
  `codigo_colar` VARCHAR(255) DEFAULT NULL,
  `codigo_conjunto` VARCHAR(255) DEFAULT NULL,
  `codigo_piercing` VARCHAR(255) DEFAULT NULL,
  `codigo_pingente` VARCHAR(255) DEFAULT NULL,
  `codigo_pulseira` VARCHAR(255) DEFAULT NULL,
  `descricao` VARCHAR(255) DEFAULT NULL,
  `nome` VARCHAR(255) DEFAULT NULL,
  `telefone` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Tabela: produto (ATUALIZADA)
CREATE TABLE IF NOT EXISTS `produto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `acabamento` TINYINT(4) NOT NULL,
  `codigofornecedor` VARCHAR(255) DEFAULT NULL,
  `categoria` VARCHAR(255) DEFAULT NULL, -- Campo de texto para a categoria
  `descricao` VARCHAR(255) DEFAULT NULL,
  `imagem` VARCHAR(255) DEFAULT NULL,
  `nome` VARCHAR(255) DEFAULT NULL,
  `preco_custo` DECIMAL(10,2) NOT NULL,
  `preco_venda` DECIMAL(10,2) NOT NULL,
  `id_fornecedor` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_codigofornecedor` (`codigofornecedor`),
  KEY `FK_produto_fornecedor` (`id_fornecedor`),
  CONSTRAINT `FK_produto_fornecedor` FOREIGN KEY (`id_fornecedor`) REFERENCES `fornecedor` (`id`)
);

-- Tabela: venda
CREATE TABLE IF NOT EXISTS `venda` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `data_venda` DATETIME(6) NOT NULL,
  `status` ENUM('CANCELADA','CONCLUIDA','PENDENTE') NOT NULL,
  `valor_total` DECIMAL(10,2) NOT NULL,
  `id_cliente` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_venda_cliente` (`id_cliente`),
  CONSTRAINT `FK_venda_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
);

-- Tabela: item_venda
CREATE TABLE IF NOT EXISTS `item_venda` (
  `preco_unitario` DECIMAL(10,2) NOT NULL,
  `quantidade` INT(11) NOT NULL,
  `id_produto` BIGINT(20) NOT NULL,
  `id_venda` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_produto`,`id_venda`),
  KEY `FK_itemvenda_venda` (`id_venda`),
  CONSTRAINT `FK_itemvenda_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`),
  CONSTRAINT `FK_itemvenda_venda` FOREIGN KEY (`id_venda`) REFERENCES `venda` (`id`)
);

-- Tabela: movimentacao_estoque
CREATE TABLE IF NOT EXISTS `movimentacao_estoque` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `data_movimentacao` DATETIME(6) NOT NULL,
  `observacao` VARCHAR(255) DEFAULT NULL,
  `quantidade` INT(11) NOT NULL,
  `tipo` TINYINT(4) NOT NULL,
  `id_produto` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movimentacao_produto` (`id_produto`),
  CONSTRAINT `FK_movimentacao_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
);
