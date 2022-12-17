CREATE TABLE order_placement
(
    order_id          VARCHAR NOT NULL,
    user_id           VARCHAR NOT NULL,
    team_id           INT          NOT NULL,
    season_code       INT          NOT NULL,
    size              VARCHAR(2),
    quantity          INT          NOT NULL,
    order_date        TIMESTAMP,
    order_placed_date TIMESTAMP,
    CONSTRAINT pk_orderplacement PRIMARY KEY (order_id)
);