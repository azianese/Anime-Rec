import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException{
    createAnimeTableFile();
    createGenresTableFile();
    createThemesTableFile();
    createDirectorsTableFile();
    createStudiosTableFile();
  }

  /////////////// HELPER METHODS ///////////////

  public static void createAnimeTableFile() throws IOException{
    //sets files to read/write from
    BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("AnimeTable.txt"));
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
        writer.newLine();
        continue;
      }
      String s = line.substring(start);
      //write relevant data to file
      if (line.startsWith("name")) {
        writer.write(s + "  ");
      }
      else if (line.startsWith("link")) {
        writer.write(s + "  ");
      }
      else if (line.startsWith("rating")) {
        writer.write(s + "  ");
      }
      else if (line.startsWith("votes")) {
        writer.write(s + "  ");
      }
      else if (line.startsWith("premiere date")) {
        String arr[] = s.split(" ", 2);
        writer.write(arr[0] + "  ");
      }
      else
        continue;
    }
  }

  public static void createGenresTableFile() throws IOException{
    //sets files to read/write from
    BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("GenresTable.txt"));
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
          s = arr[i].replaceAll(" ", "");
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
        writer.newLine();
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createThemesTableFile() throws IOException{
    //sets files to read/write from
    BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("ThemesTable.txt"));
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
          s = arr[i].replaceAll(" ", "");
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
        writer.newLine();
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createDirectorsTableFile() throws IOException{
    //sets files to read/write from
    BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("DirectorsTable.txt"));
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
        writer.newLine();
    }
    //closes readers/writers
    br.close();
    writer.close();
  }

  public static void createStudiosTableFile() throws IOException{
    //sets files to read/write from
    BufferedReader br = new BufferedReader(new FileReader("AnimeData.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("StudiosTable.txt"));
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
        writer.newLine();
    }
    //closes readers/writers
    br.close();
    writer.close();
  }
}