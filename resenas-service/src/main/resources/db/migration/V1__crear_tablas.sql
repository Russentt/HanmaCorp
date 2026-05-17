create table resenas(
    id bigint auto_increment primary key,
    usuario_id bigint not null,
    producto_id bigint not null,
    orden_id bigint not null,
    titulo varchar(120) not null,
    comentario varchar(1000) not null,
    puntuacion int not null,
    fecha_creacion datetime not null,
    visible boolean not null
);

create table reacciones(
    id bigint auto_increment primary key,
    usuario_id bigint not null,
    tipo varchar(20) not null,
    fecha_reaccion datetime not null,
    resena_id bigint not null,
    constraint fk_resena_reaccion
        foreign key (resena_id)
        references resenas(id)
);