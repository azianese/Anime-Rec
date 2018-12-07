import java.io.*;
import java.util.*;

public class Main2 {

  public static void main(String[] args) throws IOException{
    createAnimeTableFile();
  }

  /////////////// HELPER METHODS ///////////////

  public static void createAnimeTableFile() throws IOException{
    //sets files to read/write from
    Reader reader = new InputStreamReader(
      new FileInputStream("AnimeData_full.txt"), "UTF-8");
    BufferedReader br = new BufferedReader(reader);
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("table_anime_2.txt"),"UTF-8");
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
          writer.write(arr[0] + "\t"); 
        }
      }
      else if (line.startsWith("plot")) {
        if (s.equals("unknown"))
          writer.write("NULL");
        else 
          writer.write(s + "\t");
      }
      else if (line.startsWith("img")) {
        if (s.equals("unknown"))
          writer.write("NULL");
        else 
          writer.write(s + "\t");
      }
      else if (line.startsWith("altTitle")) {
        if (s.equals("unknown"))
          writer.write("NULL");
        else 
          writer.write(s + "\t");
      }
      else
        continue;
    }
  }
}