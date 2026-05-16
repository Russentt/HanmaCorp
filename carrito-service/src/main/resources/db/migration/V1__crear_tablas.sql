create table carritos(
    id bigint auto_increment primary key,
    usuario_id bigint not null,
    fecha_creacion datetime not null
);

create table items(
    id bigint auto_increment primary key,
    producto_id bigint not null,
    cantidad int not null,
    subtotal int not null,
    carrito_id bigint not null,
    constraint fk_carrito foreign key (carrito_id) references carritos (id)
);