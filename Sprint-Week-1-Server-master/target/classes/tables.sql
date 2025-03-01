CREATE TABLE mCard
(
    id          SERIAL PRIMARY KEY,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    balance     DECIMAL(10, 2)     NOT NULL DEFAULT 0.00,
    issued_at   TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE top_up
(
    id        SERIAL PRIMARY KEY,
    mcard_id  INT REFERENCES mCard (id),
    amount    DECIMAL(10, 2) NOT NULL,
    top_up_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ride
(
    id       SERIAL PRIMARY KEY,
    mcard_id INT REFERENCES mCard (id),
    fare     DECIMAL(10, 2) NOT NULL,
    ride_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
