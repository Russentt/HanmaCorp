create table bodegas(
    id bigint auto_increment primary key,
    nombre varchar(100) not null,
    direccion varchar(255) not null
);

create table inventarios(
    id bigint auto_increment primary key,
    producto_id bigint not null,
    stock_disponible int not null,
    stock_reservado int not null,
    stock_minimo int not null,
    bodega_id bigint not null,
    constraint fk_bodega_inv_id foreign key (bodega_id) references bodegas (id),
    constraint chk_stock_disp check(stock_disponible >= 0),
    constraint chk_stock_resv check(stock_reservado >= 0),
    constraint chk_stock_min check(stock_minimo > 0)
);

create table movimientos_stock(
    id bigint auto_increment primary key,
    tipo_movimiento varchar(40) not null,
    cantidad int not null,
    fecha_movimiento datetime not null,
    inventario_id bigint not null,
    constraint fk_inv_mov foreign key (inventario_id) references inventarios (id)
);



