CREATE TABLE PARKING_USER
(
    id       integer primary key,
    username varchar(20) unique not null,
    password text               not null,
    role     varchar(15)        not null
);


