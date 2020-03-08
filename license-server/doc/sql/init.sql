
DROP TABLE IF EXISTS license.client_info;
CREATE TABLE license.client_info
(
  account         VARCHAR(32)         NOT NULL,
  product         VARCHAR(32)         NOT NULL,
  client_uuid     VARCHAR(255)        NOT NULL,
  register_time   TIMESTAMP           NULL,
  last_check_time TIMESTAMP           NULL,

  id              INT                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_time    TIMESTAMP           NULL,
  updated_time    TIMESTAMP           NULL,
  enabled         TINYINT DEFAULT '1' NULL,
  remark          VARCHAR(255)        NULL,
  CONSTRAINT u_acc_prod_cid UNIQUE (ACCOUNT, product, client_uuid)
);

DROP TABLE IF EXISTS license.common_dict;
CREATE TABLE license.common_dict
(
  catalog      VARCHAR(32)         NOT NULL,
  value        VARCHAR(32)         NOT NULL,
  id           INT                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_time TIMESTAMP           NULL,
  updated_time TIMESTAMP           NULL,
  enabled      TINYINT DEFAULT '1' NULL,
  remark       VARCHAR(255)        NULL,
  CONSTRAINT u_cata_value UNIQUE (catalog, value)
);

DROP TABLE IF EXISTS license.license_info;
CREATE TABLE license.license_info
(
  account      VARCHAR(32)         NOT NULL,
  product      VARCHAR(32)         NOT NULL,
  expired_time TIMESTAMP           NOT NULL,
  max_node     INT DEFAULT '-1'    NULL,
  public_key   TEXT                NOT NULL,
  private_key  TEXT                NOT NULL,

  id           INT                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_time TIMESTAMP           NULL,
  updated_time TIMESTAMP           NULL,
  enabled      TINYINT DEFAULT '1' NULL,
  remark       VARCHAR(255)        NULL,
  CONSTRAINT u_acc_prod UNIQUE (ACCOUNT, product)
);

