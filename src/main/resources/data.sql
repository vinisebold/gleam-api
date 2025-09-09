-- Delete existing data in order to respect foreign key constraints
DELETE
FROM vendas;
DELETE
FROM produtos;
DELETE
FROM clientes;
DELETE
FROM fornecedores;
-- Insert data into fornecedores
INSERT INTO fornecedores (id, cnpj, codigo_anel, codigo_berloque, codigo_bracelete, codigo_brinco, codigo_colar,
                          codigo_conjunto, codigo_piercing, codigo_pingente, codigo_pulseira, data_atualizacao,
                          data_criacao, descricao, nome, telefone)
VALUES (1, '12.345.678/0001-90', 'An', 'Bq', 'Bc', 'Br', 'Cl', 'Cj', 'Pirc', 'Pg', 'Pl', '2025-02-01 10:00:00.000000',
        '2025-02-01 10:00:00.000000', 'Especializado em semi-joias de prata.', 'Fornecedor A', '(48) 93456-7890'),
       (2, '23.456.789/0001-01', 'An', 'Bq', 'Bc', 'Br', 'Cl', 'Cj', 'Pirc', 'Pg', 'Pl', '2025-02-01 11:00:00.000000',
        '2025-02-01 11:00:00.000000', 'Fabricação de joias finas em ouro 18k', 'Fornecedor B', '(47) 92345-6789'),
       (3, '34.567.890/0001-12', 'An', 'Bq', 'Bc', 'Br', 'Cl', 'Cj', 'Pirc', 'Pg', 'Pl', '2025-02-01 12:00:00.000000',
        '2025-02-01 12:00:00.000000', 'bijuterias e semi-joias.', 'Fornecedor C', '(55) 94567-8901'),
       (4, '45.678.901/0001-23', 'An', 'Bq', 'Bc', 'Br', 'Cl', 'Cj', 'Pirc', 'Pg', 'Pl', '2025-02-01 13:00:00.000000',
        '2025-02-01 13:00:00.000000', 'joias de prata e ouro branco.', 'Fornecedor D', '(47) 93456-1234'),
       (5, '15.475.960/0001-51', 'An', 'Bq', 'Bc', 'Br', 'Cl', 'Cj', 'Pirc', 'Pg', 'Pl', '2025-02-01 13:00:00.000000',
        '2025-02-01 13:00:00.000000', 'bijuterias e joias de prata', 'Fornecedor E', '(48) 99956-1234');
-- Insert data into clientes
INSERT INTO clientes (id, cpf, data_atualizacao, data_criacao, descricao, email, nome, telefone)
VALUES (1, '111.222.333-44', '2025-02-10 10:00:00.000000', '2025-02-10 10:00:00.000000', 'Alberto Stein',
        'ana.silva@email.com', 'Ana Clara Silva', '(11) 98765-4321'),
       (2, '222.333.444-55', '2025-02-15 11:00:00.000000', '2025-02-15 11:00:00.000000', '', 'bruno.cp@provedor.net',
        'Bruno Costa Pereira', '(21) 2345-6789'),
       (3, '333.444.555-66', '2025-02-20 12:00:00.000000', '2025-02-20 12:00:00.000000', 'Anita Garibaldi',
        'carla.lima@mail.org', 'Carla Souza Lima', '(31) 99999-1111'),
       (4, '444.555.666-77', '2025-02-25 13:00:00.000000', '2025-02-25 13:00:00.000000', '', 'd.santos@emailserver.com',
        'Daniel Oliveira Santos', '(41) 4444-3333'),
       (5, '555.666.777-88', '2025-03-05 10:00:00.000000', '2025-03-05 10:00:00.000000', '', 'eduarda.gomes@mail.com',
        'Eduarda Ferreira Gomes', '(51) 91234-5678'),
       (6, '666.777.888-99', '2025-03-10 11:00:00.000000', '2025-03-10 11:00:00.000000', '', 'f.rocha@email.br',
        'Felipe Martins Rocha', '(61) 3333-2222'),
       (7, '777.888.999-00', '2025-03-15 12:00:00.000000', '2025-03-15 12:00:00.000000', '',
        'giovana.alves@provedor.com', 'Giovana Almeida Alves', '(71) 98888-7777'),
       (8, '888.999.000-11', '2025-03-20 13:00:00.000000', '2025-03-20 13:00:00.000000', '', 'heitor.rp@email.net',
        'Heitor Rodrigues Pinto', '(81) 5555-1111'),
       (9, '999.000.111-22', '2025-03-25 14:00:00.000000', '2025-03-25 14:00:00.000000', '',
        'isabela.castro@mailservice.org', 'Isabela Castro Pereira', '(91) 97654-3210'),
       (10, '101.010.101-01', '2025-03-30 15:00:00.000000', '2025-03-30 15:00:00.000000', '', 'j.victor.f@email.com',
        'João Victor Fernandes', '(11) 4002-8922'),
       (11, '202.020.202-02', '2025-04-05 16:00:00.000000', '2025-04-05 16:00:00.000000', '',
        'larissa.dias@emailserver.com', 'Larissa Ribeiro Dias', '(31) 98877-6655'),
       (12, '303.030.303-03', '2025-04-10 17:00:00.000000', '2025-04-10 17:00:00.000000', '', 'mateus.borges@email.br',
        'Mateus Henrique Borges', '(41) 91122-3344');
