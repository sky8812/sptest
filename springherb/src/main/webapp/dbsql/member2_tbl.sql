--drop table member2   cascade constraint;
create table member2
(
    no        number        primary key,
    userid     varchar2(20)   unique not null,
    name        varchar2(20)    not null,
    pwd        varchar2(15)    not null,
    email1     varchar2(30)    null,
    email2     varchar2(20)    null,
    hp1          varchar2(3)    null,
    hp2          varchar2(4)    null,
    hp3          varchar2(4)    null,
    zipcode    varchar2(7)        null,
    address    varchar2(200)   null,
    addressDetail    varchar2(50)   null,
    regdate     date        default sysdate,
    mileage        number   default 0,
    outdate		date        null--Ż���� ��� Ż���� �ִ´�    
);

--drop sequence member2_seq;
create sequence member2_seq
increment by 1
start with 1
nocache;

select * from member2;

/*
create table zipcode
(
 ZIPCODE varchar2(10),
 SIDO varchar2(30),
 GUGUN varchar2(30),
 DONG varchar2(200),
 BUNJI varchar2(100),
 SEQ number
);

select * from zipcode;
*/
