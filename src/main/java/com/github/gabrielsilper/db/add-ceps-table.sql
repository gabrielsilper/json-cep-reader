CREATE TABLE ceps
(
    cep         VARCHAR(10) PRIMARY KEY,
    logradouro  VARCHAR(255) NOT NULL,
    complemento VARCHAR(255),
    bairro      VARCHAR(100) NOT NULL,
    localidade  VARCHAR(100) NOT NULL,
    uf          CHAR(2)      NOT NULL,
    ibge        VARCHAR(7)   NOT NULL
);