USE nirzj0shn94smawo;

#DROP TABLE IF EXISTS tester;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`tester` (
  `name` NVARCHAR(90) NOT NULL,
  `link` VARCHAR(90) NULL,
  `rating` FLOAT NULL,
  `votes` INT NULL,
  `date` VARCHAR(45) NULL
);
ALTER TABLE tester ADD id int NOT NULL AUTO_INCREMENT primary key FIRST;

SELECT * FROM tester;



#LOAD DATA LOCAL INFILE 'C:/Users/Sam/Documents/Coding/Personal Projects/WebScraping/AniRec/MySQL/AnimeTable.txt' INTO TABLE anime;
#LOAD DATA LOCAL INFILE '/mySQL/DirectorsTable.txt' INTO TABLE directors;
#LOAD DATA LOCAL INFILE '/mySQL/StudiosTable.txt' INTO TABLE studios;
#LOAD DATA LOCAL INFILE '/mySQL/GenresTable.txt' INTO TABLE genres;
#LOAD DATA LOCAL INFILE '/mySQL/ThemesTable.txt' INTO TABLE themes;