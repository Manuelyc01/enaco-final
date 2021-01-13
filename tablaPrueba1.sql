create database enaco_prueba1;
use enaco_prueba1;

create table usuario(
	id_usuario int auto_increment primary key,
    nombre varchar(200),
    telefono varchar(200),
    correo varchar(200),
    id_rol int,
    id_UniOpe int,
    usua varchar(200),
    passw varchar(200),
    id_estado int,
    /*FK*/
    foreign key(id_rol) references rol(id_rol),
    foreign key(id_UniOpe) references unidadOperativa(id_UniOpe),
    foreign key(id_estado) references estado(id_estado)
);
create table rol(
	id_rol	int auto_increment primary key,
    descripcion varchar(200)
);
create table estado(
	id_estado int auto_increment primary key,
    descripcion varchar(200)
);
create table unidadOperativa(
	id_UniOpe int auto_increment primary key,
    cod_uniOpe varchar(200),
    nom_uniOpe varchar(200),
    direccion varchar(200),
    ubigeoUniOpe varchar(200),
    localidad varchar(200)
);

/*INSERTS*/
insert into rol (descripcion) values ('Administrador'); 
insert into rol (descripcion) values ('Supervisor'); 
insert into rol (descripcion) values ('Operador'); 

insert into estado (descripcion) values ('Activo'); 
insert into estado (descripcion) values ('Inactivo'); 
insert into estado (descripcion) values ('Vacaciones'); 

