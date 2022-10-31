CREATE SEQUENCE JERSEY_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE jersey
(
    id          INT NOT NULL,
    team_code   INT NOT NULL,
    size        INT NOT NULL,
    stock       INT NOT NULL,
    season_code INT NOT NULL,
    image_url   VARCHAR(255),
    CONSTRAINT pk_jersey PRIMARY KEY (id)
);

ALTER TABLE jersey
    ADD CONSTRAINT FK_JERSEY_ON_SEASON_CODE FOREIGN KEY (season_code) REFERENCES season (season_code);

ALTER TABLE jersey
    ADD CONSTRAINT FK_JERSEY_ON_TEAM_CODE FOREIGN KEY (team_code) REFERENCES teams (team_id);