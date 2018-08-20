import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException{
    
    ///////////////// TESTING AREA /////////////////

    //setup to determine significance of each category with respect to anime score
    /*
    int rating_weight = 0;
    int min_votes = 0;
    int max_votes = 9999;
    
    // ((genre score)/total number of genres) * matched genres
    int genres_weight = 0;
    // (themes score)/total number of genres) * matched themes
    int themes_weight = 10;
    
    int director_weight = 0;
    int studio_weight = 0;
    int min_year = 0;
    int max_year = 2020;
    
    List<String> genres = new ArrayList<>(Arrays.asList("adventure"));
    List<String> themes = new ArrayList<>(Arrays.asList("alternate history","conspiracy","immortality","mecha","military","mind control","politics","real robot","revenge","strategic minds","terrorism","tragedy","war"));
    String director = "goro taniguchi";
    String studio = "Bandai Co., Ltd.";
    */
    Input input = new Input();
    
    int rating_weight = input.rating_weight;
    int min_votes = input.min_votes;
    int max_votes = input.max_votes;
    
    // ((genre score)/total number of genres) * matched genres
    int genres_weight = input.genres_weight;
    // (themes score)/total number of genres) * matched themes
    int themes_weight = input.themes_weight;
    
    int director_weight = input.director_weight;
    int studio_weight = input.studio_weight;
    int min_year = input.min_year;
    int max_year = input.max_year;
    
    List<String> genres = input.genres;
    List<String> themes = input.themes;
    String director = input.director;
    String studio = input.studio;
    
    ///////////////// END TESTING AREA /////////////////
    
    PriorityQueue<Anime> pq = new PriorityQueue<Anime>(new AnimeComparator());
    
    //starts to read the file, AnimeData.txt
    try (BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"))) {

      //skips the first 3 useless lines in the AnimeData file
      String line = "";
      for (int i = 0; i < 3; ++i)
        line = br.readLine();
      
      //until the end of the file, gets the first line of each Anime
      while (line != null) {
        line = br.readLine();
        StringBuilder sb = new StringBuilder(line);
        int start = sb.indexOf(":") + 2;
        
        //Creates a new anime object for the current anime
        Anime anime = new Anime();
        anime.name = sb.substring(start);
        
        //reads lines of data for this particular anime
        while ((line = br.readLine()) != null) {
          StringBuilder sb2 = new StringBuilder(line);
      
          //breaks the loop if a colon isn't found, indicating the end of this anime's data
          start = sb2.indexOf(":") + 2;
          if (start < 2) break;

          //finds this anime data's category and updates the current anime object's data
          String data = sb2.substring(start);
          if (sb2.indexOf("ranking") != -1) {
            anime.ranking = Integer.parseInt(data);
          }
          else if (sb2.indexOf("rating") != -1) {
            anime.rating = Float.valueOf(data);
          }
          else if (sb2.indexOf("votes") != -1) {
            anime.votes = Integer.parseInt(data);
          }
          else if (sb2.indexOf("genres") != -1) {
            try {
              String[] temp = data.split(",");
              for (int i = 0; i < temp.length; ++i) 
                anime.genres.add(temp[i]);
            }
            catch (Exception e) {
              anime.genres.add("unknown");
            }
          }
          else if (sb2.indexOf("themes") != -1) {
            try {
              String[] temp = data.split(",");
              for (int i = 0; i < temp.length; ++i)
                anime.themes.add(temp[i]);
            }
            catch (Exception e) {
              anime.themes.add("unknown");
            }
          }
          else if (sb2.indexOf("premiere date") != -1) {
            try {
              int end = data.indexOf("-");
              anime.date = data.substring(0, end);
            }
            catch (Exception e) {
              anime.date = "unknown";
            }
          }
          else if (sb2.indexOf("director") != -1) {
            anime.director = data;
          }
          else if (sb2.indexOf("production studio") != -1) {
            anime.studio = data;
          }
          
          //does nothing with any other data
          else {
            //System.out.println(line);
          }
        }//end of data for this anime
        
        anime.score = anime.determineScore(rating_weight, 
                                           min_votes, max_votes, 
                                           genres, genres_weight, 
                                           themes, themes_weight, 
                                           director, director_weight, 
                                           studio, studio_weight, 
                                           min_year, max_year);
        
        ///////////////// TESTING AREA /////////////////
        
        if(anime.score > 3) {
          System.out.println("-----------------" + anime.ranking);
          System.out.println(anime.name);
          System.out.println("score: " + anime.score);
          /*
          for (int i = 0; i < anime.themes.length; ++i) {
            System.out.println(anime.themes[i]);
          }*/
        }
        
        ///////////////// END TESTING AREA /////////////////
        
      }//file has no more lines to read
    }
    /*
    catch(Exception e) {
      System.out.println("Oops. Something went wrong");
      System.out.println(e);
    }*/
  }
}

class AnimeComparator implements Comparator<Anime> {
  public int compare(Anime a, Anime b) {
    if (a.score < b.score) return 1;
    else if(a.score > b.score) return -1;
    return 0;
  }
}