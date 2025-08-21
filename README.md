Gleam - Sistema de Gestão de Joalheria (Backend)
📖 Sobre o Projeto
Gleam é o backend de um sistema de gestão de stock e vendas (ERP) desenhado especificamente para o nicho de joalherias. A aplicação foi construída em Java utilizando o framework Spring Boot e segue uma arquitetura RESTful para se comunicar com qualquer interface de frontend.

O core do sistema é a sua lógica de inventário: cada peça é tratada como um item único. Quando uma venda é registada, o produto é atomicamente movido do stock ativo para um registo histórico de vendas, garantindo a integridade e a rastreabilidade dos dados.

✨ Funcionalidades Principais
Gestão de Fornecedores: CRUD completo para registar e gerir fornecedores de joias.

Gestão de Clientes: CRUD básico para manter um registo de clientes.

Gestão de Produtos (Inventário):

Registo de produtos únicos com detalhes como preço de custo, preço de venda, acabamento e categoria.

Lógica de "Mover na Venda": os produtos vendidos são removidos do inventário ativo.

Registo de Vendas:

Criação de "recibos" de venda (RegistrarVenda) que podem conter múltiplos itens.

Cálculo automático do lucro para cada item vendido.

Armazenamento de detalhes da transação, como nome do cliente, forma de pagamento e situação.

🛠️ Tecnologias Utilizadas
Linguagem: Java 21+

Framework: Spring Boot 3

Acesso a Dados: Spring Data JPA, Hibernate

Base de Dados: MySQL 8

Gestão de Dependências: Maven

Utilitários: Lombok
