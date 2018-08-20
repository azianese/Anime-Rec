import java.io.*;
import java.util.*;

public class Input {

  int rating_weight;
  int min_votes;
  int max_votes;
  int min_year;
  int max_year;
  int genres_weight;
  List<String> genres;
  int themes_weight;
  List<String> themes;
  int director_weight;
  String director;
  int studio_weight;
  String studio;
  
  int numRecs;
  Boolean compareAnime;

  //Default costructor
  public Input() {
    if (getDefault().equals("y")) {
      setDefault();
      return;
    }
    numRecs = getNumRecs();
    if (getPurpose().equals("y")) {
      
    }
    else {
      
    }
  }




  //Asks for default recommendations
  private String getDefault() {
    System.out.println("Do you want to print the default recommendations? (y/n)?");
    Scanner reader = new Scanner(System.in);
    String s = reader.nextLine();
    if (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      s = getDefault();
    }
    reader.close();
    return s;
  }

  private void setDefault() {
    this.rating_weight = 10;

    this.min_votes = 0;
    this.max_votes = Integer.MAX_VALUE;
    this.min_year = 0;
    this.max_year = 2018;

    this.genres_weight = 0;
    this.genres = new ArrayList<>(Arrays.asList(""));
    this.themes_weight = 0;
    this.themes = new ArrayList<>(Arrays.asList(""));
    this.director_weight = 0;
    this.director = "";
    this.studio_weight = 0;
    this.studio = "";
    
    this.numRecs = 100;
    this.compareAnime = false;
  }

  //Asks for the number of recommendations
  private int getNumRecs() {
    System.out.println("How many anime recommendations do you want (up to 500)?");
    Scanner reader = new Scanner(System.in);
    String s = reader.nextLine();
    int num = 0;
    try {
      num = Integer.valueOf(s);
      if (0 > num || num > 500) {
        System.out.println("Sorry, invalid input. Only numbers between 0 and 500 [inclusive] are allowed.");
        num = getNumRecs();
      }
    }
    catch (Exception e) {
      System.out.println("Sorry, invalid input. Only numbers between 0 and 500 [inclusive] are allowed.");
      num = getNumRecs();
    }
    reader.close();
    return num;
  }

  //Asks if the user wants to use a single anime for recomendations
  private String getPurpose() {
    System.out.println("Do you have an anime in mind that you want to find similar animes to? (y/n)");
    Scanner reader = new Scanner(System.in);
    String s = reader.nextLine();    
    if (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      s = getPurpose();
    } 
    reader.close();
    return s;
  }

}