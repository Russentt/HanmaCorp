create table estado_orden(
    id bigint auto_increment primary key,
    nombre varchar(12) not null
);

create table ordenes(
    id bigint auto_increment primary key,
    usuario_id bigint not null,
    total int not null,
    fecha_creacion datetime not null,
    estado_id bigint not null,
    constraint fk_estado_orden_id_orden foreign key (estado_id) references estado_orden (id)
);

create table detalle_orden(
    id bigint auto_increment primary key,
    producto_id bigint not null,
    cantidad int not null,
    precio_unitario int not null,
    orden_id bigint not null,
    constraint fk_orden_id_detalle foreign key (orden_id) references ordenes (id)
);

create table historial(
    id bigint auto_increment primary key,
    fecha_cambio datetime not null,
    orden_id bigint not null,
    estado_anterior_id bigint not null,
    estado_actual_id bigint not null,
    constraint fk_orden_id_historial foreign key (orden_id) references ordenes (id),
    constraint fk_estado_anterior_id foreign key (estado_anterior_id) references estado_orden (id),
    constraint fk_estado_actual_id foreign key (estado_actual_id) references estado_orden (id)
);