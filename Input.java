import java.io.*;
import java.util.*;
import java.lang.reflect.Field;

public class Input {
  //basic data
  int rating_weight;
  int min_votes;
  int max_votes;
  int min_year;
  int max_year;
  //specific data
  int genres_weight;
  List<String> genres;
  int themes_weight;
  List<String> themes;
  int director_weight;
  String director;
  int studio_weight;
  String studio;
  //other data
  int numRecs;
  Boolean compareAnime;

  //Class costructor.
  //animeList is a list of Anime class objects
  public Input(Scanner sc, List<Anime> animeList) {
    //uses preset data if the user wants to use default data
    if (wantDefaultRecommendations(sc)) {
      setDefaultData();
      return;
    }
    //gets some baseline data
    numRecs = getNumRecs(sc);
    rating_weight = getRatingWeight(sc);
    min_votes = getMinVotes(sc);
    max_votes = getMaxVotes(sc);
    min_year = getMinYear(sc);
    max_year = getMaxYear(sc);
    compareAnime = false;
    //uses preset data based on an anime if an anime is used as reference
    if (getPurpose(sc)) {
      compareAnime = true;
      useAnime(sc, animeList);
    }
    //otherwise asks for user-inputted data
    else {
      genres = getGenres(sc);
      genres_weight = getGenresWeight(sc);
      themes = getThemes(sc);
      themes_weight = getThemesWeight(sc);
      director_weight = getDirectorWeight(sc);
      if (director_weight > 0)
        director = getDirector(sc, animeList);
      studio_weight = getStudioWeight(sc);
      if (studio_weight > 0)
        studio = getStudio(sc, animeList);
    }
  }

  ////////////////// METHODS TO GET USER PURPOSE & SET PRESET DATA //////////////////

  //Asks for default recommendations
  private Boolean wantDefaultRecommendations(Scanner sc) {
    System.out.println("Do you want the default recommendations? (y/n)?");
    String s = sc.nextLine();
    while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      System.out.println("Do you want the default recommendations? (y/n)?");
      s = sc.nextLine();
    }
    if (s.equalsIgnoreCase("y"))
      return true;
    else
      return false;
  }

  //Sets default weights if the user selects default recommendations
  private void setDefaultData() {
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
  private void useAnime(Scanner sc, List<Anime> animeList) {
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
    }
    //String animeName = helperString(sc, animeList, "name");
    //weights for genres and themes
    genres_weight = 10;
    List<String> genreList = new ArrayList<>();
    for (String genre : animeList.get(i).genres)
      genreList.add(genre); 
    genres = genreList;
    themes_weight = 10;
    List<String> themeList = new ArrayList<>();
    for (String theme : animeList.get(i).themes)
      themeList.add(theme);
    themes = themeList;
    //weights for director and studio
    director_weight = 5;
    director = animeList.get(i).director;
    studio_weight = 5;
    studio = animeList.get(i).studio;
  }

  //////////////////// METHODS TO GET/SET USER-DETERMINED DATA ////////////////////

  //Asks for the rating weight
  private int getRatingWeight(Scanner sc) {
    return helperWeight10(sc, "How much do you value the anime's rating (1-10)?");
  }

  //Asks for the minimum vote cutoff
  private int getMinVotes(Scanner sc) {
    return helperInt(sc, "What is the minimum number of votes you want your recommendations to have?");
  }

  //Asks for the maximum vote cutoff
  private int getMaxVotes(Scanner sc) {
    return helperInt(sc, "What is the maximum number of votes you want your recommendations to have?");
  }

  //Asks for the minimum year cutoff
  private int getMinYear(Scanner sc) {
    return helperInt(sc, "You want to see recommendations no earlier than the year: ____");
  }

  //Asks for the maximum year cutoff
  private int getMaxYear(Scanner sc) {
    return helperInt(sc, "You want to see recommendations no later than the year: ____");
  }

  private int getGenresWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the genres?");
  }

  private List<String> getGenres(Scanner sc) {
    String message = "What genres would you like to search for (comma separated)? Ex: comedy, drama, etc.";
    return helperList(sc, message, "genres");
  }

  private int getThemesWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the themes?");
  }

  private List<String> getThemes(Scanner sc) {
    String message = "What themes would you like to search for (comma separated)? Ex: technology, time travel, etc.";
    return helperList(sc, message , "themes");
  }

  private int getDirectorWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the director?");
  }

  private String getDirector(Scanner sc, List<Anime> animeList) {
    return helperString(sc, animeList, "director");
  }

  private int getStudioWeight(Scanner sc) {
    return helperWeight10(sc, "How much weight would you like to give the studio?");
  }

  private String getStudio(Scanner sc, List<Anime> animeList) {
    return helperString(sc, animeList, "studio");
  }

  //////////////////// HELPER METHOD ////////////////////

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

  //Helper function to get a user inputted int
  private int helperInt(Scanner sc, String message) {
    boolean valid = false;
    System.out.println(message);
    String s = sc.nextLine();
    int num = 0;
    while (!valid) {
      try {
        num = Integer.valueOf(s);
        valid = true;
      }
      catch (Exception e) {
        System.out.println("Sorry, invalid input. Only whole numbers between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + " are allowed.");
        System.out.println(message);
        s = sc.nextLine();
      }
    }
    return num;
  }

  private List<String> helperList(Scanner sc, String message, String category) {
    System.out.println(message);
    String s = sc.nextLine();
    List<String> list = new ArrayList<>();
    //case: genre category
    if (category.equalsIgnoreCase("genres")) {
      String[] genreArray = s.split(",");
      for (int i = 0; i < genreArray.length; ++i) {
        list.add(genreArray[i].replaceAll("\\s+",""));
      }
    }
    //case: theme category
    if (category.equalsIgnoreCase("themes")) {
      String[] themeArray = s.split(",");
      for (int i = 0; i < themeArray.length; ++i) {
        list.add(themeArray[i].replaceAll("\\s+",""));
      }
    }
    return list;
  }

  //Helper function to get a user inputted string
  private String helperString(Scanner sc, List<Anime> animeList, String category) {
    System.out.println("What " + category + " would you like to use?");
    String s = sc.nextLine();
    //checks to see if the inputted string is present in the anime list
    if (category.equalsIgnoreCase("name")) {
      for (int i = 0; i < animeList.size(); ++i)
        if (animeList.get(i).name.equalsIgnoreCase(s))
          return s;
    }
    if (category.equalsIgnoreCase("director")) {
      for (int i = 0; i < animeList.size(); ++i)
        if (animeList.get(i).director.equalsIgnoreCase(s))
          return s;
    }
    if (category.equalsIgnoreCase("studio")) {
      for (int i = 0; i < animeList.size(); ++i)
        if (animeList.get(i).studio.equalsIgnoreCase(s))
          return s;
    }
    //the inputted string was not found
    System.out.println("Sorry, that " + category + " was not found. Would you like to try a different " + category + " (y/n)?");
    s = sc.nextLine();
    while (!s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n")) {
      System.out.println("Sorry, invalid input. only 'y' or 'n' are valid inputs.");
      System.out.println("Would you like to try a different " + category + " (y/n)?");
      s = sc.nextLine();
    }
    if (s.equalsIgnoreCase("y")) 
      return helperString(sc, animeList, category);
    else
      return null;
  }
}