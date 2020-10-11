ALTER TABLE LIB_LOGIN ADD     LGI_INCLAU           NUMBER(1)            default 0 not null;

comment on column LIB_LOGIN.LGI_INCLAU is
'Indica si la página de login automáticamente dispara la redirección a Cl@ve';