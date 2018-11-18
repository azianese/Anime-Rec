SELECT * FROM anime;
#SELECT * FROM genres;
#SELECT * FROM themes;
#SELECT * FROM directors;
#SELECT * FROM studios;
#SELECT * FROM anime_genres;
#SELECT * FROM anime_themes;
SELECT * FROM anime_directors;
#SELECT * FROM anime_studios;

#SELECT themes.theme FROM themes
#INNER JOIN anime_themes ON anime_themes.theme_id = themes.id
#INNER JOIN anime ON anime.id = anime_themes.anime_id
#WHERE anime.anime = 'your name. (movie)';

SELECT directors.director FROM directors
INNER JOIN anime_directors ON directors.id = anime_directors.director_id
INNER JOIN anime ON anime.id = anime_directors.anime_id
WHERE anime.anime = "Gintama' (TV 2/2011)";

#SHOW TABLES;