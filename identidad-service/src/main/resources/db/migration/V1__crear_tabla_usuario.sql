create table roles (
    id bigint auto_increment primary key,
    nombre varchar(50) unique not null,
    descripcion varchar(255) not null
);

create table usuarios (
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    email varchar(70) not null,
    passwd varchar(30) not null,
    telefono varchar(20) not null,
    fecha_registro date not null,
    rol_id bigint not null,
    constraint fk_roles_usuarios foreign key(rol_id) references roles (id) 
);
