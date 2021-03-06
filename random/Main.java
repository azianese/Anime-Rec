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
                anime.genres.add(temp[i].replaceAll("\\s+",""));
            }
            catch (Exception e) {
              anime.genres.add("unknown");
            }
          }
          else if (sb2.indexOf("themes") != -1) {
            try {
              String[] temp = data.split(",");
              for (int i = 0; i < temp.length; ++i)
                anime.themes.add(temp[i].replaceAll("\\s+",""));
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

    ///////////////// TESTING AREA /////////////////

    //initialize variables
    System.out.println("Disclaimer: Data is taken from animenewsnetwork and may not be compelete.");
    Scanner sc = new Scanner(System.in);
    Input input = new Input(sc, animeList);
    PriorityQueue<Anime> pq = new PriorityQueue<Anime>(new AnimeComparator());

    //add animes to a priority queue
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
      if (pq.size() > input.numRecs+1)
        pq.poll();
    }

    //outputs the contents on the priority queue in reverse order
    System.out.println();
    Stack<Anime> stack = new Stack<Anime>();
    //makes sure we have the correct number of anime recommendations being output,
    //else recommendation 0 is the anime reference used itself.
    int index = 0;
    if (!input.compareAnime) {
      pq.poll();
      index = 1;
    }
    while (pq.peek() != null) {
      stack.push(pq.poll());
    }
    while (!stack.empty()) {
      Anime anime = stack.pop();
      System.out.println(index + ": " + anime.name);
      System.out.println("  score: " + anime.score);
      ++index;
    }

    ///////////////// END TESTING AREA /////////////////
    sc.close();
  }
}

//helper class to compare anime based on their score
class AnimeComparator implements Comparator<Anime> {
  public int compare(Anime a, Anime b) {
    if (a.score < b.score) return -1;
    if (a.score > b.score) return 1;
    //in case of tied score, breaks ties with ranking
    if (a.ranking < b.ranking) return 1;
    if (a.ranking > b.ranking) return -1;
    //shouldn't ever return 0....
    return 0;
  }
}

///////////////// NOTES /////////////////

//still need a way deal with anime with unknown data