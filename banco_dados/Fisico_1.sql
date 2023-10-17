/* Fisico_1: */

CREATE TABLE PacoteViagem (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    data_ida DATE,
    data_volta DATE,
    preco NUMERIC(8,2),
    num_max_prestacoes INTEGER,
    fk_Destino_id INTEGER
);
ALTER TABLE PacoteViagem ENGINE = InnoDB; 

CREATE TABLE Cliente (
    cpf CHAR(11) PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255)
);
ALTER TABLE Cliente ENGINE = InnoDB;

CREATE TABLE Imagem (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    url TEXT,
    fk_Destino_id INTEGER
);
ALTER TABLE Imagem ENGINE = InnoDB;

CREATE TABLE Destino (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255)
);
ALTER TABLE Destino ENGINE = InnoDB;

CREATE TABLE Venda (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    num_prestacoes INTEGER,
    fk_Cliente_cpf CHAR(11),
    fk_PacoteViagem_id INTEGER
);
ALTER TABLE Venda ENGINE = InnoDB;

CREATE TABLE Contato (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(20),
    opcao_contato INTEGER
);
ALTER TABLE Contato ENGINE = InnoDB;
 
ALTER TABLE PacoteViagem ADD CONSTRAINT FK_PacoteViagem_2
    FOREIGN KEY (fk_Destino_id)
    REFERENCES Destino (id)
    ON DELETE CASCADE;
 
ALTER TABLE Imagem ADD CONSTRAINT FK_Imagem_2
    FOREIGN KEY (fk_Destino_id)
    REFERENCES Destino (id)
    ON DELETE CASCADE;
 
ALTER TABLE Venda ADD CONSTRAINT FK_Venda_2
    FOREIGN KEY (fk_Cliente_cpf)
    REFERENCES Cliente (cpf)
    ON DELETE CASCADE;
 
ALTER TABLE Venda ADD CONSTRAINT FK_Venda_3
    FOREIGN KEY (fk_PacoteViagem_id)
    REFERENCES PacoteViagem (id)
    ON DELETE CASCADE;