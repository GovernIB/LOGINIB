ALTER TABLE LIB_LOGIN ADD     LGI_INCLAU            boolean NOT NULL DEFAULT false;

comment on column LIB_LOGIN.LGI_INCLAU is
'Indica si la página de login automáticamente dispara la redirección a Cl@ve';