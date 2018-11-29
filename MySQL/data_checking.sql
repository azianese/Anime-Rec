#SELECT * FROM anime;
#SELECT * FROM genres;
#SELECT * FROM themes;
#SELECT * FROM directors;
#SELECT * FROM studios;
#SELECT * FROM anime_genres;
#SELECT * FROM anime_themes;
#SELECT * FROM anime_directors;
#SELECT * FROM anime_studios;
#SHOW TABLES;


SELECT anime.*, directors.director, studios.studio, 
GROUP_CONCAT(genres.genre) AS genres, GROUP_CONCAT(DISTINCT themes.theme) AS themes
FROM anime
LEFT JOIN anime_directors ON anime.id = anime_directors.anime_id
LEFT JOIN directors ON directors.id = anime_directors.director_id
LEFT JOIN anime_studios ON anime.id = anime_studios.anime_id
LEFT JOIN studios ON studios.id = anime_studios.studio_id
LEFT JOIN anime_genres ON anime.id = anime_genres.anime_id
LEFT JOIN genres ON genres.id = anime_genres.genre_id
LEFT JOIN anime_themes ON anime.id = anime_themes.anime_id
LEFT JOIN themes ON themes.id = anime_themes.theme_id
GROUP BY anime.anime
ORDER BY anime.id;

#SELECT anime.anime, GROUP_CONCAT(themes.theme) AS themes
#FROM anime
#INNER JOIN anime_themes ON anime.id = anime_themes.anime_id
#INNER JOIN themes ON themes.id = anime_themes.theme_id
#GROUP BY anime.anime
#ORDER BY anime.id;