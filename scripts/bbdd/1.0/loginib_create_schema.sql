create sequence LIB_LGI_SEQ;

create sequence LIB_LGO_SEQ;

/*==============================================================*/
/* Table: LIB_LOGIN                                             */
/*==============================================================*/
create table LIB_LOGIN
(
   LGI_CODIGO           NUMBER(20)           not null,
   LGI_ENTIDA           VARCHAR2(100 CHAR)   not null,
   LGI_APLICA           VARCHAR2(100 CHAR),
   LGI_FCSES            DATE                 not null,
   LGI_IDIOMA           VARCHAR2(2 CHAR)     not null,
   LGI_IDPS             VARCHAR2(100 CHAR)   not null,
   LGI_URLCBK           VARCHAR2(4000 CHAR)  not null,
   LGI_URLCER 			VARCHAR2(4000 CHAR)  not null,
   LGI_FCAUTH           NUMBER(1)            default 0 not null,
   LGI_TICKET           VARCHAR2(100 CHAR),
   LGI_FCALTA           DATE,
   LGI_NIVAUT           VARCHAR2(50 CHAR),
   LGI_NIF              VARCHAR2(10 CHAR),
   LGI_NOM              VARCHAR2(1000 CHAR),
   LGI_APE              VARCHAR2(1000 CHAR),
   LGI_APE1             VARCHAR2(1000 CHAR),
   LGI_APE2             VARCHAR2(1000 CHAR),
   LGI_NIFRPT           VARCHAR2(10 CHAR),
   LGI_NOMRPT           VARCHAR2(1000 CHAR),
   LGI_APERPT           VARCHAR2(1000 CHAR),
   LGI_AP1RPT           VARCHAR2(1000 CHAR),
   LGI_AP2RPT           VARCHAR2(1000 CHAR),
   LGI_SESION           VARCHAR2(100 CHAR),
   LGI_QAA              NUMBER(1),
   LGI_SAMLID           VARCHAR2(100 CHAR),
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
'Url callback';

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
'Nivel de seguridad';

comment on column LIB_LOGIN.LGI_SAMLID is
'SAML ID petición Clave';

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
   LGO_CODIGO           NUMBER(20)           not null,
   LGO_ENTIDA           VARCHAR2(100 CHAR)   not null,
   LGO_FCSES            DATE                 not null,
   LGO_IDIOMA           VARCHAR2(2 CHAR)     not null,
   LGO_URLCBK           VARCHAR2(4000 CHAR)  not null,
   LGO_FCALTA           DATE,
   LGO_SAMLID           VARCHAR2(100 CHAR),
   LGO_SESION           VARCHAR2(100 CHAR),
   LGO_APLICA           VARCHAR2(100 CHAR),
   constraint LIB_LGO_PK primary key (LGO_CODIGO)
);

comment on table LIB_LOGOUT is
'Peticiones de logout de Cl@ve';

comment on column LIB_LOGOUT.LGO_CODIGO is
'Codigo interno secuencial';

comment on column LIB_LOGOUT.LGO_ENTIDA is
'Código entidad';

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

comment on column LIB_LOGOUT.LGO_APLICA is
'Identidicador aplicación';

/*==============================================================*/
/* Index: LIB_LGO_AK                                            */
/*==============================================================*/
create unique index LIB_LGO_AK on LIB_LOGOUT (
   LGO_SESION ASC
);
