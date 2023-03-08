package my;


import lombok.SneakyThrows;
import my.dao.HouseDao;
import my.house.House;
import my.view.ViewHouses;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private HouseDao houseDao;
    private Scanner scanner;
    private ViewHouses viewHouses;
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    @SneakyThrows
    private void run() {
        Properties props = new Properties();
        try(BufferedReader reader = Files.newBufferedReader(Path.of("config.properties"))){
            props.load(reader);
            Connection connection = DriverManager.getConnection(props.getProperty("url"),props);
            houseDao = new HouseDao(connection);
            viewHouses = new ViewHouses();
        }
        scanner = new Scanner(System.in);
        int n;
        while ((n = menu())!=0){
            switch (n){
                case 1 ->{
                    showAll();
                }
                case 2 ->{
                    addHouse();
                }
                case 3 ->{
                    showByNumberOfRooms();
                }
                case 4 ->{
                    showByNumberOfRoomsAndFloorRange();
                }
                case 5 ->{
                    showByArea();
                }
                case 6 ->{
                    showSortedByArea();
                }
                case 7 ->{
                    showFloorWithApartment();
                }
                case 8 ->{
                    showHousesForEachFloors();
                }
                default -> {
                    System.out.println("Wrong number please repeat");
                }
            }

        }
    }

    private void remove() {
        System.out.println("Enter the id of house");
        int id = Integer.parseInt(scanner.nextLine());
        //houseDao.deleteById(id);
    }

    private void showByNumberOfRoomsAndFloorRange() {
        List<House> houses = houseDao.findAll();
        System.out.println("Enter a number of rooms");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter from * floor");
        int fromFloor = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter to * floor");
        int toFloor = Integer.parseInt(scanner.nextLine());
        List<House> changedList = houseDao.findByNumberOfRoomsAndFloors(n, fromFloor, toFloor, houses);
        System.out.println("--Houses with certain number of rooms and floor range--");
        viewHouses.showList(changedList);
    }

    private void showHousesForEachFloors() {
        List<House> houses = houseDao.findAll();
        System.out.println("----Houses for each floor----");
        List<List<House>> changedList = houseDao.apartmentListOfFloor(houses);
        viewHouses.printApartmentListOfFloor(changedList);

    }

    private void showFloorWithApartment() {
        List<House> houses = houseDao.findAll();
        System.out.println("----Houses sorted by floor----");
        List<House> changedList = houseDao.sortByFloor(houses);
        viewHouses.showList(changedList);
    }

    private void showSortedByArea() {
        List<House> houses = houseDao.findAll();
        System.out.println("----Houses sorted by area----");
        List<House> changedList = houseDao.sortByArea(houses);
        viewHouses.showList(changedList);

    }

    private void showByArea() {
        List<House> houses = houseDao.findAll();
        System.out.println("Enter the area");
        double areaC = Double.parseDouble(scanner.nextLine());
        System.out.println("----Houses with area bigger than printed ----");
        List<House> changedList = houseDao.findByAreaAndSortByAreaAndFloor(houses, areaC);
        viewHouses.showList(changedList);
    }

    private void showByNumberOfRooms() {
        List<House> houses = houseDao.findAll();
        System.out.println("Enter a number of rooms");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("----Houses with certain number of rooms----");
        List<House> changedList = houseDao.findByNumberOfRooms(n,houses);
        viewHouses.showList(changedList);
    }

    private void addHouse() {
        House h = viewHouses.readHouse(scanner);
        houseDao.add(h);
    }

    private void showAll() {
        List<House> houses = houseDao.findAll();
        System.out.println("------- All houses ----------");
        viewHouses.showList(houses);
    }

    private int menu() {
        System.out.println(
                "Choose an action:\n"+
                        "1: Show all Houses\n"+
                        "2: Add to House\n"+
                        "3: Show list of houses that have certain number of rooms\n"+
                        "4: Show list of houses that have certain number of rooms\n"+
                        "and based in certain floors' range\n"+
                        "5: Show list of houses that have area that \n"+
                        "bigger than certain(sorted in descending order)\n"+
                        "6: Show list of houses sorted by area in ascending order\n"+
                        "7: Show list of houses' floor that have at least one apartment\n"+
                        "8: Show list of houses for each floor \n"+
                        "0: Exit"
        );
        return Integer.parseInt(scanner.nextLine());
    }


}