-- Insert data into produtos
-- Anéis
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (1, 'BANHO_PRATA', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'An002',
        'Anel Infinito Duplo', 38.10, NULL, 'EM_ESTOQUE', 1),
       (2, 'BANHO_DOURADO', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'An123',
        'Anel Coração Triplo', 45.10, NULL, 'EM_ESTOQUE', 2),
       (3, 'PRATA', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'An456',
        'Anel Nó da Amizade', 65.60, NULL, 'EM_ESTOQUE', 3),
       (4, 'BANHO_DOURADO', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'An078',
        'Anel Coroa Delicada', 50.10, NULL, 'EM_ESTOQUE', 4),
       (5, 'BANHO_PRATA', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000',
        '2025-09-09 10:40:22.838519', 'An321', 'Anel Estrela Cadente', 35.00, 120.00, 'VENDIDO', 1),
       (6, 'PRATA', 'Anel', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'An499',
        'Anel Espiral da Vida', 70.60, NULL, 'EM_ESTOQUE', 2);
-- Berloques
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (7, 'BANHO_PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq011',
        'Berloque Coração Vazado Duplo', 38.10, NULL, 'EM_ESTOQUE', 3),
       (8, 'PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq234',
        'Berloque Estrela do Mar', 55.00, NULL, 'EM_ESTOQUE', 4),
       (9, 'BANHO_PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq345',
        'Berloque Pena Leve', 60.60, NULL, 'EM_ESTOQUE', 1),
       (10, 'BANHO_DOURADO', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq067',
        'Berloque Trevo da Sorte', 42.00, NULL, 'EM_ESTOQUE', 2),
       (11, 'BANHO_PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq189',
        'Berloque Ramos de Oliva', 34.10, NULL, 'EM_ESTOQUE', 3),
       (12, 'PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq400',
        'Berloque Onda do Mar', 58.10, NULL, 'EM_ESTOQUE', 4),
       (13, 'BANHO_PRATA', 'Berloque', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bq255',
        'Berloque Flor Cerejeira', 76.60, NULL, 'EM_ESTOQUE', 1);
-- Braceletes
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (14, 'BANHO_PRATA', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc033',
        'Bracelete Círculo Trançado', 48.00, NULL, 'EM_ESTOQUE', 2),
       (15, 'PRATA', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc111',
        'Bracelete Folhas Unidas', 95.10, NULL, 'EM_ESTOQUE', 3),
       (16, 'BANHO_DOURADO', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc222',
        'Bracelete Estrela Zirconia', 65.60, NULL, 'EM_ESTOQUE', 4),
       (17, 'BANHO_PRATA', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc444',
        'Bracelete Flor do Campo', 45.10, NULL, 'EM_ESTOQUE', 1),
       (18, 'PRATA', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc055',
        'Bracelete Gota Cristal', 110.60, NULL, 'EM_ESTOQUE', 2),
       (19, 'PRATA', 'Bracelete', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Bc300',
        'Bracelete Nós Celtas', 105.60, NULL, 'EM_ESTOQUE', 3);
-- Brincos
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (20, 'BANHO_PRATA', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br001',
        'Brinco Estrela Cravejada', 40.60, NULL, 'EM_ESTOQUE', 4),
       (21, 'BANHO_DOURADO', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br042',
        'Brinco Laço Cravejado', 55.10, NULL, 'EM_ESTOQUE', 1),
       (22, 'PRATA', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br333',
        'Brinco Flor Cravejada', 80.00, NULL, 'EM_ESTOQUE', 2),
       (23, 'DOURADO', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br500',
        'Brinco Sol Radiante', 952.00, NULL, 'EM_ESTOQUE', 3),
       (24, 'BANHO_PRATA', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br127',
        'Brinco Arco-Íris Delicado', 78.10, NULL, 'EM_ESTOQUE', 4),
       (25, 'PRATA', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br250', 'Brinco Gota',
        75.00, NULL, 'EM_ESTOQUE', 1),
       (26, 'BANHO_DOURADO', 'Brinco', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Br088',
        'Brinco Argola Torcida', 60.60, NULL, 'EM_ESTOQUE', 2);
-- Colares
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (27, 'PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl009',
        'Colar Cruz Delicada', 90.10, NULL, 'EM_ESTOQUE', 3),
       (28, 'BANHO_PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl117',
        'Colar Estrela Guia', 45.00, NULL, 'EM_ESTOQUE', 4),
       (29, 'BANHO_DOURADO', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl208',
        'Colar Chave do Amor', 68.60, NULL, 'EM_ESTOQUE', 1),
       (30, 'PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl350',
        'Colar Lua Vazada', 85.00, NULL, 'EM_ESTOQUE', 2),
       (31, 'BANHO_PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl475',
        'Colar Infinito Coração', 42.60, NULL, 'EM_ESTOQUE', 3),
       (32, 'DOURADO', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl499',
        'Colar Raio Reluzente', 662.00, NULL, 'EM_ESTOQUE', 4),
       (33, 'PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl066',
        'Colar Coração Vazado', 88.10, NULL, 'EM_ESTOQUE', 1),
       (34, 'PRATA', 'Colar', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cl199', 'Colar Cruz',
        92.60, NULL, 'EM_ESTOQUE', 2);
