create table CONFIG
(
  PASSWORD         VARCHAR(30),
  CANQUERYMAXMONEY float,
  CHECKSORTTYPE    CHAR(1)
)
;
create table MONEY_DETAIL_T
(
  MONEY_SNO  int not null,
  MONEY_TIME DATE not null,
  MONEY      float not null,
  MONEY_TYPE VARCHAR(10) not null,
  MONEY_DESC VARCHAR(200)
)
;

create table MONEY_TYPE_T
(
  TYPE_ID   varchar(1) not null,
  TYPE_DESC VARCHAR(10)
)
;

create table TALLY_TYPE_T
(
  TALLY_TYPE_SNO  int not null,
  TALLY_TYPE_DESC VARCHAR(10),
  MONEY_TYPE      varchar(1),
  PARENT_CODE     VARCHAR(10),
  TYPE_CODE       VARCHAR(10) not null
)
;