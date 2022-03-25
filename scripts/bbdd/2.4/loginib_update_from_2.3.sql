create table LIB_DESGLO
(
   LDS_NIF              VARCHAR2(10 CHAR)     not null,
   LDS_NOM              VARCHAR2(1000 CHAR)   not null,
   LDS_APE              VARCHAR2(1000 CHAR),
   LDS_APE1             VARCHAR2(1000 CHAR)   not null,
   LDS_APE2             VARCHAR2(1000 CHAR)   null,
   LDS_NOMCTO           VARCHAR2(1000 CHAR)   not null,
   LDS_FCCREA           DATE                  not null,
   LDS_FCACT            DATE,
   UNIQUE(LDS_NIF)
);

comment on table LIB_DESGLO is 'Tabla de desglose de apellidos';
comment on column LIB_DESGLO.LDS_NIF is 'NIF de la persona que se autentica';
comment on column LIB_DESGLO.LDS_NOM is 'Nombre de la persona autenticada';
comment on column LIB_DESGLO.LDS_APE is 'Apellidos completos de la persona autenticada';
comment on column LIB_DESGLO.LDS_APE1 is 'Primer apellido de la persona autenticada';
comment on column LIB_DESGLO.LDS_APE2 is 'Segundo apellido de la persona autenticada';
comment on column LIB_DESGLO.LDS_NOMCTO is 'Nombre completo de la persona autenticada';
comment on column LIB_DESGLO.LDS_FCCREA is 'Fecha de creación del desglose de apellidos';
comment on column LIB_DESGLO.LDS_FCACT is 'Fecha de actualización del desglose de apellidos';

/* anyadido un check para indicar si se viene detest **/
ALTER TABLE LIB_LOGIN ADD LGI_ISTEST NUMBER(1,0) DEFAULT 0;
ALTER TABLE LIB_LOGIN ADD LGI_SAMLRQ CLOB;

comment on column LIB_LOGIN.LGI_ISTEST is 'Indica si es de tipo test para almacenar info extra';
comment on column LIB_LOGIN.LGI_SAMLRQ is 'Indica la peticion request en B64 a realizar hacia clave, necesaria para el test.html';

