create table tiendas(
    id bigint auto_increment primary key,
    nombre varchar(150) unique not null,
    descripcion varchar(500) not null,
    reputacion real not null,
    activa boolean not null
);

create table categorias(
    id bigint auto_increment primary key,
    nombre varchar(35) not null,
    descripcion varchar(255) not null
);

create table productos(
    id bigint auto_increment primary key,
    nombre varchar(200) not null,
    descripcion varchar(1000) not null,
    precio real not null,
    stock bigint not null,
    tienda_id bigint not null,
    categoria_id bigint not null,
    constraint fk_tienda_id foreign key (tienda_id) references tiendas (id),
    constraint fk_categoria_id foreign key (categoria_id) references categorias (id)
);

create table vendedores(
    id bigint auto_increment primary key,
    usuario_id bigint unique not null,
    fecha_registro datetime not null,
    tienda_id bigint not null,
    constraint fk_tienda_vendedores_id foreign key (tienda_id) references tiendas (id)
);