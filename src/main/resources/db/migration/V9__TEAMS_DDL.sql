CREATE TABLE TEAMS (
  TEAM_ID INT NOT NULL,
  TEAM_NAME VARCHAR(255) NOT NULL,
  SHORT_NAME VARCHAR(3) NOT NULL,
  LEAGUE_CODE INT,
  FOREIGN KEY (LEAGUE_CODE) REFERENCES LEAGUE(LEAGUE_CODE),
  CONSTRAINT pk_teams PRIMARY KEY (TEAM_ID)
);