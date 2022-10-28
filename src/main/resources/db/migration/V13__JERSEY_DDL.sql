CREATE SEQUENCE JERSEY_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE jersey
(
    id        INT NOT NULL,
    team_code INT,
    size      INT,
    stock     INT,
    CONSTRAINT pk_jersey PRIMARY KEY (id)
);

ALTER TABLE jersey
    ADD CONSTRAINT FK_JERSEY_ON_TEAM_CODE FOREIGN KEY (team_code) REFERENCES TEAMS (team_id);