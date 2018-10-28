USE nirzj0shn94smawo;

LOAD DATA LOCAL INFILE '/mySQL/AnimeTable.txt' INTO TABLE anime;
LOAD DATA LOCAL INFILE '/mySQL/DirectorsTable.txt' INTO TABLE directors;
LOAD DATA LOCAL INFILE '/mySQL/StudiosTable.txt' INTO TABLE studios;
LOAD DATA LOCAL INFILE '/mySQL/GenresTable.txt' INTO TABLE genres;
LOAD DATA LOCAL INFILE '/mySQL/ThemesTable.txt' INTO TABLE themes;