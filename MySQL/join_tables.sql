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

INSERT IGNORE INTO anime_genres (anime_id, genre_id)
SELECT anime.id AS anime_id, genres.id AS genre_id
FROM anime_to_genres
INNER JOIN anime ON anime_to_genres.anime = anime.anime
INNER JOIN genres ON anime_to_genres.genre = genres.genre;
#DROP TABLE IF EXISTS anime_to_genres;

INSERT IGNORE INTO anime_themes (anime_id, theme_id)
SELECT anime.id AS anime_id, themes.id AS genre_id
FROM anime_to_themes
INNER JOIN anime ON anime_to_themes.anime = anime.anime
INNER JOIN themes ON anime_to_themes.theme = themes.theme;
#DROP TABLE IF EXISTS anime_to_themes;
