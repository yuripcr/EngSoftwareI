CREATE DATABASE IF NOT EXISTS sistema_usuarios;

USE sistema_usuarios;

CREATE TABLE usuarios (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    tipoUsuario VARCHAR(50) NOT NULL
);