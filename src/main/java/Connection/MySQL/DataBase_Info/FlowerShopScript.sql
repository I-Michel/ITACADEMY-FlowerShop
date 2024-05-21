-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FlowerShop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FlowerShop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FlowerShop` DEFAULT CHARACTER SET utf8 ;
USE `FlowerShop` ;

-- -----------------------------------------------------
-- Table `FlowerShop`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `price` FLOAT NOT NULL,
  `stock` INT NOT NULL,
  `type` ENUM('FLOWER', 'TREE', 'DECORATION') NOT NULL,
  PRIMARY KEY (`id_product`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FlowerShop`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`ticket` (
  `id_ticket` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_price` FLOAT NOT NULL,
  PRIMARY KEY (`id_ticket`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FlowerShop`.`flower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`flower` (
  `color` VARCHAR(45) NOT NULL,
  `product_id` INT NOT NULL,
  INDEX `fk_flower_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_flower_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShop`.`product` (`id_product`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FlowerShop`.`ticket_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`ticket_product` (
  `quantity` INT(11) NOT NULL,
  `ticket_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  INDEX `fk_ticket_product_ticket1_idx` (`ticket_id` ASC) VISIBLE,
  INDEX `fk_ticket_product_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShop`.`product` (`id_product`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ticket_product_ticket1`
    FOREIGN KEY (`ticket_id`)
    REFERENCES `FlowerShop`.`ticket` (`id_ticket`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShop`.`tree`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`tree` (
  `height` FLOAT NOT NULL,
  `product_id` INT NOT NULL,
  INDEX `fk_tree_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_tree_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShop`.`product` (`id_product`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FlowerShop`.`decoration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`decoration` (
  `material` ENUM('WOOD', 'PLASTIC') NOT NULL,
  `product_id` INT NOT NULL,
  INDEX `fk_decoration_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_decoration_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShop`.`product` (`id_product`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FlowerShop`.`product_in_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShop`.`product_in_ticket` (
  `quantity` INT NOT NULL,
  `product_id` INT NOT NULL,
  `ticket_id` INT NOT NULL,
  INDEX `fk_product_in_ticket_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_product_in_ticket_ticket1_idx` (`ticket_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_in_ticket_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShop`.`product` (`id_product`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_in_ticket_ticket1`
    FOREIGN KEY (`ticket_id`)
    REFERENCES `FlowerShop`.`ticket` (`id_ticket`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
