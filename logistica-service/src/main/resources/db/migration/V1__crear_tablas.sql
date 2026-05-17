create table regiones(
    id bigint auto_increment primary key,
    nombre varchar(150) not null
);

create table comunas(
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    region_id bigint not null,
    constraint fk_region_comuna foreign key (region_id) references regiones (id)
);

create table direccion_entrega(
    id bigint auto_increment primary key,
    usuario_id bigint not null,
    calle varchar(200) not null,
    numero varchar(200) not null,
    departamento varchar(150),
    referencia varchar(150),
    codigo_postal varchar(150) not null,
    comuna_id bigint not null,
    constraint fk_comuna_entrega foreign key (comuna_id) references comunas (id)
);

create table envios(
    id bigint auto_increment primary key,
    orden_id bigint not null,
    direccion_entrega_id bigint not null,
    fecha_envio date not null,
    fecha_entrega_estimada datetime not null,
    fecha_entrega_real datetime not null,
    constraint fk_direccion_id foreign key (direccion_entrega_id) references direccion_entrega (id)
);
