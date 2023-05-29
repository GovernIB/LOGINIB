create table LIB_DESGLO
(
   LDS_NIF              character varying(10)     not null,
   LDS_NOM              character varying(1000)   not null,
   LDS_APE              character varying(1000),
   LDS_APE1             character varying(1000)   not null,
   LDS_APE2             character varying(1000)   not null,
   LDS_NOMCTO           character varying(1000)   not null,
   LDS_FCCREA           timestamp without time zone    not null,
   LDS_FCACT            timestamp without time zone,
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
ALTER TABLE LIB_LOGIN ADD LGI_ISTEST boolean DEFAULT false;
ALTER TABLE LIB_LOGIN ADD LGI_SAMLRQ text;

comment on column LIB_LOGIN.LGI_ISTEST is 'Indica si es de tipo test para almacenar info extra';
comment on column LIB_LOGIN.LGI_SAMLRQ is 'Indica la peticion request en B64 a realizar hacia clave, necesaria para el test.html';