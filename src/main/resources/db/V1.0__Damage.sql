CREATE TABLE if not exists damage (
    id bigint primary key,
    municipality varchar,
    latitude float,
    longitude float,
    damage_date date,
    damage_extent int,
    createdAt timestamp,
    updatedAt timestamp

);