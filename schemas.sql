CREATE DATABASE biblioteca;

USE biblioteca;

CREATE TABLE livro (
	id SERIAL PRIMARY KEY,
	titulo VARCHAR(255) NOT NULL,
	autor VARCHAR(150) NOT NULL,
	ano_publicacao INTEGER
);

CREATE TABLE membro (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	endereco TEXT NOT NULL,
	telefone VARCHAR(25) NOT NULL
);

CREATE TABLE bibliotecario (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	email VARCHAR(150) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

CREATE TABLE visitante (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	telefone VARCHAR(25)
);

CREATE TABLE emprestimo (
	id SERIAL PRIMARY KEY,
	id_livro INTEGER REFERENCES livro(id),
	id_membro INTEGER REFERENCES membro(id),
	data_emprestimo DATE NOT NULL,
	data_devolucao DATE NOT NULL
);
