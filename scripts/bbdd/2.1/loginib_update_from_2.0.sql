ALTER TABLE LIB_LOGIN ADD     LGI_EVIAUD           NUMBER(1)            default 0 not null;
ALTER TABLE LIB_LOGIN ADD     LGI_EVIDEN           CLOB;
ALTER TABLE LIB_LOGIN ADD     LGI_EVIHSH           VARCHAR2(500 CHAR);
ALTER TABLE LIB_LOGIN ADD     LGI_EVIALG           VARCHAR2(50 CHAR);

comment on column LIB_LOGIN.LGI_EVIAUD is
'Auditar autenticación generando evidencias';

comment on column LIB_LOGIN.LGI_EVIDEN is
'Evidencias autenticación (JSON)';

comment on column LIB_LOGIN.LGI_EVIHSH is
'Hash de las evidencias';

comment on column LIB_LOGIN.LGI_EVIALG is
'Algoritmo del Hash';