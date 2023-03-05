create database collibra;

\connect collibra;

create schema if not exists asset;

create table if not exists asset.asset_status (
    asset_status_cd varchar(50) primary key,
    asset_status_descr varchar(250) not null,
    lst_chg_ts timestamp without time zone not null default ( now() at time zone 'utc')
);

create table if not exists asset.assets (
    id serial primary key,
    description varchar(250) not null,
    asset_status_cd varchar(50) references asset.asset_status(asset_status_cd) not null default 'INIT',
    asset_version int not null default 1,
    lst_chg_ts timestamp without time zone not null default ( now() at time zone 'utc')
);

create table if not exists asset.asset_composition (
    id serial primary key,
    asset_parent bigint references asset.assets(id) not null,
    asset_child bigint references asset.assets(id) not null,
    lst_chg_ts timestamp without time zone not null default ( now() at time zone 'utc')
);

create index if not exists asset_composition_parent on asset.asset_composition (asset_parent);
create unique index if not exists asset_composition_unq on asset.asset_composition (asset_parent, asset_child);

insert into asset.asset_status values ('INIT', 'Asset has been initialized');
insert into asset.asset_status values ('PROMOTED', 'Asset has been promoted');

create user app password 'app';
grant all on schema asset to app;
grant all on all tables in schema asset to app;
grant all on all functions in schema asset to app;
grant all on all procedures in schema asset to app;
grant all on all routines in schema asset to app;
grant all on all sequences in schema asset to app;