
  DROP SEQUENCE  "PETCLINIC"."OWNERS_SEQ"   ;
   DROP SEQUENCE  "PETCLINIC"."PETS_SEQ"   ;
   DROP SEQUENCE  "PETCLINIC"."SPECIALTIES_SEQ"  ;
   DROP SEQUENCE  "PETCLINIC"."TYPES_SEQ"   ;
   DROP SEQUENCE  "PETCLINIC"."VETS_SEQ"   ;
   DROP SEQUENCE  "PETCLINIC"."VISITS_SEQ" ;

   CREATE SEQUENCE  "PETCLINIC"."OWNERS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "PETCLINIC"."PETS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "PETCLINIC"."SPECIALTIES_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "PETCLINIC"."TYPES_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "PETCLINIC"."VETS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "PETCLINIC"."VISITS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER  NOCYCLE ;

CREATE TABLE vets (
  id         INTEGER PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);

create sequence vets_seq;

create or replace trigger vets_trg
before insert on vets
for each row
begin
  select vets_seq.nextval into :new.id from dual;
end;
/
CREATE INDEX vets_last_name ON vets (last_name);

CREATE TABLE specialties (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(80)
);
create sequence specialties_seq;

create or replace trigger specialties_trg
before insert on specialties
for each row
begin
  select specialties_seq.nextval into :new.id from dual;
end;
/
CREATE INDEX specialties_name ON specialties (name);

CREATE TABLE vet_specialties (
  vet_id       INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL
);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_vets FOREIGN KEY (vet_id) REFERENCES vets (id);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_specialties FOREIGN KEY (specialty_id) REFERENCES specialties (id);

CREATE TABLE types (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(80)
);
create sequence types_seq;

create or replace trigger types_trg
before insert on types
for each row
begin
  select types_seq.nextval into :new.id from dual;
end;
/
CREATE INDEX types_name ON types (name);

CREATE TABLE owners (
  id         INTEGER PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
create sequence owners_seq;

create or replace trigger owners_trg
before insert on owners
for each row
begin
  select owners_seq.nextval into :new.id from dual;
end;
/
CREATE INDEX owners_last_name ON owners (last_name);

CREATE TABLE pets (
  id         INTEGER PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER NOT NULL
);
create sequence pets_seq;

create or replace trigger pets_trg
before insert on pets
for each row
begin
  select pets_seq.nextval into :new.id from dual;
end;
/
ALTER TABLE pets ADD CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
ALTER TABLE pets ADD CONSTRAINT fk_pets_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX pets_name ON pets (name);

CREATE TABLE visits (
  id          INTEGER PRIMARY KEY,
  pet_id      INTEGER NOT NULL,
  visit_date  DATE,
  description VARCHAR(255)
);
create sequence visits_seq;

create or replace trigger visits_trg
before insert on visits
for each row
begin
  select visits_seq.nextval into :new.id from dual;
end;
/
ALTER TABLE visits ADD CONSTRAINT fk_visits_pets FOREIGN KEY (pet_id) REFERENCES pets (id);
CREATE INDEX visits_pet_id ON visits (pet_id);