-- Conjuntos
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (35, 'BANHO_PRATA', 'Conjunto', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cj010',
        'Conjunto Corações Entrelaçados', 50.10, NULL, 'EM_ESTOQUE', 3),
       (36, 'PRATA', 'Conjunto', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cj150',
        'Conjunto Borboleta Vazada', 120.60, NULL, 'EM_ESTOQUE', 4),
       (37, 'BANHO_DOURADO', 'Conjunto', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cj275',
        'Conjunto Gota Verde', 70.10, NULL, 'EM_ESTOQUE', 1),
       (38, 'BANHO_PRATA', 'Conjunto', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cj390',
        'Conjunto Símbolo Paz', 48.00, NULL, 'EM_ESTOQUE', 2),
       (39, 'PRATA', 'Conjunto', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Cj425',
        'Conjunto Coração Batimento', 115.10, NULL, 'EM_ESTOQUE', 3);
-- Piercings
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (40, 'ACO', 'Piercing', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pirc020',
        'Piercing Mão Unida', 25.10, NULL, 'EM_ESTOQUE', 4),
       (41, 'BANHO_DOURADO', 'Piercing', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pirc140',
        'Piercing Gota Dourada', 40.60, NULL, 'EM_ESTOQUE', 1),
       (42, 'ACO', 'Piercing', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pirc360',
        'Piercing Estrela Tripla', 28.00, NULL, 'EM_ESTOQUE', 2);
-- Pingentes
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (43, 'BANHO_DOURADO', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg030',
        'Pingente Olho de Tigre', 45.10, NULL, 'EM_ESTOQUE', 3),
       (44, 'PRATA', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg170',
        'Pingente Flor de Liz', 60.10, NULL, 'EM_ESTOQUE', 4),
       (45, 'BANHO_PRATA', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg290',
        'Pingente Concha do Mar', 35.10, NULL, 'EM_ESTOQUE', 1),
       (46, 'BANHO_PRATA', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg410',
        'Pingente Estrela Noturna', 33.00, NULL, 'EM_ESTOQUE', 2),
       (47, 'DOURADO', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg480',
        'Pingente Círculo Solar', 848.00, NULL, 'EM_ESTOQUE', 3),
       (48, 'BANHO_DOURADO', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg125',
        'Pingente Árvore', 50.10, NULL, 'EM_ESTOQUE', 4),
       (49, 'BANHO_DOURADO', 'Pingente', '2025-02-05 10:00:00.000000', '2025-02-05 10:00:00.000000', NULL, 'Pg245',
        'Pingente Olho Grego', 76.60, NULL, 'EM_ESTOQUE', 1);
-- Novos produtos para Fornecedor 1
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (50, 'BANHO_PRATA', 'Anel', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'An501',
        'Anel Lua Crescente', 39.50, NULL, 'EM_ESTOQUE', 1),
       (51, 'BANHO_DOURADO', 'Berloque', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bq502',
        'Berloque Árvore da Vida', 47.20, NULL, 'EM_ESTOQUE', 1),
       (52, 'PRATA', 'Bracelete', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bc503',
        'Bracelete Linhas Infinitas', 89.90, NULL, 'EM_ESTOQUE', 1),
       (53, 'BANHO_PRATA', 'Brinco', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Br504',
        'Brinco Pétala Delicada', 62.30, NULL, 'EM_ESTOQUE', 1),
       (54, 'PRATA', 'Colar', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Cl505',
        'Colar Flor de Lótus', 94.80, NULL, 'EM_ESTOQUE', 1);
-- Novos produtos para Fornecedor 2
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (55, 'BANHO_DOURADO', 'Anel', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'An506',
        'Anel Sol Brilhante', 42.70, NULL, 'EM_ESTOQUE', 2),
       (56, 'PRATA', 'Berloque', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bq507',
        'Berloque Pássaro Livre', 58.90, NULL, 'EM_ESTOQUE', 2),
       (57, 'BANHO_PRATA', 'Bracelete', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bc508',
        'Bracelete Ondas Marinhas', 78.40, NULL, 'EM_ESTOQUE', 2),
       (58, 'BANHO_DOURADO', 'Brinco', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Br509',
        'Brinco Espiral Dourada', 67.10, NULL, 'EM_ESTOQUE', 2),
       (59, 'PRATA', 'Colar', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Cl510',
        'Colar Âncora do Mar', 88.20, NULL, 'EM_ESTOQUE', 2);
-- Novos produtos para Fornecedor 3
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (60, 'PRATA', 'Anel', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'An511',
        'Anel Trança Celta', 68.30, NULL, 'EM_ESTOQUE', 3),
       (61, 'BANHO_PRATA', 'Berloque', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bq512',
        'Berloque Lua Cheia', 49.50, NULL, 'EM_ESTOQUE', 3),
       (62, 'PRATA', 'Bracelete', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bc513',
        'Bracelete Coração Gravado', 102.60, NULL, 'EM_ESTOQUE', 3),
       (63, 'DOURADO', 'Conjunto', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Cj514',
        'Conjunto Sol e Lua', 130.20, NULL, 'EM_ESTOQUE', 3),
       (64, 'ACO', 'Piercing', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Pirc515',
        'Piercing Círculo Aberto', 29.80, NULL, 'EM_ESTOQUE', 3);
