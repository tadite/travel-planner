CREATE TABLE IF NOT EXISTS category
(
  category_id bigserial NOT NULL,
  name character varying(255),
  CONSTRAINT category_pkey PRIMARY KEY (category_id),
  CONSTRAINT unique_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE category
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS check_point
(
  check_point_id bigserial NOT NULL,
  cost character varying(255),
  description character varying(255),
  number_of_days integer,
  place_of_residence_id bigint,
  travel_id bigint,
  type_of_movement_id bigint,
  type_of_rest_id bigint,
  CONSTRAINT check_point_pkey PRIMARY KEY (check_point_id),
  CONSTRAINT fk1s6axdhsygk2xggvw16eomhla FOREIGN KEY (place_of_residence_id)
      REFERENCES place_of_residence (place_of_residence_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkfjmgia3tety15hhfwa3rm9ekh FOREIGN KEY (type_of_rest_id)
      REFERENCES type_of_rest (type_of_rest_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkkyq2qq8qcmv4aux1jf0oail4b FOREIGN KEY (type_of_movement_id)
      REFERENCES type_of_movement (type_of_movement_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkl58463g6ou0gq1h6cdlbymb74 FOREIGN KEY (travel_id)
      REFERENCES travel (travel_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE check_point
  OWNER TO admin;

CREATE TABLE IF NOT EXISTS city
(
  city_id bigserial NOT NULL,
  name character varying(255),
  country_id bigint,
  CONSTRAINT city_pkey PRIMARY KEY (city_id),
  CONSTRAINT fkrpd7j1p7yxr784adkx4pyepba FOREIGN KEY (country_id)
      REFERENCES country (country_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE city
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS client
(
  client_id bigserial NOT NULL,
  age integer,
  email character varying(255),
  first_name character varying(255),
  is_blocked boolean,
  last_name character varying(255),
  login character varying(255),
  password character varying(255),
  role character varying(255),
  city_id bigint,
  country_id bigint,
  CONSTRAINT client_pkey PRIMARY KEY (client_id),
  CONSTRAINT fkevdwlrxhbct07e6dighauj6er FOREIGN KEY (country_id)
      REFERENCES country (country_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkkb5fr3nx3qucrbme4wewcmwbf FOREIGN KEY (city_id)
      REFERENCES city (city_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE client
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS country
(
  country_id bigserial NOT NULL,
  name character varying(255),
  CONSTRAINT country_pkey PRIMARY KEY (country_id),
  CONSTRAINT unique_country_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE country
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS excursion
(
  excursion_id bigserial NOT NULL,
  cost character varying(255),
  duration character varying(255),
  name character varying(255),
  check_point_id bigint,
  city_id bigint,
  country_id bigint,
  CONSTRAINT excursion_pkey PRIMARY KEY (excursion_id),
  CONSTRAINT fk57wb1w5f2kibblahl078cv4bi FOREIGN KEY (country_id)
      REFERENCES country (country_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkjrwbjh8vmjp68l99qtgnc1hvk FOREIGN KEY (check_point_id)
      REFERENCES check_point (check_point_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fko8b54rtmv58a23edox7g21itp FOREIGN KEY (city_id)
      REFERENCES city (city_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE excursion
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS option
(
  option_id bigserial NOT NULL,
  name character varying(255),
  CONSTRAINT option_pkey PRIMARY KEY (option_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE option
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS option_for_travel
(
  option_for_travel_id bigserial NOT NULL,
  description character varying(255),
  option_id bigint,
  travel_id bigint,
  CONSTRAINT option_for_travel_pkey PRIMARY KEY (option_for_travel_id),
  CONSTRAINT fk6t6aamghnonl4kndhsyq71rtj FOREIGN KEY (travel_id)
      REFERENCES travel (travel_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkryxdnceow0j3rh2apm5he3c4s FOREIGN KEY (option_id)
      REFERENCES option (option_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE option_for_travel
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS place_of_residence
(
  place_of_residence_id bigserial NOT NULL,
  climate character varying(255),
  cost_per_day character varying(255),
  description character varying(255),
  name character varying(255),
  city_id bigint,
  country_id bigint,
  type_of_residence_id bigint,
  CONSTRAINT place_of_residence_pkey PRIMARY KEY (place_of_residence_id),
  CONSTRAINT fk12vprs2ycgxwoy9h9lxqe1ono FOREIGN KEY (type_of_residence_id)
      REFERENCES type_of_residence (type_of_residence_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkgtit24jkw7myucu0pqdp183f7 FOREIGN KEY (city_id)
      REFERENCES city (city_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkiqtxgvfuhgdgckvvokanpogue FOREIGN KEY (country_id)
      REFERENCES country (country_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE place_of_residence
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS social_network
(
  social_network_id bigserial NOT NULL,
  name character varying(255),
  client_id bigint,
  CONSTRAINT social_network_pkey PRIMARY KEY (social_network_id),
  CONSTRAINT fkd8bsx801cbehoo2aces275bvv FOREIGN KEY (client_id)
      REFERENCES client (client_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE social_network
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS travel
(
  travel_id bigserial NOT NULL,
  cost character varying(255),
  description character varying(255),
  name character varying(255),
  number_of_days integer,
  CONSTRAINT travel_pkey PRIMARY KEY (travel_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE travel
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS travel_for_client
(
  travel_for_client_id bigserial NOT NULL,
  description character varying(255),
  client_id bigint,
  travel_id bigint,
  CONSTRAINT travel_for_client_pkey PRIMARY KEY (travel_for_client_id),
  CONSTRAINT fkc44xsu6pft0n93nn828b48a98 FOREIGN KEY (travel_id)
      REFERENCES travel (travel_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkdpw333r08tcgrl215fp9wva6r FOREIGN KEY (client_id)
      REFERENCES client (client_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE travel_for_client
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS type_of_movement
(
  type_of_movement_id bigserial NOT NULL,
  cost character varying(255),
  description character varying(255),
  duration character varying(255),
  number_of_people integer,
  category_id bigint,
  CONSTRAINT type_of_movement_pkey PRIMARY KEY (type_of_movement_id),
  CONSTRAINT fko4yaqfq43th00k0w05infn0py FOREIGN KEY (category_id)
      REFERENCES category (category_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE type_of_movement
  OWNER TO admin;



CREATE TABLE IF NOT EXISTS type_of_residence
(
  type_of_residence_id bigserial NOT NULL,
  description character varying(255),
  name character varying(255),
  CONSTRAINT type_of_residence_pkey PRIMARY KEY (type_of_residence_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE type_of_residence
  OWNER TO admin;


CREATE TABLE IF NOT EXISTS type_of_rest
(
  type_of_rest_id bigserial NOT NULL,
  description character varying(255),
  name character varying(255),
  CONSTRAINT type_of_rest_pkey PRIMARY KEY (type_of_rest_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE type_of_rest
  OWNER TO admin;
