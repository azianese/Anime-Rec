import java.io.*;
import java.util.*;

public class Input {
  //basic data
  int rating_weight;
  int min_votes;
  int max_votes;
  int min_year;
  int max_year;
  //specific data
  int genres_weight;
  List<String> genres = new ArrayList<String>();
  int themes_weight;
  List<String> themes = new ArrayList<String>();
  int director_weight;
  String director;
  int studio_weight;
  String studio;
  //other data
  int numRecs;
  Boolean compareAnime;

  //Default costructor
  public Input(Scanner sc, List<Anime> animeList) {
    if (getDefault(sc).equals("y")) {
      setDefault();
      return;
    }
    numRecs = getNumRecs(sc);
    rating_weight = getRatingWeight(sc);
    min_votes = getMinVotes(sc);
    max_votes = getMaxVotes(sc);
    min_year = getMinYear(sc);
    max_year = getMaxYear(sc);

    if (getPurpose(sc)) 
      compareAnime = useAnime(sc, animeList);
    else
      compareAnime = false;
    if (!compareAnime) {
      genres = getGenres(sc);
      genres_weight = getGenresWeight(sc);
      themes = getThemes(sc);
      themes_weight = getThemesWeight(sc);
      director = getDirector(sc);
      director_weight = getDirectorWeight(sc);
      studio = getStudio(sc);
      studio_weight = getStudioWeight(sc);
    }
  }

  //Asks for default recommendations
  private String getDefault(Scanner sc) {
    System.out.println("Do you want the default recommendations? (y/n)?");
    String s = sc.nextLine();
    while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      System.out.println("Do you want the default recommendations? (y/n)?");
      s = sc.nextLine();
    }
    return s;
  }

  //Sets default weights if the user selects default recommendations
  private void setDefault() {
    //basic data
    this.rating_weight = 10;
    this.min_votes = 0;
    this.max_votes = Integer.MAX_VALUE;
    this.min_year = 0;
    this.max_year = Integer.MAX_VALUE;
    //specific data
    this.genres_weight = 0;
    this.genres = new ArrayList<>(Arrays.asList(""));
    this.themes_weight = 0;
    this.themes = new ArrayList<>(Arrays.asList(""));
    this.director_weight = 0;
    this.director = "";
    this.studio_weight = 0;
    this.studio = "";
    //other data
    this.numRecs = 100;
    this.compareAnime = false;
  }

  //Asks for the number of recommendations
  private int getNumRecs(Scanner sc) {
    System.out.println("How many anime recommendations do you want (up to 500)?");
    String s = sc.nextLine();
    int num = 0;
    boolean valid = false;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        if (0 > num || num > 500) {
          System.out.println("Sorry, invalid input. Only numbers between 0 and 500 [inclusive] are allowed.");
          System.out.println("How many anime recommendations do you want (up to 500)?");
          s = sc.nextLine();
        }
        else
          valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only numbers between 0 and 500 [inclusive] are allowed.");
        System.out.println("How many anime recommendations do you want (up to 500)?");
        s = sc.nextLine();
      }
    }
    return num;
  }

  //Asks for the rating weight
  private int getRatingWeight(Scanner sc) {
    return helperWeight10(sc, "How much do you value the anime's rating (1-10)?");
  }

  //Asks for the minimum vote cutoff
  private int getMinVotes(Scanner sc) {
    boolean valid = false;
    System.out.println("What is the minimum number of votes you want your recommendations to have?");
    String s = sc.nextLine();
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + " are allowed.");
        System.out.println("What is the minimum number of votes you want your recommendations to have?");
        s = sc.nextLine();
      }
    }
    return num;
  }

  //Asks for the maximum vote cutoff
  private int getMaxVotes(Scanner sc) {
    boolean valid = false;
    System.out.println("What is the maximum number of votes you want your recommendations to have?");
    String s = sc.nextLine();
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + " are allowed.");
        System.out.println("What is the maximum number of votes you want your recommendations to have?");
        s = sc.nextLine();
      }
    }
    return num;
  }

  //Asks for the minimum year cutoff
  private int getMinYear(Scanner sc) {
    boolean valid = false;
    System.out.println("You want to see recommendations no earlier than ____ year: ");
    String s = sc.nextLine();
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + " are allowed.");
        System.out.println("You want to see recommendations no earlier than ____ year: ");
        s = sc.nextLine();
      }
    }
    return num;
  }

  //Asks for the maximum year cutoff
  private int getMaxYear(Scanner sc) {
    boolean valid = false;
    System.out.println("You want to see recommendations no later than ____ year: ");
    String s = sc.nextLine();
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + " are allowed.");
        System.out.println("You want to see recommendations no later than ____ year: ");
        s = sc.nextLine();
      }
    }
    return num;
  }

  //Asks if the user wants to use a single anime for recomendations
  private Boolean getPurpose(Scanner sc) {
    System.out.println("Do you have an anime in mind that you want to find similar animes to? (y/n)");
    String s = sc.nextLine();
    while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      System.out.println("Do you have an anime in mind that you want to find similar animes to? (y/n)");
      s = sc.nextLine();
    } 
    if (s.equalsIgnoreCase("y")) 
      return true;
    else 
      return false;
  }

  //Autofills in input variable values if an anime is selected for comparison
  private Boolean useAnime(Scanner sc, List<Anime> animeList) {
    System.out.println("What anime would you like to use as a reference?");
    String animeName = sc.nextLine();
    boolean animeFound = false;
    int i = 0;
    for (; i < animeList.size(); ++i) {
      if (animeList.get(i).name.equalsIgnoreCase(animeName)) {
        animeFound = true;
        break;
      }
    }
    if (animeFound == false) {
      System.out.println("Sorry, that anime was not found. Would you like to try a different anime (y/n)?");
      String s = sc.nextLine();
      while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
        System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
        System.out.println("Would you like to try a different anime (y/n)?");
        s = sc.nextLine();
      }
      if (s.equals("y")) {
        return useAnime(sc, animeList);
      }
      else {
        return false;
      }
    }
    //weights for genres and themes
    genres_weight = 10;
    for (String genre : animeList.get(i).genres)
      genres.add(genre);    
    themes_weight = 10;
    for (String theme : animeList.get(i).themes)
      themes.add(theme);
    //weights for director and studio
    director_weight = 5;
    director = animeList.get(i).director;
    studio_weight = 5;
    studio = animeList.get(i).studio;
    return true;
  }

  private List<String> getGenres(Scanner sc) {
    return new ArrayList<String>();
  }

  private int getGenresWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the genres?");
  }

  private List<String> getThemes(Scanner sc) {
    return new ArrayList<String>();
  }

  private int getThemesWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the themes?");
  }

  private String getDirector(Scanner sc) {
    return "";
  }

  private int getDirectorWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the director?");
  }

  private String getStudio(Scanner sc) {
    return "";
  }

  private int getStudioWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the studio?");
  }

  //Helper function to determine weights between 0 and 10
  private int helperWeight10(Scanner sc, String message) {
    System.out.println(message);
    String s = sc.nextLine();
    boolean valid = false;
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        if (num < 0 || num > 10) {
          System.out.println("Sorry, invalid input. Only whole numbers between 0 and 10 [inclusive] are allowed.");
          System.out.println(message);
          s = sc.nextLine();
        }
        else
          valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between 0 and 10 [inclusive] are allowed.");
        System.out.println(message);
        s = sc.nextLine();
      }
    }
    return num;
  }
}