-- Novos produtos para Fornecedor 4
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (65, 'BANHO_DOURADO', 'Anel', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'An516',
        'Anel Flor Aberta', 52.40, NULL, 'EM_ESTOQUE', 4),
       (66, 'PRATA', 'Berloque', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Bq517',
        'Berloque Concha Brilhante', 61.20, NULL, 'EM_ESTOQUE', 4),
       (67, 'BANHO_PRATA', 'Conjunto', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Cj518',
        'Conjunto Estrelas Cadentes', 85.70, NULL, 'EM_ESTOQUE', 4),
       (68, 'BANHO_DOURADO', 'Pingente', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Pg519',
        'Pingente Coração Alado', 48.90, NULL, 'EM_ESTOQUE', 4),
       (69, 'PRATA', 'Colar', '2025-09-09 09:23:00.000000', '2025-09-09 09:23:00.000000', NULL, 'Cl520',
        'Colar Pétalas Douradas', 79.60, NULL, 'EM_ESTOQUE', 4);
-- Adding more clients realistically over the months to support increasing sales
INSERT INTO clientes (id, cpf, data_atualizacao, data_criacao, descricao, email, nome, telefone)
VALUES (13, '404.040.404-04', '2025-04-15 10:00:00.000000', '2025-04-15 10:00:00.000000', '',
        'natalia.moraes@email.com', 'Natalia Moraes Silva', '(51) 97788-5544'),
       (14, '505.050.505-05', '2025-05-05 11:00:00.000000', '2025-05-05 11:00:00.000000', '',
        'otavio.souza@provedor.net', 'Otavio Souza Lima', '(61) 92233-4455'),
       (15, '606.060.606-06', '2025-05-15 12:00:00.000000', '2025-05-15 12:00:00.000000', '',
        'paula.fernandes@email.org', 'Paula Fernandes Rocha', '(71) 95566-7788'),
       (16, '707.070.707-07', '2025-06-05 13:00:00.000000', '2025-06-05 13:00:00.000000', '',
        'quintino.alves@emailserver.com', 'Quintino Alves Pinto', '(81) 94455-6677'),
       (17, '808.080.808-08', '2025-06-15 14:00:00.000000', '2025-06-15 14:00:00.000000', '', 'rafaela.castro@mail.com',
        'Rafaela Castro Gomes', '(91) 93344-5566'),
       (18, '909.090.909-09', '2025-07-05 15:00:00.000000', '2025-07-05 15:00:00.000000', '', 'sandro.victor@email.br',
        'Sandro Victor Borges', '(11) 96677-8899'),
       (19, '010.101.010-10', '2025-07-15 16:00:00.000000', '2025-07-15 16:00:00.000000', '',
        'tatiana.dias@provedor.com', 'Tatiana Dias Almeida', '(21) 97788-9900'),
       (20, '111.212.121-11', '2025-08-05 17:00:00.000000', '2025-08-05 17:00:00.000000', '',
        'ulisses.rodrigues@email.net', 'Ulisses Rodrigues Fernandes', '(31) 98899-0011'),
       (21, '212.121.212-12', '2025-08-15 18:00:00.000000', '2025-08-15 18:00:00.000000', '',
        'valeria.santos@mailservice.org', 'Valeria Santos Ribeiro', '(41) 99900-1122'),
       (22, '313.131.313-13', '2025-09-01 19:00:00.000000', '2025-09-01 19:00:00.000000', '',
        'wagner.pereira@email.com', 'Wagner Pereira Martins', '(51) 91122-2233');
-- Adding new products month by month, with restocking every 2 months (more products in Apr, Jun, Aug)
-- March additions (small addition)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (70, 'BANHO_PRATA', 'Anel', '2025-03-10 09:00:00.000000', '2025-03-10 09:00:00.000000', NULL, 'An701',
        'Anel Folha Simples', 36.20, NULL, 'EM_ESTOQUE', 1),
       (71, 'PRATA', 'Berloque', '2025-03-10 09:00:00.000000', '2025-03-10 09:00:00.000000', NULL, 'Bq702',
        'Berloque Chave Antiga', 52.30, NULL, 'EM_ESTOQUE', 2),
       (72, 'BANHO_DOURADO', 'Brinco', '2025-03-15 09:00:00.000000', '2025-03-15 09:00:00.000000', NULL, 'Br703',
        'Brinco Círculo Vazado', 58.40, NULL, 'EM_ESTOQUE', 3),
       (73, 'PRATA', 'Colar', '2025-03-20 09:00:00.000000', '2025-03-20 09:00:00.000000', NULL, 'Cl704',
        'Colar Pingente Redondo', 82.50, NULL, 'EM_ESTOQUE', 4);
-- April additions (restocking: more products)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (74, 'BANHO_PRATA', 'Bracelete', '2025-04-05 09:00:00.000000', '2025-04-05 09:00:00.000000', NULL, 'Bc705',
        'Bracelete Corrente Fina', 92.60, NULL, 'EM_ESTOQUE', 1),
       (75, 'PRATA', 'Conjunto', '2025-04-05 09:00:00.000000', '2025-04-05 09:00:00.000000', NULL, 'Cj706',
        'Conjunto Pérolas', 112.70, NULL, 'EM_ESTOQUE', 2),
       (76, 'ACO', 'Piercing', '2025-04-10 09:00:00.000000', '2025-04-10 09:00:00.000000', NULL, 'Pirc707',
        'Piercing Barra Curva', 26.80, NULL, 'EM_ESTOQUE', 3),
       (77, 'BANHO_DOURADO', 'Pingente', '2025-04-10 09:00:00.000000', '2025-04-10 09:00:00.000000', NULL, 'Pg708',
        'Pingente Cruz Simples', 46.90, NULL, 'EM_ESTOQUE', 4),
       (78, 'PRATA', 'Anel', '2025-04-15 09:00:00.000000', '2025-04-15 09:00:00.000000', NULL, 'An709',
        'Anel Onda Suave', 41.00, NULL, 'EM_ESTOQUE', 1),
       (79, 'BANHO_PRATA', 'Berloque', '2025-04-15 09:00:00.000000', '2025-04-15 09:00:00.000000', NULL, 'Bq710',
        'Berloque Borboleta', 54.10, NULL, 'EM_ESTOQUE', 2),
       (80, 'PRATA', 'Brinco', '2025-04-20 09:00:00.000000', '2025-04-20 09:00:00.000000', NULL, 'Br711',
        'Brinco Argola Fina', 64.20, NULL, 'EM_ESTOQUE', 3);
