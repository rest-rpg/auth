CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS refresh_token (
  id UUID NOT NULL DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  token VARCHAR(255) NOT NULL,
  expiry_date TIMESTAMP NOT NULL,
  CONSTRAINT pk_refreshtoken PRIMARY KEY (id)
);

ALTER TABLE refresh_token
ADD CONSTRAINT uc_refreshtoken_token UNIQUE (token);
