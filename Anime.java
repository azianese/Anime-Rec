import java.io.*;
import java.util.*;

public class Anime {

  //general anime data
  String name;
  String link;
  float rating;
  int ranking;
  //data used for eliminating certain animes from consideration
  int votes;
  String date;
  //more specific anime data
  HashSet<String> genres = new HashSet<String>();
  HashSet<String> themes = new HashSet<String>();
  String director;
  String studio;
  //an anime's score/weight based on factors that the user determines is important
  float score;
  
  //method to determine the score that an anime receives based on user input weight
  public float determineScore (int rating_weight, 
                             int min_votes, int max_votes, 
                             List<String> genres, int genres_weight, 
                             List<String> themes, int themes_weight, 
                             String director, int director_weight, 
                             String studio, int studio_weight, 
                             int min_year, int max_year) {
    //calls a helper function to determine the weight/score for this anime
    if (isInRange(min_votes, max_votes, min_year, max_year) == -1) 
      return -1;
    float score = 0;
    score += getRatingScore(rating_weight);
    score += getGenresScore(genres, genres_weight);
    score += getThemesScore(themes, themes_weight);
    score += getDirectorScore(director, director_weight);
    score += getStudioScore(studio, studio_weight);    
    return score;
  }
  
  //-1 means the data is for sure out of range
  //0 means the data is unknown
  //1 means the data is within range
  private int isInRange(int min_votes, int max_votes, 
                        int min_year, int max_year) {
    //makes sure this anime's votes is not out of range
    if (this.votes < min_votes || this.votes > max_votes) 
      return -1;
    //case for when the date is unknown
    if (this.date.equals("unknown")) 
      return 0;
    //makes sure this anime's date is not out of range
    if (Float.valueOf(this.date) < min_year 
        || max_year < Float.valueOf(this.date)) 
      return -1;
    //anime data is within range
    else return 1;
  }
  
  //helper function to determine the rating score
  private float getRatingScore(int rating_weight) {
    return (float) rating_weight * (this.rating / 10.0f);
  }
  
  //helper to determine the genre score
  private float getGenresScore(List<String> genres, int genres_weight) {
    float score = 0;
    float weight = (float) genres_weight / (float) genres.size();
    for (String genre : genres) 
      if (this.genres.contains(genre)) 
        score += weight;
    return score;
  }
  
  //helper function to determine the theme score
  private float getThemesScore(List<String> themes, int themes_weight) {
    float score = 0;
    float weight = (float) themes_weight / (float) themes.size();
    for (String theme : themes) 
      if (this.themes.contains(theme)) 
        score += weight;
    return score;
  }

  //helper function to determine the director score
  private float getDirectorScore(String director, int director_weight) {
    if (this.director.equalsIgnoreCase(director)) 
      return director_weight;
    else return 0;
  }
  
  //helper function to determine the studio score
  private float getStudioScore(String studio, int studio_weight) {
    if (this.studio.equalsIgnoreCase(studio)) 
      return studio_weight;
    else return 0;
  }
}