-- May additions (small)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (81, 'BANHO_DOURADO', 'Colar', '2025-05-05 09:00:00.000000', '2025-05-05 09:00:00.000000', NULL, 'Cl712',
        'Colar Corrente Longa', 76.30, NULL, 'EM_ESTOQUE', 4),
       (82, 'PRATA', 'Bracelete', '2025-05-10 09:00:00.000000', '2025-05-10 09:00:00.000000', NULL, 'Bc713',
        'Bracelete Pérola Única', 98.40, NULL, 'EM_ESTOQUE', 1),
       (83, 'BANHO_PRATA', 'Conjunto', '2025-05-15 09:00:00.000000', '2025-05-15 09:00:00.000000', NULL, 'Cj714',
        'Conjunto Flores', 118.50, NULL, 'EM_ESTOQUE', 2);
-- June additions (restocking: more)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (84, 'ACO', 'Piercing', '2025-06-05 09:00:00.000000', '2025-06-05 09:00:00.000000', NULL, 'Pirc715',
        'Piercing Estrela Pequena', 24.60, NULL, 'EM_ESTOQUE', 3),
       (85, 'BANHO_DOURADO', 'Pingente', '2025-06-05 09:00:00.000000', '2025-06-05 09:00:00.000000', NULL, 'Pg716',
        'Pingente Lua', 44.70, NULL, 'EM_ESTOQUE', 4),
       (86, 'PRATA', 'Anel', '2025-06-10 09:00:00.000000', '2025-06-10 09:00:00.000000', NULL, 'An717',
        'Anel Coração Simples', 38.80, NULL, 'EM_ESTOQUE', 1),
       (87, 'BANHO_PRATA', 'Berloque', '2025-06-10 09:00:00.000000', '2025-06-10 09:00:00.000000', NULL, 'Bq718',
        'Berloque Folha', 50.90, NULL, 'EM_ESTOQUE', 2),
       (88, 'PRATA', 'Brinco', '2025-06-15 09:00:00.000000', '2025-06-15 09:00:00.000000', NULL, 'Br719',
        'Brinco Pérola', 60.00, NULL, 'EM_ESTOQUE', 3),
       (89, 'BANHO_DOURADO', 'Colar', '2025-06-15 09:00:00.000000', '2025-06-15 09:00:00.000000', NULL, 'Cl720',
        'Colar Estrela', 80.10, NULL, 'EM_ESTOQUE', 4),
       (90, 'PRATA', 'Bracelete', '2025-06-20 09:00:00.000000', '2025-06-20 09:00:00.000000', NULL, 'Bc721',
        'Bracelete Trançado', 95.20, NULL, 'EM_ESTOQUE', 1);
-- July additions (small)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (91, 'BANHO_PRATA', 'Conjunto', '2025-07-05 09:00:00.000000', '2025-07-05 09:00:00.000000', NULL, 'Cj722',
        'Conjunto Círculos', 110.30, NULL, 'EM_ESTOQUE', 2),
       (92, 'ACO', 'Piercing', '2025-07-10 09:00:00.000000', '2025-07-10 09:00:00.000000', NULL, 'Pirc723',
        'Piercing Coração', 27.40, NULL, 'EM_ESTOQUE', 3),
       (93, 'BANHO_DOURADO', 'Pingente', '2025-07-15 09:00:00.000000', '2025-07-15 09:00:00.000000', NULL, 'Pg724',
        'Pingente Flor', 48.50, NULL, 'EM_ESTOQUE', 4);
-- August additions (restocking: more)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (94, 'PRATA', 'Anel', '2025-08-05 09:00:00.000000', '2025-08-05 09:00:00.000000', NULL, 'An725', 'Anel Pérola',
        42.60, NULL, 'EM_ESTOQUE', 1),
       (95, 'BANHO_PRATA', 'Berloque', '2025-08-05 09:00:00.000000', '2025-08-05 09:00:00.000000', NULL, 'Bq726',
        'Berloque Estrela', 56.70, NULL, 'EM_ESTOQUE', 2),
       (96, 'PRATA', 'Brinco', '2025-08-10 09:00:00.000000', '2025-08-10 09:00:00.000000', NULL, 'Br727', 'Brinco Lua',
        66.80, NULL, 'EM_ESTOQUE', 3),
       (97, 'BANHO_DOURADO', 'Colar', '2025-08-10 09:00:00.000000', '2025-08-10 09:00:00.000000', NULL, 'Cl728',
        'Colar Coração', 86.90, NULL, 'EM_ESTOQUE', 4),
       (98, 'PRATA', 'Bracelete', '2025-08-15 09:00:00.000000', '2025-08-15 09:00:00.000000', NULL, 'Bc729',
        'Bracelete Pérola Dupla', 102.00, NULL, 'EM_ESTOQUE', 1),
       (99, 'BANHO_PRATA', 'Conjunto', '2025-08-15 09:00:00.000000', '2025-08-15 09:00:00.000000', NULL, 'Cj730',
        'Conjunto Ondas', 122.10, NULL, 'EM_ESTOQUE', 2),
       (100, 'ACO', 'Piercing', '2025-08-20 09:00:00.000000', '2025-08-20 09:00:00.000000', NULL, 'Pirc731',
        'Piercing Flor', 30.20, NULL, 'EM_ESTOQUE', 3);
