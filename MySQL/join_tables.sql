DROP TABLE IF EXISTS anime_directors;
CREATE TABLE anime_directors (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    anime_id INT UNSIGNED NOT NULL,
    director_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (anime_id) REFERENCES anime(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (director_id) REFERENCES directors(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (anime_id, director_id)
);

DROP TABLE IF EXISTS anime_studios;
CREATE TABLE anime_studios (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    anime_id INT UNSIGNED NOT NULL,
    studio_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (anime_id) REFERENCES anime(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (studio_id) REFERENCES studios(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (anime_id, studio_id)
);

DROP TABLE IF EXISTS anime_genres;
CREATE TABLE anime_genres (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	anime_id INT UNSIGNED NOT NULL,
    genre_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (anime_id) REFERENCES anime(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (anime_id, genre_id)
);

DROP TABLE IF EXISTS anime_themes;
CREATE TABLE anime_themes (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	anime_id INT UNSIGNED NOT NULL,
    theme_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (anime_id) REFERENCES anime(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (anime_id, theme_id)
);

INSERT IGNORE INTO anime_directors (anime_id, director_id)
SELECT anime.id AS anime_id, directors.id AS director_id
FROM anime_to_directors
INNER JOIN anime ON anime_to_directors.anime = anime.title
INNER JOIN directors ON anime_to_directors.director = directors.director;
DROP TABLE anime_to_directors;

INSERT IGNORE INTO anime_studios (anime_id, studio_id)
SELECT anime.id AS anime_id, studios.id AS studio_id
FROM anime_to_studios
INNER JOIN anime ON anime_to_studios.anime = anime.title
INNER JOIN studios ON anime_to_studios.studio = studios.studio;
DROP TABLE anime_to_studios;

INSERT IGNORE INTO anime_genres (anime_id, genre_id)
SELECT anime.id AS anime_id, genres.id AS genre_id
FROM anime_to_genres
INNER JOIN anime ON anime_to_genres.anime = anime.title
INNER JOIN genres ON anime_to_genres.genre = genres.genre;
DROP TABLE anime_to_genres;

INSERT IGNORE INTO anime_themes (anime_id, theme_id)
SELECT anime.id AS anime_id, themes.id AS genre_id
FROM anime_to_themes
INNER JOIN anime ON anime_to_themes.anime = anime.title
INNER JOIN themes ON anime_to_themes.theme = themes.theme;
DROP TABLE anime_to_themes;