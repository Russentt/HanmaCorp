create table estado_pago (
    id bigint auto_increment primary key,
    nombre varchar(30) not null
);

create table pago(
    id bigint auto_increment primary key,
    orden_id bigint not null,
    monto int not null,
    metodo_pago varchar(50) not null,
    fecha_pago datetime not null,
    estado_id bigint not null,
    constraint fk_estado_pago_pago foreign key (estado_id) references estado_pago(id)
);

create table reembolso(
    id bigint auto_increment primary key,
    monto int not null,
    motivo varchar(500) not null,
    pago_id bigint not null,
    constraint fk_pago foreign key (pago_id) references pago (id)
);

create table transaccion(
    id bigint auto_increment primary key,
    codigo_transaccion varchar(50) unique not null,
    respuesta_pasarela varchar(50) not null,
    pago_id bigint not null,
    constraint fk_pago_tran_id foreign key (pago_id) references pago (id)
);