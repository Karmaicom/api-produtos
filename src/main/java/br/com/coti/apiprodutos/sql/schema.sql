create table produtos(
      id                serial          primary key,
      nome              varchar(150)    not null,
      descricao         text            not null,
      preco             decimal(10,2)   not null,
      quantidade        integer         not null,
      data_cadastro     timestamp       not null    default current_timestamp,
      data_atualizacao  timestamp       not null,
      data_exclusao     timestamp       not null,
      ativo             int             not null    default 1
);