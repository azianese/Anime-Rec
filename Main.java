import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException{

    List<Anime> animeList = new ArrayList<Anime>();

    //starts to read the file, AnimeData.txt.
    //then adds anime to a list of anime.
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

        animeList.add(anime);

      }//file has no more lines to read
    }
    /*
    catch(Exception e) {
      System.out.println("Oops. Something went wrong");
      System.out.println(e);
    }*/

    ///////////////// TESTING AREA /////////////////

    System.out.println("anime list size: " + animeList.size());
    Scanner sc = new Scanner(System.in);
    Input input = new Input(sc, animeList);
    PriorityQueue<Anime> pq = new PriorityQueue<Anime>(new AnimeComparator());

    for (int i = 0; i < animeList.size(); ++i) {
      Anime anime = animeList.get(i);
      anime.score = anime.determineScore(input.rating_weight,
                                         input.min_votes, input.max_votes, 
                                         input.genres, input.genres_weight, 
                                         input.themes, input.themes_weight, 
                                         input.director, input.director_weight, 
                                         input.studio, input.studio_weight, 
                                         input.min_year, input.max_year);
      pq.add(anime);
    }

    for (int i = 1; i < 11; ++i) {
      System.out.print(i + ": ");
      System.out.println(pq.poll().name);
    }

    /*
    if(anime.score > 3) {
      System.out.println("-----------------" + anime.ranking);
      System.out.println(anime.name);
      System.out.println("score: " + anime.score);
    }

    for (int i = 0; i < anime.themes.length; ++i) {
      System.out.println(anime.themes[i]);
    }
    */

    sc.close();
    ///////////////// END TESTING AREA /////////////////
  }
}

class AnimeComparator implements Comparator<Anime> {
  public int compare(Anime a, Anime b) {
    if (a.score < b.score) return 1;
    else if(a.score > b.score) return -1;
    return 0;
  }
}