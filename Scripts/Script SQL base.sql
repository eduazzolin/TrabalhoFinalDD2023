
-- Criando banco de dados:
DROP DATABASE IF EXISTS SISTEMA_FARMACIA;
CREATE DATABASE SISTEMA_FARMACIA;
USE SISTEMA_FARMACIA;


-- Criando tabelas:
CREATE TABLE PRODUTO (
	ID_PRODUTO INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NOME VARCHAR(255) NOT NULL,
	DESCRICAO VARCHAR(255), 
	EAN VARCHAR(16) UNIQUE NOT NULL,
	VALOR DECIMAL(12,2) NOT NULL,
	ESTOQUE INT NOT NULL
);

CREATE TABLE VENDA (
	ID_VENDA INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DATA_VENDA DATETIME NOT NULL
);

CREATE TABLE ITEM_VENDA (
	ID_ITEM_VENDA INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ID_VENDA INT NOT NULL,
	ID_PRODUTO INT NOT NULL,
	QTDE_PRODUTO INT NOT NULL,
	VALOR_UNITARIO DECIMAL(12,2) NOT NULL,
	FOREIGN KEY (ID_VENDA) REFERENCES VENDA (ID_VENDA),
	FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO (ID_PRODUTO)
);


-- Inserts de PRODUTO:
INSERT INTO PRODUTO (NOME, DESCRICAO, EAN, VALOR, ESTOQUE) VALUES ('DIPIRONA MONOIDRATADA 500MG 10 COMPRIMIDOS EMS GENÉRICO', 'A Dipirona Sódica é indicada como analgésico e antitérmico. Utilizada também em estados febris e dolorosos.', 59568, 6.95, 500);
INSERT INTO PRODUTO (NOME, DESCRICAO, EAN, VALOR, ESTOQUE) VALUES ('BENEGRIP DIPIRONA MONOIDRATADA 500MG + MALEATO DE CLORFENIRAMINA 2MG + CAFEÍNA 30MG 20 COMPRIMIDOS', 'Benegrip é destinado para alívio dos sintomas decorrentes das gripes e resfriados, como dores de cabeça, febre e processos alérgicos.', 27896094904686, 25.89, 254);
INSERT INTO PRODUTO (NOME, DESCRICAO, EAN, VALOR, ESTOQUE) VALUES ('CONDICIONADOR L''ORÉAL PARIS ELSEVE REPARAÇÃO TOTAL 5+ 400ML', 'Condicionador para cabelos danificados ideal para reparar, revitalizar e reforçar os fios.', 7899026459908, 22.99, 105);
INSERT INTO PRODUTO (NOME, DESCRICAO, EAN, VALOR, ESTOQUE) VALUES ('SUPLEMENTO VITAMÍNICO-MINERAL CENTRUM ADULTO A A ZINCO - 150 COMPRIMIDOS', 'Suplemento vitamínico completo de A a Zinco. Contribui para melhorar a energia e a imunidade.', 7891045042741, 167.93, 800);

-- Inserts de VENDA:
INSERT INTO VENDA (DATA_VENDA) VALUES ('2023-01-15');
INSERT INTO VENDA (DATA_VENDA) VALUES ('2023-03-01');
INSERT INTO VENDA (DATA_VENDA) VALUES ('2023-05-20');

-- Inserts de ITEM_VENDA:
INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (1, 2, 1, 25.89);
INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (1, 4, 2, 167.93);
INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (2, 1, 5, 6.95);
INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (2, 3, 2, 22.99);
INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (3, 3, 1, 167.93);


-- View de produtos das vendas:
CREATE OR REPLACE VIEW VW_LISTA_PRODUTOS_POR_VENDA AS
SELECT 
	VENDA.ID_VENDA,
	VENDA.DATA_VENDA,
	PRODUTO.ID_PRODUTO,
	PRODUTO.NOME,
	ITEM_VENDA.QTDE_PRODUTO,
	ITEM_VENDA.VALOR_UNITARIO,
	ITEM_VENDA.QTDE_PRODUTO * ITEM_VENDA.VALOR_UNITARIO AS VALOR_TOTAL_PRODUTO
FROM VENDA
LEFT JOIN ITEM_VENDA ON
	ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA 
LEFT JOIN PRODUTO ON
	ITEM_VENDA.ID_PRODUTO = PRODUTO.ID_PRODUTO
ORDER BY 2 DESC;
select * from VW_LISTA_PRODUTOS_POR_VENDA; 

-- View de valor total e quantidade de produtos por vendas:
CREATE OR REPLACE VIEW VW_VENDA_COM_VALOR_TOTAL_E_QTDE AS
SELECT 
	VENDA.ID_VENDA ,
	VENDA.DATA_VENDA,
	SUM(ITEM_VENDA.QTDE_PRODUTO) AS QTDE_PRODUTOS,
	SUM(ITEM_VENDA.QTDE_PRODUTO * ITEM_VENDA.VALOR_UNITARIO) AS VALOR_TOTAL
FROM VENDA
LEFT JOIN ITEM_VENDA ON
	ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA 
GROUP BY VENDA.ID_VENDA
ORDER BY VENDA.DATA_VENDA DESC;
select * from VW_VENDA_COM_VALOR_TOTAL_E_QTDE; 




