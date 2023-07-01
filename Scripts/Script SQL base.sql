-- ----------------------------------------------------------------------------
-- Criando banco de dados:
DROP DATABASE IF EXISTS SISTEMA_FARMACIA;
CREATE DATABASE SISTEMA_FARMACIA;
USE SISTEMA_FARMACIA;

-- ----------------------------------------------------------------------------
-- Criando tabelas:
CREATE TABLE PRODUTO (
	ID_PRODUTO INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NOME VARCHAR(255) NOT NULL,
	DESCRICAO VARCHAR(255), 
	EAN VARCHAR(16) UNIQUE NOT NULL,
	VALOR DECIMAL(12,2) NOT NULL,
	ESTOQUE INT NOT NULL,
	ATIVO BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE VENDA (
	ID_VENDA INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DATA_VENDA DATETIME NOT NULL,
	VALOR_TOTAL DECIMAL (12,2) NOT NULL,
	QTDE_ITENS INT NOT NULL
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

-- ----------------------------------------------------------------------------
-- Inserindo exemplos:
-- Inserts de PRODUTO:
INSERT INTO `produto` VALUES (1,'DIPIRONA MONOIDRATADA 500MG 10 COMPRIMIDOS EMS GENÉRICO','A DIPIRONA SÓDICA É INDICADA COMO ANALGÉSICO E ANTITÉRMICO. UTILIZADA TAMBÉM EM ESTADOS FEBRIS E DOLOROSOS.','59568',6.95,500,1);
INSERT INTO `produto` VALUES (2,'BENEGRIP DIPIRONA MONOIDRATADA 500MG + MALEATO DE CLORFENIRAMINA 2MG + CAFEÍNA 30MG 20 COMPRIMIDOS','BENEGRIP É DESTINADO PARA ALÍVIO DOS SINTOMAS DECORRENTES DAS GRIPES E RESFRIADOS, COMO DORES DE CABEÇA, FEBRE E PROCESSOS ALÉRGICOS.','27896094904686',25.89,254,1);
INSERT INTO `produto` VALUES (3,'CONDICIONADOR L\'ORÉAL PARIS ELSEVE REPARAÇÃO TOTAL 5+ 400ML','CONDICIONADOR PARA CABELOS DANIFICADOS IDEAL PARA REPARAR, REVITALIZAR E REFORÇAR OS FIOS.','7899026459908',22.99,105,1);
INSERT INTO `produto` VALUES (4,'SUPLEMENTO VITAMÍNICO-MINERAL CENTRUM ADULTO A A ZINCO - 150 COMPRIMIDOS','SUPLEMENTO VITAMÍNICO COMPLETO DE A A ZINCO. CONTRIBUI PARA MELHORAR A ENERGIA E A IMUNIDADE.','7891045042741',167.93,1800,0);
INSERT INTO `produto` VALUES (5,'PASTA DE DENTE COLGATE TOTAL 12 GENGIVA REFORÇADA COM FLÚOR DE 140G','NOVO CREME DENTAL COLGATE TOTAL 12 GENGIVA REFORÇADA, COM FÓRMULA AVANÇADA E ANTIBACTERIANA, GARANTE ATÉ 12 HORAS DE DEFESA ATIVA QUE REFORÇA SUA GENGIVA CONTRAS AS BACTÉRIAS COM O USO CONTÍNUO.','7891024033289',12.90,1200,1);
INSERT INTO `produto` VALUES (6,'DICLOFENACO DIETILAMÔNIO NEO QUÍMICA GEL CREME COM 60G','O DICLOFENACO DIETILAMÔNIO ALIVIA A DOR E REDUZ OS SINTOMAS DA INFLAMAÇÃO . NAS SEGUINTES CONDIÇÕES: ENTORSES, LESÕES, CONTUSÕES, DISTENSÕES, TORCICOLO, DORES NAS COSTAS, DORES MUSCULARES, DOR PÓS-TRAUMÁTICA, LESÕES CAUSADAS PELA PRÁTICA ESPORTIVA;','7896714204413',10.06,50,1);
INSERT INTO `produto` VALUES (7,'CEFALIV MESILATO DE DI-HIDROERGOTAMINA 1MG + DIPIRONA MONOIDRATADA 350MG + CAFEÍNA 100MG 12 COMPRIMIDOS','CEFALIV É DESTINADO AO TRATAMENTO DAS CRISES DE DOR DE CABEÇA (CEFALÉIA) INCLUINDO A ENXAQUECA.','7896658001079',19.29,77,1);
INSERT INTO `produto` VALUES (8,'CLORIDRATO DE NARATRIPTANA 2,5MG 20 COMPRIMIDOS NOVA QUÍMICA GENÉRICO','O CLORIDRATO DE NARATRIPTANA É CONTRAINDICADO A PESSOAS COM CONHECIDA HIPERSENSIBILIDADE (ALERGIA) À NARATRIPTANA OU A QUALQUER COMPONENTE DA FÓRMULA.','7895296448932',48.59,84,1);
INSERT INTO `produto` VALUES (9,'SAL DE FRUTA ENO TRADICIONAL 100G','ENO SAL DE FRUTA É UM ANTIÁCIDO INDICADO PARA AUXILIAR NA AZIA, MÁ DIGESTÃO E OUTROS TRANSTORNOS ESTOMACAIS, TAIS COMO EXCESSO DE ACIDEZ DO ESTÔMAGO E INDIGESTÃO ÁCIDA. ','7896015560300',27.39,7854,1);
INSERT INTO `produto` VALUES (10,'MALEATO DE DEXCLOFENIRAMINA 0,4MG/ML SOLUÇÃO ORAL 120ML NEO QUÍMICA GENÉRICO','O MALEATO DE DEXCLORFENIRAMINA É UM ANTI-HISTAMÍNICO (ANTIALÉRGICO), POR ISSO AJUDA A REDUZIR OS SINTOMAS DA ALERGIA, PREVENINDO OS EFEITOS DA HISTAMINA, QUE É UMA SUBSTÂNCIA PRODUZIDA PELO PRÓPRIO ORGANISMO.','7896714203645',12.69,511,1);
INSERT INTO `produto` VALUES (11,'VICK VAPORUB DESCONGESTIONANTE POMADA 12G','VICK VAPORUB® É DESTINADO AO ALÍVIO DA TOSSE E DO MAL-ESTAR MUSCULAR QUE ACOMPANHAM GRIPES E RESFRIADOS, ALÉM DA CONGESTÃO NASAL.','78911222',13.49,0,1);
INSERT INTO `produto` VALUES (12,'VICK INALADOR 0,5G','INALADOR VICK AJUDA A DESENTUPIR O NARIZ E RESPIRAR LIVREMENTE.','78911239',16.09,200,1);
INSERT INTO `produto` VALUES (13,'PARACETAMOL 750MG C/ 20CP GENÉRICO PRATI','SE VOCÊ BUSCA UM MEDICAMENTO EFICAZ PARA A REDUÇÃO DA FEBRE E DO ALÍVIO TEMPORÁRIO DE DORES LEVES A MODERADAS ASSOCIADAS A RESFRIADOS, DORES DE CABEÇA, DORES NO CORPO OU DORES DE DENTE.','78978975',5.99,452,1);
INSERT INTO `produto` VALUES (14,'LOÇÃO HIDRATANTE DOVE MÃOS COCO 75ML','LOÇÃO HIDRATANTE DOVE MÃOS COCO 75ML','4465746',11.99,87,1);
INSERT INTO `produto` VALUES (15,'FIO DENTAL JOHNSON\'S REACH ESSENCIAL SABOR MENTA 100 METROS','O FIO DENTAL JOHNSON\'S REACH ESSENCIAL FOI CRIADO PARA SER O ALIADO IDEAL DURANTE A ESCOVAÇÃO DIÁRIA. PERFEITO PARA AJUDAR NA REMOÇÃO DA PLACA BACTERIANA AO MESMO TEMPO EM QUE OFERECE UM REFRESCANTE SABOR DE MENTA.','7891010501105',11.49,9885,1);
INSERT INTO `produto` VALUES (16,'HASTES FLEXÍVEIS COTONETES 150 UNIDADES','OS COTONETES JOHNSON & JOHNSON SÃO HASTES FLEXÍVEIS DESENVOLVIDAS PARA LIMPEZA E HIGIENIZAÇÃO DE PARTES MAIS SENSÍVEIS DO CORPO. ELES SÃO SEGUROS E INQUEBRÁVEIS, PROPORCIONANDO MAIS SEGURANÇA','7891010560812',10.89,787,1);
INSERT INTO `produto` VALUES (17,'PROTETOR LABIAL BEPANTOL DERMA FPS 50 4,5G','O BEPANTOL DERMA É UM PROTETOR LABIAL DE USO DIÁRIO, QUE PROPORCIONA HIDRATAÇÃO INTENSA E PROLONGADA COM PROTEÇÃO CONTRA OS RAIOS UV.','7891106914277',38.98,0,1);
INSERT INTO `produto` VALUES (18,'KIT SABONETE EM BARRA NIVEA CREME CARE COM 6 UNIDADES DE 90G CADA','O SABONETE EM BARRA NIVEA CREME CARE POSSUI EXCLUSIVA FÓRMULA COM INGREDIENTES SELECIONADOS QUE ENVOLVEM A SUA PELE COM UMA ESPUMA DE FRAGRÂNCIA ÚNICA, PROPORCIONANDO SENSAÇÃO DE PELE MACIA E HIDRATADA POR MUITO MAIS TEMPO.','4005900701367',20.69,2,1);



-- Inserts de VENDA:
INSERT INTO `venda` VALUES (1,'2023-01-15 00:00:00',361.75,3);
INSERT INTO `venda` VALUES (2,'2023-01-15 00:00:00',80.73,7);
INSERT INTO `venda` VALUES (3,'2023-01-15 00:00:00',167.93,1);
INSERT INTO `venda` VALUES (4,'2023-07-01 10:40:50',305.80,44);
INSERT INTO `venda` VALUES (5,'2023-07-01 10:41:59',638.44,25);
INSERT INTO `venda` VALUES (6,'2023-07-01 10:42:04',38.70,3);
INSERT INTO `venda` VALUES (7,'2023-07-01 10:42:21',38.58,2);
INSERT INTO `venda` VALUES (8,'2023-07-01 10:42:28',543.69,21);
INSERT INTO `venda` VALUES (9,'2023-07-01 10:42:35',27.39,1);
INSERT INTO `venda` VALUES (10,'2023-07-01 10:42:55',1038.84,58);
INSERT INTO `venda` VALUES (11,'2023-07-01 10:43:00',10.06,1);
INSERT INTO `venda` VALUES (12,'2023-07-01 10:43:13',25.28,2);
INSERT INTO `venda` VALUES (13,'2023-07-01 10:43:22',10.89,1);
INSERT INTO `venda` VALUES (14,'2023-07-01 10:43:34',20.69,1);
INSERT INTO `venda` VALUES (15,'2023-07-01 10:43:48',20.69,1);
INSERT INTO `venda` VALUES (16,'2023-07-01 10:44:15',11.49,1);
INSERT INTO `venda` VALUES (17,'2023-07-01 10:44:24',48.59,1);

-- Inserts de ITEM_VENDA:
INSERT INTO `item_venda` VALUES (1,1,2,1,25.89);
INSERT INTO `item_venda` VALUES (2,1,4,2,167.93);
INSERT INTO `item_venda` VALUES (3,2,1,5,6.95);
INSERT INTO `item_venda` VALUES (4,2,3,2,22.99);
INSERT INTO `item_venda` VALUES (5,3,3,1,167.93);
INSERT INTO `item_venda` VALUES (6,4,1,22,6.95);
INSERT INTO `item_venda` VALUES (7,4,1,22,6.95);
INSERT INTO `item_venda` VALUES (8,5,6,1,10.06);
INSERT INTO `item_venda` VALUES (9,5,5,2,12.90);
INSERT INTO `item_venda` VALUES (10,5,9,22,27.39);
INSERT INTO `item_venda` VALUES (11,6,5,3,12.90);
INSERT INTO `item_venda` VALUES (12,7,7,2,19.29);
INSERT INTO `item_venda` VALUES (13,8,2,21,25.89);
INSERT INTO `item_venda` VALUES (14,9,9,1,27.39);
INSERT INTO `item_venda` VALUES (15,10,9,22,27.39);
INSERT INTO `item_venda` VALUES (16,10,16,2,10.89);
INSERT INTO `item_venda` VALUES (17,10,16,12,10.89);
INSERT INTO `item_venda` VALUES (18,10,5,22,12.90);
INSERT INTO `item_venda` VALUES (19,11,6,1,10.06);
INSERT INTO `item_venda` VALUES (20,12,7,1,19.29);
INSERT INTO `item_venda` VALUES (21,12,13,1,5.99);
INSERT INTO `item_venda` VALUES (22,13,16,1,10.89);
INSERT INTO `item_venda` VALUES (23,14,18,1,20.69);
INSERT INTO `item_venda` VALUES (24,15,18,1,20.69);
INSERT INTO `item_venda` VALUES (25,16,15,1,11.49);
INSERT INTO `item_venda` VALUES (26,17,8,1,48.59);

-- ----------------------------------------------------------------------------
-- Criando views: *somente para visualização no banco de dados. Não foram usadas no código.
-- View de produtos das vendas:
CREATE OR REPLACE VIEW VW_LISTA_PRODUTOS_POR_VENDA AS
SELECT 
	VENDA.ID_VENDA,
	VENDA.DATA_VENDA,
	PRODUTO.ID_PRODUTO,
	PRODUTO.NOME,
    PRODUTO.EAN,
	ITEM_VENDA.QTDE_PRODUTO,
	ITEM_VENDA.VALOR_UNITARIO,
	(ITEM_VENDA.QTDE_PRODUTO * ITEM_VENDA.VALOR_UNITARIO) VALOR_TOTAL_PRODUTO,
	VENDA.QTDE_ITENS AS QTD_ITENS_TOTAL_VENDA,
	VENDA.VALOR_TOTAL AS VALOR_TOTAL_VENDA
FROM VENDA
LEFT JOIN ITEM_VENDA ON
	ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA 
LEFT JOIN PRODUTO ON
	ITEM_VENDA.ID_PRODUTO = PRODUTO.ID_PRODUTO
ORDER BY 2 DESC;

-- ----------------------------------------------------------------------------
-- Criando selects de exemplo:
select * from venda;
select * from item_venda;
select * from produto;
select * from VW_LISTA_PRODUTOS_POR_VENDA;