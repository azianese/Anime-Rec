-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema nirzj0shn94smawo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema nirzj0shn94smawo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nirzj0shn94smawo` DEFAULT CHARACTER SET utf8 ;
USE `nirzj0shn94smawo` ;

-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`studios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`studios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`directors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`directors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`anime`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `link` VARCHAR(90) NULL,
  `rating` FLOAT NULL,
  `votes` INT NULL,
  `date` VARCHAR(45) NULL,
  `studios_id` INT NOT NULL,
  `directors_id` INT NOT NULL,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  PRIMARY KEY (`id`, `studios_id`, `directors_id`),
  UNIQUE INDEX `link_UNIQUE` (`link` ASC),
  INDEX `fk_anime_studios1_idx` (`studios_id` ASC),
  INDEX `fk_anime_directors1_idx` (`directors_id` ASC),
  CONSTRAINT `fk_anime_studios1`
    FOREIGN KEY (`studios_id`)
    REFERENCES `nirzj0shn94smawo`.`studios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_anime_directors1`
    FOREIGN KEY (`directors_id`)
    REFERENCES `nirzj0shn94smawo`.`directors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `genre_UNIQUE` (`genre` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`themes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`themes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `theme` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `theme_UNIQUE` (`theme` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`anime_has_themes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_has_themes` (
  `name_id` INT NOT NULL,
  `themes_id` INT NOT NULL,
  PRIMARY KEY (`name_id`, `themes_id`),
  INDEX `fk_name_has_themes_themes1_idx` (`themes_id` ASC),
  INDEX `fk_name_has_themes_name_idx` (`name_id` ASC),
  CONSTRAINT `fk_name_has_themes_name`
    FOREIGN KEY (`name_id`)
    REFERENCES `nirzj0shn94smawo`.`anime` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_name_has_themes_themes1`
    FOREIGN KEY (`themes_id`)
    REFERENCES `nirzj0shn94smawo`.`themes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nirzj0shn94smawo`.`anime_has_genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nirzj0shn94smawo`.`anime_has_genres` (
  `name_id` INT NOT NULL,
  `genres_id` INT NOT NULL,
  PRIMARY KEY (`name_id`, `genres_id`),
  INDEX `fk_name_has_genres_genres1_idx` (`genres_id` ASC),
  INDEX `fk_name_has_genres_name1_idx` (`name_id` ASC),
  CONSTRAINT `fk_name_has_genres_name1`
    FOREIGN KEY (`name_id`)
    REFERENCES `nirzj0shn94smawo`.`anime` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_name_has_genres_genres1`
    FOREIGN KEY (`genres_id`)
    REFERENCES `nirzj0shn94smawo`.`genres` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;