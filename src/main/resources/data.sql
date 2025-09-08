SET FOREIGN_KEY_CHECKS = 0;

-- Limpeza na ordem segura (filhos -> pais)
TRUNCATE TABLE `vendas`;
TRUNCATE TABLE `produtos`;
TRUNCATE TABLE `clientes`;
TRUNCATE TABLE `fornecedores`;

-- SEED: FORNECEDORES
INSERT INTO `fornecedores`
(`id`, `cnpj`, `codigo_anel`, `codigo_berloque`, `codigo_bracelete`, `codigo_brinco`, `codigo_colar`, `codigo_conjunto`, `codigo_piercing`, `codigo_pingente`, `codigo_pulseira`, `data_atualizacao`, `data_criacao`, `descricao`, `nome`, `telefone`)
VALUES
(1,'12.345.678/0001-99','An','Bq','Bc','Br','Cl','Cj','Pc','Pg','Pl','2025-02-01 10:00:00','2025-02-01 10:00:00','Especializado em prata','Prata Elegante','(11) 99999-9999'),
(2,'98.765.432/0001-55','AAn','ABq','ABc','ABr','ACl','ACj','APc','APg','APl','2025-02-02 12:00:00','2025-02-02 12:00:00','Aço e banho dourado','Aço Fino','(21) 98888-7777');

-- SEED: CLIENTES
INSERT INTO `clientes`
(`id`, `cpf`, `data_atualizacao`, `data_criacao`, `descricao`, `email`, `nome`, `telefone`)
VALUES
(1,'714.958.566-81','2025-02-07 12:00:00','2025-02-07 12:00:00','Cliente fiel',NULL,'Isabela Rodrigues','(53) 91701-3345'),
(2,'419.917.261-44','2025-03-26 12:00:00','2025-03-26 12:00:00','Cliente fiel',NULL,'Carla Mendes','(18) 95192-9823'),
(3,'773.361.328-75','2025-03-02 12:00:00','2025-03-02 12:00:00','Compra frequente',NULL,'Bruno Santos','(31) 99124-3561');

-- SEED: PRODUTOS (referenciam fornecedores)
INSERT INTO `produtos`
(`id`, `acabamento`, `categoria`, `data_atualizacao`, `data_criacao`, `data_venda`, `id_referencia`, `nome`, `preco_custo`, `preco_venda`, `status`, `id_fornecedor`)
VALUES
(1,'BANHO_PRATA','Anel','2025-04-01 10:00:00','2025-04-01 10:00:00',NULL,'An1001','Anel zirconia',120.00,NULL,'EM_ESTOQUE',1),
(2,'ACO','Colar','2025-04-02 10:00:00','2025-04-02 10:00:00','2025-05-01 14:30:00','Cl2001','Colar minimalista',80.00,150.00,'VENDIDO',2),
(3,'PRATA','Pulseira','2025-04-03 10:00:00','2025-04-03 10:00:00',NULL,'Pl3001','Pulseira elos',90.00,NULL,'EM_ESTOQUE',1);

-- SEED: VENDAS (referenciam clientes e produtos)
INSERT INTO `vendas`
(`id`, `data_atualizacao`, `data_criacao`, `data_vencimento`, `forma_pagamento`, `lucro`, `parcelas_pagas`, `preco_venda`, `status`, `total_parcelas`, `cliente_id`, `produto_id`)
VALUES
(1,'2025-05-01 14:31:00','2025-05-01 14:30:00','2025-06-01','PIX',70.00,1,150.00,'PAGO',1,2,2);

SET FOREIGN_KEY_CHECKS = 1;

-- Observações:
-- 1) Este script é idempotente graças aos TRUNCATEs. Ao subir em produção, prefira migrações Flyway/Liquibase.
-- 2) Se desejar manter dados existentes e apenas inserir se não houver, troque TRUNCATE por INSERT ... ON DUPLICATE KEY UPDATE id=id;
