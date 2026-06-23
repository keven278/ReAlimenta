drop database if exists realimenta;
create database realimenta;
use realimenta;

create table usuario(
                        id_usuario int auto_increment primary key,
                        senha varchar(255) not null,
                        nome varchar(100) not null,
                        telefone varchar(20),
                        email varchar(100) unique
);

create table consumidor(
                           id_consumidor int auto_increment primary key,
                           id_usuario int not null unique,
                           cpf varchar(30) unique,
                           foreign key (id_usuario) references usuario(id_usuario)
);

create table comerciante (
                             id_comerciante int auto_increment primary key,
                             id_usuario int not null unique,
                             cnpj varchar(30) unique,
                             endereco varchar(100),
                             nome_comercio varchar(100),
                             foreign key (id_usuario) references usuario(id_usuario)
);

create table alimento (
                          id_alimento int auto_increment primary key,
                          nome_alimento varchar (100) not null,
                          categoria varchar (100),
                          validade date,
                          imagem_alimento varchar (100),
                          promocao boolean default false,
                          quantidade int,
                          descricao varchar (100),
                          id_comerciante int,
                          foreign key (id_comerciante) references comerciante(id_comerciante)
);

create table promocao(
                         id_promocao int auto_increment primary key,
                         id_alimento int,
                         inicio_promocao date,
                         fim_promocao date,
                         percentual_desconto int,
                         foreign key (id_alimento) references alimento (id_alimento)
);

create table solicitacao (
                             id_solicitacao int auto_increment primary key,
                             id_consumidor int,
                             id_comerciante int,
                             id_alimento int,
                             inicio_doacao date,
                             fim_doacao date,
                             doacao_feita boolean default false,
                             quantidade_doacao int,

                             foreign key (id_consumidor) references consumidor(id_consumidor),
                             foreign key (id_comerciante) references comerciante(id_comerciante),
                             foreign key (id_alimento) references alimento(id_alimento)
);

create table doacao (
                        id_doacao int auto_increment primary key,
                        id_solicitacao int,
                        id_consumidor int,
                        id_comerciante int,
                        quantidade_doacao int,

                        foreign key(id_solicitacao) references  solicitacao(id_solicitacao),
                        foreign key (id_consumidor) references  consumidor(id_consumidor),
                        foreign key(id_comerciante) references  comerciante(id_comerciante)
);
-- comentario
create table notificacao (
                             id_notificacao int auto_increment primary key,
                             id_consumidor int,
                             mensagem varchar(255),
                             data_notificacao date,
                             foreign key (id_consumidor) references consumidor(id_consumidor)
);