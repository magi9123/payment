CREATE TABLE IF NOT EXISTS merchant
(
    id                    uuid,
    name                  varchar(100) not null,
    description           varchar(255),
    email                 varchar(50),
    status                boolean default true,
    total_transaction_Sum numeric(20, 5),
    CONSTRAINT pkey_merchant_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transaction
(
    id             uuid,
    amount         numeric(20, 5),
    status         varchar(10),
    customer_email varchar(50),
    customer_phone varchar(17),
    created_at     timestamp without time zone,
    reference_id   uuid,
    CONSTRAINT pkey_transaction_id PRIMARY KEY (id),
    CONSTRAINT fk_transactionMenchant FOREIGN KEY (reference_id) REFERENCES merchant (id)
);

INSERT INTO merchant(id, name, description, email, status, total_Transaction_Sum)
VALUES ('d1311ea7-9e43-4847-9529-bcee4031dc7e', 'Maggie', 'for test', 'maggie@gmail.com', true, 1434324123.8787),
       ('dd1afbfd-c15c-4712-a2d2-7006ab50f3fb', 'Siri', 'testing', 'siri@gmail.com', true, 4343134123213.7767),
       ('dd1afbfd-c15c-4712-5643-7006ab50f3fb', 'Stefan', 'testing fire system', 'peter@gmail.com', false, 20.10);

INSERT INTO transaction(id, amount, status, customer_email, customer_phone, created_at, reference_id)
VALUES (gen_random_uuid(), 321312.00, 'APPROVED', 'someemail@gmail.com', '08776567567',
        current_timestamp, 'd1311ea7-9e43-4847-9529-bcee4031dc7e')
