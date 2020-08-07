ALTER TABLE LIB_LOGIN ADD     LGI_EVIAUD           boolean NOT NULL DEFAULT false;
ALTER TABLE LIB_LOGIN ADD     LGI_EVIDEN           TEXT;
ALTER TABLE LIB_LOGIN ADD     LGI_EVIHSH           character varying(500);
ALTER TABLE LIB_LOGIN ADD     LGI_EVIALG           character varying(500);

comment on column LIB_LOGIN.LGI_EVIAUD is
'Auditar autenticación generando evidencias';

comment on column LIB_LOGIN.LGI_EVIDEN is
'Evidencias autenticación (JSON)';

comment on column LIB_LOGIN.LGI_EVIHSH is
'Hash de las evidencias';

comment on column LIB_LOGIN.LGI_EVIALG is
'Algoritmo del Hash';