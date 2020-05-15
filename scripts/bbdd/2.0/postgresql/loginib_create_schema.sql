
create sequence LIB_LGI_SEQ;

create sequence LIB_LGO_SEQ;

/*==============================================================*/
/* Table: LIB_LOGIN                                             */
/*==============================================================*/
create table LIB_LOGIN
(
   LGI_CODIGO           bigint not null,
   LGI_ENTIDA           character varying(100)   not null,
   LGI_APLICA           character varying(100),
   LGI_FCSES            timestamp without time zone   not null,
   LGI_IDIOMA           character varying(2)     not null,
   LGI_IDPS             character varying(100)   not null,
   LGI_URLCBK           character varying(4000)  not null,
   LGI_URLCER           character varying(4000)  not null,
   LGI_FCAUTH           boolean NOT NULL DEFAULT false,
   LGI_TICKET           character varying(100),
   LGI_FCALTA           timestamp without time zone,
   LGI_NIVAUT           character varying(50),
   LGI_NIF              character varying(10),
   LGI_NOM              character varying(1000),
   LGI_APE              character varying(1000),
   LGI_APE1             character varying(1000),
   LGI_APE2             character varying(1000),
   LGI_NIFRPT           character varying(10),
   LGI_NOMRPT           character varying(1000),
   LGI_APERPT           character varying(1000),
   LGI_AP1RPT           character varying(1000),
   LGI_AP2RPT           character varying(1000),
   LGI_SESION           character varying(100),
   LGI_QAA              bigint,
   LGI_QAARES           bigint,
   LGI_SAMLID           character varying(100),
   LGI_PURGCK           boolean NOT NULL DEFAULT false,
   constraint LIB_LGI_PK primary key (LGI_CODIGO)
);

comment on table LIB_LOGIN is
'Sesiones para autenticaciones basadas en ticket para Cl@ve para aplicaciones externas';

comment on column LIB_LOGIN.LGI_CODIGO is
'Codigo interno secuencial';

comment on column LIB_LOGIN.LGI_ENTIDA is
'Código entidad';

comment on column LIB_LOGIN.LGI_APLICA is
'Identidicador aplicación';

comment on column LIB_LOGIN.LGI_FCSES is
'Fecha inicio sesion (desde webservice)';

comment on column LIB_LOGIN.LGI_IDIOMA is
'Idioma';

comment on column LIB_LOGIN.LGI_IDPS is
'Idps (separados por ;)';

comment on column LIB_LOGIN.LGI_URLCBK is
'Url callback login correcto (se pasara parametro ticket)';

comment on column LIB_LOGIN.LGI_URLCER is
'Url callback error';

comment on column LIB_LOGIN.LGI_FCAUTH is
'Indica si se fuerza la autenticacion (si no se intentara SSO)';

comment on column LIB_LOGIN.LGI_TICKET is
'Ticket';

comment on column LIB_LOGIN.LGI_FCALTA is
'Fecha alta';

comment on column LIB_LOGIN.LGI_NIVAUT is
'Nivel autenticacion (Idp con el que se ha autenticado)';

comment on column LIB_LOGIN.LGI_NIF is
'Nif / Cif';

comment on column LIB_LOGIN.LGI_NOM is
'Nombre o razon social';

comment on column LIB_LOGIN.LGI_APE is
'Apellidos';

comment on column LIB_LOGIN.LGI_APE1 is
'Apellido 1';

comment on column LIB_LOGIN.LGI_APE2 is
'Apellido 2';

comment on column LIB_LOGIN.LGI_NIFRPT is
'Nif representante';

comment on column LIB_LOGIN.LGI_NOMRPT is
'Nombre representante';

comment on column LIB_LOGIN.LGI_APERPT is
'Apellidos representante';

comment on column LIB_LOGIN.LGI_AP1RPT is
'Apellido 1 representante';

comment on column LIB_LOGIN.LGI_AP2RPT is
'Apellido 2 representante';

comment on column LIB_LOGIN.LGI_SESION is
'Identificador aleatorio de sesion';

comment on column LIB_LOGIN.LGI_QAA is
'Nivel de seguridad solicitado';

comment on column LIB_LOGIN.LGI_QAARES is
'Nivel de seguridad con el que se autentica';

comment on column LIB_LOGIN.LGI_SAMLID is
'SAML ID petición Clave';

comment on column LIB_LOGIN.LGI_PURGCK is
'Marcado como purgado';

/*==============================================================*/
/* Index: LIB_LGI_AK                                            */
/*==============================================================*/
create unique index LIB_LGI_AK on LIB_LOGIN (
   LGI_TICKET ASC
);

/*==============================================================*/
/* Index: LIB_LGI_AK2                                           */
/*==============================================================*/
create unique index LIB_LGI_AK2 on LIB_LOGIN (
   LGI_SESION ASC
);

/*==============================================================*/
/* Table: LIB_LOGOUT                                            */
/*==============================================================*/
create table LIB_LOGOUT
(
   LGO_CODIGO           bigint           not null,
   LGO_ENTIDA           character varying(100)   not null,
   LGO_APLICA           character varying(100),
   LGO_FCSES            timestamp without time zone                 not null,
   LGO_IDIOMA           character varying(2)     not null,
   LGO_URLCBK           character varying(4000)  not null,
   LGO_FCALTA           timestamp without time zone,
   LGO_SAMLID           character varying(100),
   LGO_SESION           character varying(100),
   LGO_PURGCK           boolean NOT NULL DEFAULT false,
   constraint LIB_LGO_PK primary key (LGO_CODIGO)
);

comment on table LIB_LOGOUT is
'Peticiones de logout de Cl@ve';

comment on column LIB_LOGOUT.LGO_CODIGO is
'Codigo interno secuencial';

comment on column LIB_LOGOUT.LGO_ENTIDA is
'Código entidad';

comment on column LIB_LOGOUT.LGO_APLICA is
'Identidicador aplicación';

comment on column LIB_LOGOUT.LGO_FCSES is
'Fecha inicio sesion';

comment on column LIB_LOGOUT.LGO_IDIOMA is
'Idioma';

comment on column LIB_LOGOUT.LGO_URLCBK is
'Url callback';

comment on column LIB_LOGOUT.LGO_FCALTA is
'Fecha alta de logout';

comment on column LIB_LOGOUT.LGO_SESION is
'Identificador aleatorio de sesion';

comment on column LIB_LOGOUT.LGO_PURGCK is
'Marcado como purgado';

/*==============================================================*/
/* Index: LIB_LGO_AK                                            */
/*==============================================================*/
create unique index LIB_LGO_AK on LIB_LOGOUT (
   LGO_SESION ASC
);

