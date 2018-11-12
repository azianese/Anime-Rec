import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException{
    createAnimeTableFile();
    createGenresTableFile();
    createThemesTableFile();
    createDirectorsTableFile();
    createStudiosTableFile();
    createAnimeGenresFile();
    createAnimeThemesFile();
    createAnimeDirectorsFile();
    createAnimeStudiosFile();
  }

  /////////////// HELPER METHODS ///////////////

  public static void createAnimeTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //reads until end of file
    while (true) {
      String line = br.readLine();
      if (line == null) {
        //closes readers and writers
        br.close();
        writer.close();
        return;
      }
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2) {
        writer.write('\n');
        continue;
      }
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name")) {
        writer.write(s + "\t");
      }
      else if (line.startsWith("link")) {
        writer.write(s + " \t");
      }
      else if (line.startsWith("rating")) {
        writer.write(s + "\t");
      }
      else if (line.startsWith("votes")) {
        writer.write(s + "\t");
      }
      else if (line.startsWith("premiere date")) {
        if (s.equals("unknown"))
          writer.write("NULL");
        else {
          String arr[] = s.split(" ", 2);
          writer.write(arr[0]); 
        }
      }
      else
        continue;
    }
  }

  public static void createGenresTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_genres.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //creates a set to check for duplicates
    Set<String> set = new HashSet<>();
    //creates a priority queue to order data
    PriorityQueue<String> pq = new PriorityQueue<>();
    //reads until end of file
    while (true) {
      String line = br.readLine();
      if (line == null) 
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2) 
        continue;
      String s = line.substring(start);
      //adds unique genres to priority queue
      if (line.startsWith("genres")) {
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; ++i) {
          s = arr[i].replaceAll(" ", "").toLowerCase();
          if (!set.contains(s)) {
            set.add(s);
            pq.add(s);
          } 
        }
      }
    }
    //writes contents of pq to file
    while(true) {
      String s = pq.poll();
      writer.write(s);
      if (pq.peek() == null) 
        break;
      else
        writer.write('\n');
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createThemesTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_themes.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //creates a set to check for duplicates
    Set<String> set = new HashSet<>();
    //creates a priority queue to order data
    PriorityQueue<String> pq = new PriorityQueue<>();
    //reads until end of file
    while (true) {
      String line = br.readLine();
      if (line == null) 
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2) 
        continue;
      String s = line.substring(start);
      //adds unique themes to priority queue
      if (line.startsWith("themes")) {
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; ++i) {
          s = arr[i].replaceAll(" ", "").toLowerCase();
          if (!set.contains(s)) {
            set.add(s);
            pq.add(s);
          } 
        }
      }
    }
    //writes contents of pq to file
    while(true) {
      String s = pq.poll();
      writer.write(s);
      if (pq.peek() == null) 
        break;
      else
        writer.write('\n');
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createDirectorsTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_directors.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //creates a set to check for duplicates
    Set<String> set = new HashSet<>();
    //creates a priority queue to order data
    PriorityQueue<String> pq = new PriorityQueue<>();
    //reads until end of file
    while (true) {
      String line = br.readLine();
      if (line == null) 
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("director")) {
        if (!set.contains(s)) {
          set.add(s);
          pq.add(s);
        }
      }
    }
    //writes contents of pq to file
    while(true) {
      String s = pq.poll();
      writer.write(s);
      if (pq.peek() == null) 
        break;
      else
        writer.write('\n');
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createStudiosTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_studios.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //creates a set to check for duplicates
    Set<String> set = new HashSet<>();
    //creates a priority queue to order data
    PriorityQueue<String> pq = new PriorityQueue<>();
    //reads until end of file
    while (true) {
      String line = br.readLine();
      if (line == null) 
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("production studio")) {
        if (!set.contains(s)) {
          set.add(s);
          pq.add(s);
        }
      }
    }
    //writes contents of pq to file
    while(true) {
      String s = pq.poll();
      writer.write(s);
      if (pq.peek() == null) 
        break;
      else
        writer.write('\n');
    }
    //closes readers/writers
    br.close();
    writer.close();
  }
  
    public static void createAnimeGenresFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime_to_genres.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //reads until end of file
    String name = null;
    while (true) {
      String line = br.readLine();
      if (line == null)
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name"))
        name = s;
      else if (line.startsWith("genres")) {
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; ++i) {
          s = arr[i].replaceAll(" ", "").toLowerCase();
          writer.write(name + "\t" + s + "\n");
        }
      }
      else
        continue;
    }
    br.close();
    writer.close();
  }
  
    public static void createAnimeThemesFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime_to_themes.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //reads until end of file
    String name = null;
    while (true) {
      String line = br.readLine();
      if (line == null)
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name"))
        name = s;
      else if (line.startsWith("themes")) {
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; ++i) {
          s = arr[i].replaceAll(" ", "").toLowerCase();
          writer.write(name + "\t" + s + "\n");
        }
      }
      else
        continue;
    }
    br.close();
    writer.close();
  }
  
  public static void createAnimeDirectorsFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime_to_directors.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //reads until end of file
    String name = null;
    while (true) {
      String line = br.readLine();
      if (line == null)
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name"))
        name = s;
      else if (line.startsWith("director")) {
        writer.write(name + "\t" + s + "\n");
      }
      else
        continue;
    }
    br.close();
    writer.close();
  }
  
  public static void createAnimeStudiosFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime_to_studios.txt"),"UTF-8");
    //skips the first 3 useless lines in the AnimeData file
    for (int i = 0; i < 3; ++i)
      br.readLine();
    //reads until end of file
    String name = null;
    while (true) {
      String line = br.readLine();
      if (line == null)
        break;
      //creates string without the data identifier prefix
      int start = line.indexOf(":") + 2;
      //skips empty lines
      if (start < 2)
        continue;
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name"))
        name = s;
      else if (line.startsWith("production studio")) {
        writer.write(name + "\t" + s + "\n");
      }
      else
        continue;
    }
    br.close();
    writer.close();
  }
}