-- September additions (up to 9th, small)
INSERT INTO produtos (id, acabamento, categoria, data_atualizacao, data_criacao, data_venda, id_referencia, nome,
                      preco_custo, preco_venda, status, id_fornecedor)
VALUES (101, 'BANHO_DOURADO', 'Pingente', '2025-09-01 09:00:00.000000', '2025-09-01 09:00:00.000000', NULL, 'Pg732',
        'Pingente Borboleta', 52.30, NULL, 'EM_ESTOQUE', 4),
       (102, 'PRATA', 'Anel', '2025-09-05 09:00:00.000000', '2025-09-05 09:00:00.000000', NULL, 'An733',
        'Anel Trançado', 44.40, NULL, 'EM_ESTOQUE', 1);
-- Now, adding sales: consistent weekly sales, varying 2-5 per week, varying profits (lucro = preco_venda - preco_custo, varying markup 1.5x-3x cost)
-- Update produtos with data_venda, preco_venda, status='VENDIDO' for sold items
-- February sales (starting mid-month, 3 weeks: ~3 sales/week)
UPDATE produtos
SET data_venda='2025-02-12 15:00:00.000000',
    preco_venda=90.20,
    status='VENDIDO'
WHERE id = 2;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-02-12 15:00:00.000000', '2025-02-28', 'CARTAO_CREDITO', 45.10, 1, 90.20, 'PAGO', 1, 2, 2);
UPDATE produtos
SET data_venda='2025-02-14 16:00:00.000000',
    preco_venda=131.20,
    status='VENDIDO'
WHERE id = 3;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-02-14 16:00:00.000000', '2025-02-28', 'DINHEIRO', 65.60, 1, 131.20, 'PAGO', 1, 3, 3);
UPDATE produtos
SET data_venda='2025-02-17 14:00:00.000000',
    preco_venda=100.20,
    status='VENDIDO'
WHERE id = 4;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-02-17 14:00:00.000000', '2025-03-10', 'PIX', 50.10, 1, 100.20, 'PAGO', 1, 4, 4);
UPDATE produtos
SET data_venda='2025-02-28 16:00:00.000000',
    preco_venda=68.20,
    status='VENDIDO'
WHERE id = 11;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-02-28 16:00:00.000000', '2025-03-10', 'PIX', 34.10, 1, 68.20, 'PAGO', 1, 3, 11);
-- March sales (4 weeks, varying 2-4 sales/week, profits vary)
UPDATE produtos
SET data_venda='2025-03-10 14:00:00.000000',
    preco_venda=96.00,
    status='VENDIDO'
WHERE id = 14;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-10 14:00:00.000000', '2025-03-31', 'DINHEIRO', 48.00, 1, 96.00, 'PAGO', 1, 6, 14);
UPDATE produtos
SET data_venda='2025-03-17 14:00:00.000000',
    preco_venda=90.20,
    status='VENDIDO'
WHERE id = 17;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-17 14:00:00.000000', '2025-03-31', 'DINHEIRO', 45.10, 1, 90.20, 'PAGO', 1, 9, 17);
UPDATE produtos
SET data_venda='2025-03-19 15:00:00.000000',
    preco_venda=221.20,
    status='VENDIDO'
WHERE id = 18;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-19 15:00:00.000000', '2025-03-31', 'CARTAO_DEBITO', 110.60, 1, 221.20, 'PAGO', 1, 10, 18);
UPDATE produtos
SET data_venda='2025-03-24 14:00:00.000000',
    preco_venda=81.20,
    status='VENDIDO'
WHERE id = 20;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-24 14:00:00.000000', '2025-03-31', 'CARTAO_CREDITO', 40.60, 2, 81.20, 'PAGO', 2, 12, 20);
UPDATE produtos
SET data_venda='2025-03-26 15:00:00.000000',
    preco_venda=110.20,
    status='VENDIDO'
WHERE id = 21;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-26 15:00:00.000000', '2025-03-31', 'DINHEIRO', 55.10, 1, 110.20, 'PAGO', 1, 1, 21);
UPDATE produtos
SET data_venda='2025-03-28 16:00:00.000000',
    preco_venda=160.00,
    status='VENDIDO'
WHERE id = 22;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-03-28 16:00:00.000000', '2025-03-31', 'PIX', 80.00, 1, 160.00, 'PAGO', 1, 2, 22);
-- April sales (increasing turnover, 4 weeks, 3-5 sales/week)
UPDATE produtos
SET data_venda='2025-04-02 15:00:00.000000',
    preco_venda=156.20,
    status='VENDIDO'
WHERE id = 24;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-02 15:00:00.000000', '2025-04-30', 'PIX', 78.10, 1, 156.20, 'PAGO', 1, 4, 24);
UPDATE produtos
SET data_venda='2025-04-04 16:00:00.000000',
    preco_venda=150.00,
    status='VENDIDO'
