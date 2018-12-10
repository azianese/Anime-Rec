CREATE SCHEMA IF NOT EXISTS `nirzj0shn94smawo` DEFAULT CHARACTER SET utf8 ;
USE nirzj0shn94smawo;

DROP TABLE IF EXISTS anime_genres;
DROP TABLE IF EXISTS anime_themes;
DROP TABLE IF EXISTS anime_directors;
DROP TABLE IF EXISTS anime_studios;

#NVARCHAR is to encompass foreign characters
DROP TABLE IF EXISTS anime;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` NVARCHAR(255) NOT NULL,
  `link` VARCHAR(255) NOT NULL,
  `altTitle` NVARCHAR(255),
  `rating` FLOAT NOT NULL,
  `votes` INT NOT NULL,
  `date` DATE NOT NULL,
  `plot` TEXT,
  `img` VARCHAR(255)
);

DROP TABLE IF EXISTS directors;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`directors` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `director` NVARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS studios;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`studios` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `studio` NVARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS genres;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`genres` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `genre` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS themes;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`themes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `theme` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS anime_to_genres;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_to_genres` (
  `anime` NVARCHAR(255) NOT NULL,
  `genre` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS anime_to_themes;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_to_themes` (
  `anime` NVARCHAR(255) NOT NULL,
  `theme` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS anime_to_directors;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_to_directors` (
  `anime` NVARCHAR(255) NOT NULL,
  `director` NVARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS anime_to_studios;
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_to_studios` (
  `anime` NVARCHAR(255) NOT NULL,
  `studio` NVARCHAR(255) NOT NULL
);

LOAD DATA LOCAL INFILE './MySQL/Data/table_anime.txt' INTO TABLE anime (
  title,
  link,
  altTitle,
  rating,
  votes,
  date,
  plot,
  img
);
LOAD DATA LOCAL INFILE './MySQL/Data/table_directors.txt' INTO TABLE directors (director);
LOAD DATA LOCAL INFILE './MySQL/Data/table_studios.txt' INTO TABLE studios (studio);
LOAD DATA LOCAL INFILE './MySQL/Data/table_genres.txt' INTO TABLE genres (genre);
LOAD DATA LOCAL INFILE './MySQL/Data/table_themes.txt' INTO TABLE themes (theme);
LOAD DATA LOCAL INFILE './MySQL/Data/table_anime_to_genres.txt' INTO TABLE anime_to_genres;
LOAD DATA LOCAL INFILE './MySQL/Data/table_anime_to_themes.txt' INTO TABLE anime_to_themes;
LOAD DATA LOCAL INFILE './MySQL/Data/table_anime_to_directors.txt' INTO TABLE anime_to_directors;
LOAD DATA LOCAL INFILE './MySQL/Data/table_anime_to_studios.txt' INTO TABLE anime_to_studios;