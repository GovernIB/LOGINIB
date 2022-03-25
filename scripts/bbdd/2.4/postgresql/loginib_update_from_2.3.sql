create table LIB_DESGLO
(
   LDS_NIF              character varying(10 CHAR)     not null,
   LDS_NOM              character varying(1000 CHAR)   not null,
   LDS_APE              character varying(1000 CHAR),
   LDS_APE1             character varying(1000 CHAR)   not null,
   LDS_APE2             character varying(1000 CHAR)   not null,
   LDS_NOMCTO           character varying(1000 CHAR)   not null,
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