WHERE id = 25;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-04 16:00:00.000000', '2025-04-30', 'DINHEIRO', 75.00, 1, 150.00, 'PAGO', 1, 5, 25);
UPDATE produtos
SET data_venda='2025-04-18 16:00:00.000000',
    preco_venda=85.20,
    status='VENDIDO'
WHERE id = 31;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-18 16:00:00.000000', '2025-04-30', 'CARTAO_DEBITO', 42.60, 1, 85.20, 'PAGO', 1, 11, 31);
UPDATE produtos
SET data_venda='2025-04-25 16:00:00.000000',
    preco_venda=185.20,
    status='VENDIDO'
WHERE id = 34;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-25 16:00:00.000000', '2025-04-30', 'DINHEIRO', 92.60, 1, 185.20, 'PAGO', 1, 1, 34);
UPDATE produtos
SET data_venda='2025-04-28 14:00:00.000000',
    preco_venda=100.20,
    status='VENDIDO'
WHERE id = 35;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-28 14:00:00.000000', '2025-04-30', 'PIX', 50.10, 1, 100.20, 'PAGO', 1, 2, 35);
UPDATE produtos
SET data_venda='2025-04-30 15:00:00.000000',
    preco_venda=241.20,
    status='VENDIDO'
WHERE id = 36;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-04-30 15:00:00.000000', '2025-05-15', 'CARTAO_CREDITO', 120.60, 2, 241.20, 'PAGO', 2, 3, 36);
-- May sales (4 weeks, 4-5 sales/week, varying profits)
UPDATE produtos
SET data_venda='2025-05-12 14:00:00.000000',
    preco_venda=81.20,
    status='VENDIDO'
WHERE id = 41;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-12 14:00:00.000000', '2025-05-31', 'CARTAO_CREDITO', 40.60, 2, 81.20, 'PAGO', 2, 8, 41);
UPDATE produtos
SET data_venda='2025-05-16 16:00:00.000000',
    preco_venda=90.20,
    status='VENDIDO'
WHERE id = 43;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-16 16:00:00.000000', '2025-05-31', 'PIX', 45.10, 1, 90.20, 'PAGO', 1, 10, 43);
UPDATE produtos
SET data_venda='2025-05-21 15:00:00.000000',
    preco_venda=70.20,
    status='VENDIDO'
WHERE id = 45;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-21 15:00:00.000000', '2025-05-31', 'PIX', 35.10, 1, 70.20, 'PAGO', 1, 12, 45);
UPDATE produtos
SET data_venda='2025-05-23 16:00:00.000000',
    preco_venda=66.00,
    status='VENDIDO'
WHERE id = 46;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-23 16:00:00.000000', '2025-05-31', 'DINHEIRO', 33.00, 1, 66.00, 'PAGO', 1, 13, 46);
UPDATE produtos
SET data_venda='2025-05-26 14:00:00.000000',
    preco_venda=1696.00,
    status='VENDIDO'
WHERE id = 47;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-26 14:00:00.000000', '2025-06-15', 'CARTAO_CREDITO', 848.00, 4, 1696.00, 'PAGO', 4, 14, 47);
UPDATE produtos
SET data_venda='2025-05-30 16:00:00.000000',
    preco_venda=153.20,
    status='VENDIDO'
WHERE id = 49;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-05-30 16:00:00.000000', '2025-05-31', 'CARTAO_DEBITO', 76.60, 1, 153.20, 'PAGO', 1, 1, 49);
-- June sales (4 weeks, 4-6 sales/week)
UPDATE produtos
SET data_venda='2025-06-02 14:00:00.000000',
    preco_venda=79.00,
    status='VENDIDO'
WHERE id = 50;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-02 14:00:00.000000', '2025-06-30', 'PIX', 39.50, 1, 79.00, 'PAGO', 1, 2, 50);
UPDATE produtos
SET data_venda='2025-06-06 16:00:00.000000',
    preco_venda=179.80,
    status='VENDIDO'
WHERE id = 52;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-06 16:00:00.000000', '2025-06-30', 'CARTAO_CREDITO', 89.90, 2, 179.80, 'PAGO', 2, 4, 52);
UPDATE produtos
SET data_venda='2025-06-11 15:00:00.000000',
    preco_venda=189.60,
    status='VENDIDO'
WHERE id = 54;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-11 15:00:00.000000', '2025-06-30', 'CARTAO_DEBITO', 94.80, 1, 189.60, 'PAGO', 1, 6, 54);
UPDATE produtos
SET data_venda='2025-06-13 16:00:00.000000',
    preco_venda=85.40,
    status='VENDIDO'
WHERE id = 55;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-13 16:00:00.000000', '2025-06-30', 'DINHEIRO', 42.70, 1, 85.40, 'PAGO', 1, 7, 55);
UPDATE produtos
SET data_venda='2025-06-16 14:00:00.000000',
    preco_venda=117.80,
    status='VENDIDO'
WHERE id = 56;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-16 14:00:00.000000', '2025-06-30', 'PIX', 58.90, 1, 117.80, 'PAGO', 1, 8, 56);
UPDATE produtos
SET data_venda='2025-06-27 16:00:00.000000',
    preco_venda=99.00,
    status='VENDIDO'
