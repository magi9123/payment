CREATE USER postrges;
CREATE DATABASE payments;
GRANT ALL PRIVILEGES ON DATABASE payments TO postgres;

CREATE TABLE IF NOT EXISTS merchant
(
    id                    uuid,
    name                  varchar(100) not null,
    description           varchar(255),
    email                 varchar(50),
    status                int default 0,
    total_transaction_Sum numeric(20, 5),
    CONSTRAINT pkey_merchant_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id           int not null ,
    name         varchar(50),
    password     text,
    CONSTRAINT pkey_user_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role
(
    id           int not null ,
    name         varchar(50),
    CONSTRAINT pkey_role_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles
(
  user_id int not null,
  role_id int not null,
  CONSTRAINT fk_usersRolesUserId FOREIGN KEY(user_id) REFERENCES users(id),
  CONSTRAINT fk_usersRolesRoleId FOREIGN KEY(role_id) REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS transaction
(
    id             uuid,
    amount         numeric(20, 5),
    status         varchar(10),
    customer_email varchar(50),
    customer_phone varchar(17),
    created_at     timestamp without time zone,
    next_transaction uuid references transaction(id),
    reference_id   uuid,
    CONSTRAINT pkey_transaction_id PRIMARY KEY (id),
    CONSTRAINT fk_transactionMenchant FOREIGN KEY (reference_id) REFERENCES merchant (id)
);

INSERT INTO role(id, name)
VALUES(1,'ROLE_ADMIN'),
      (2,'ROLE_USER');

INSERT INTO users(id, name, password)
VALUES (1,'maggie','$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi'),
       (2,'hilp','$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi');

INSERT INTO users_roles(user_id, role_id)
VALUES ('1','1'),
       ('1','2'),
       ('2','2');

INSERT INTO merchant(id, name, description, email, status, total_Transaction_Sum)
VALUES ('d1311ea7-9e43-4847-9529-bcee4031dc7e', 'Maggie', 'for test', 'maggie@gmail.com', '0', 1434324123.8787),
       ('dd1afbfd-c15c-4712-a2d2-7006ab50f3fb', 'Siri', 'testing', 'siri@gmail.com', '0', 4343134123213.7767),
       ('dd1afbfd-c15c-4712-5643-7006ab50f3fb', 'Stefan', 'testing fire system', 'peter@gmail.com', '1', 20.10);

INSERT INTO transaction(id, amount, status, customer_email, customer_phone, created_at, reference_id)
VALUES (gen_random_uuid(), 321312.00, 'APPROVED', 'someemail@gmail.com', '08776567567', current_timestamp,
        'd1311ea7-9e43-4847-9529-bcee4031dc7e')