WHERE id = 61;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-27 16:00:00.000000', '2025-06-30', 'PIX', 49.50, 1, 99.00, 'PAGO', 1, 13, 61);
UPDATE produtos
SET data_venda='2025-06-30 14:00:00.000000',
    preco_venda=205.20,
    status='VENDIDO'
WHERE id = 62;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-06-30 14:00:00.000000', '2025-07-15', 'CARTAO_CREDITO', 102.60, 2, 205.20, 'PAGO', 2, 14, 62);
-- July sales (5 weeks, 3-5 sales/week)
UPDATE produtos
SET data_venda='2025-07-11 16:00:00.000000',
    preco_venda=171.40,
    status='VENDIDO'
WHERE id = 67;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-11 16:00:00.000000', '2025-07-31', 'CARTAO_CREDITO', 85.70, 2, 171.40, 'PAGO', 2, 19, 67);
UPDATE produtos
SET data_venda='2025-07-14 14:00:00.000000',
    preco_venda=97.80,
    status='VENDIDO'
WHERE id = 68;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-14 14:00:00.000000', '2025-07-31', 'DINHEIRO', 48.90, 1, 97.80, 'PAGO', 1, 1, 68);
UPDATE produtos
SET data_venda='2025-07-18 16:00:00.000000',
    preco_venda=72.40,
    status='VENDIDO'
WHERE id = 70;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-18 16:00:00.000000', '2025-07-31', 'CARTAO_DEBITO', 36.20, 1, 72.40, 'PAGO', 1, 3, 70);
UPDATE produtos
SET data_venda='2025-07-21 14:00:00.000000',
    preco_venda=104.60,
    status='VENDIDO'
WHERE id = 71;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-21 14:00:00.000000', '2025-07-31', 'PIX', 52.30, 1, 104.60, 'PAGO', 1, 4, 71);
UPDATE produtos
SET data_venda='2025-07-23 15:00:00.000000',
    preco_venda=116.80,
    status='VENDIDO'
WHERE id = 72;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-23 15:00:00.000000', '2025-07-31', 'DINHEIRO', 58.40, 1, 116.80, 'PAGO', 1, 5, 72);
UPDATE produtos
SET data_venda='2025-07-28 14:00:00.000000',
    preco_venda=185.20,
    status='VENDIDO'
WHERE id = 74;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-07-28 14:00:00.000000', '2025-07-31', 'PIX', 92.60, 1, 185.20, 'PAGO', 1, 7, 74);
-- August sales (4 weeks, 4-6 sales/week)
UPDATE produtos
SET data_venda='2025-08-04 14:00:00.000000',
    preco_venda=93.80,
    status='VENDIDO'
WHERE id = 77;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-08-04 14:00:00.000000', '2025-08-31', 'PIX', 46.90, 1, 93.80, 'PAGO', 1, 10, 77);
UPDATE produtos
SET data_venda='2025-08-06 15:00:00.000000',
    preco_venda=82.00,
    status='VENDIDO'
WHERE id = 78;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-08-06 15:00:00.000000', '2025-08-31', 'CARTAO_CREDITO', 41.00, 2, 82.00, 'PAGO', 2, 11, 78);
UPDATE produtos
SET data_venda='2025-08-08 16:00:00.000000',
    preco_venda=108.20,
    status='VENDIDO'
WHERE id = 79;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-08-08 16:00:00.000000', '2025-08-31', 'PIX', 54.10, 1, 108.20, 'PAGO', 1, 12, 79);
UPDATE produtos
SET data_venda='2025-08-25 14:00:00.000000',
    preco_venda=77.60,
    status='VENDIDO'
WHERE id = 86;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-08-25 14:00:00.000000', '2025-08-31', 'CARTAO_DEBITO', 38.80, 1, 77.60, 'PAGO', 1, 19, 86);
UPDATE produtos
SET data_venda='2025-08-27 15:00:00.000000',
    preco_venda=101.80,
    status='VENDIDO'
WHERE id = 87;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-08-27 15:00:00.000000', '2025-08-31', 'DINHEIRO', 50.90, 1, 101.80, 'PAGO', 1, 20, 87);
-- September sales (up to 9th, partial week, 2 sales before the one provided)
UPDATE produtos
SET data_venda='2025-09-06 15:00:00.000000',
    preco_venda=190.40,
    status='VENDIDO'
WHERE id = 90;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-09-07 15:00:00.000000', '2025-09-30', 'PIX', 95.20, 1, 190.40, 'PAGO', 1, 1, 90);
UPDATE produtos
SET data_venda='2025-09-07 16:00:00.000000',
    preco_venda=220.60,
    status='VENDIDO'
WHERE id = 91;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-09-06 16:00:00.000000', '2025-09-30', 'DINHEIRO', 110.30, 1, 220.60, 'PAGO', 1, 2, 91);
UPDATE produtos
SET data_venda='2025-09-08 14:00:00.000000',
    preco_venda=54.80,
    status='VENDIDO'
WHERE id = 92;
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-09-08 14:00:00.000000', '2025-09-30', 'CARTAO_DEBITO', 27.40, 1, 54.80, 'PAGO', 1, 3, 92);
INSERT INTO vendas (data_criacao, data_vencimento, forma_pagamento, lucro, parcelas_pagas, preco_venda, status,
                    total_parcelas, cliente_id, produto_id)
VALUES ('2025-09-09 10:40:22.838519', '2025-09-30', 'CARTAO_DEBITO', 85.00, 2, 120.00, 'PAGO', 2, 2, 5);