package my.view;

import my.house.House;

import java.util.List;
import java.util.Scanner;

public class ViewHouses {
    public void showList(List<House> houses) {
        houses.forEach(System.out::println);
        System.out.println("------------------------------");
    }

    public House readHouse(Scanner scanner) {
        System.out.println("Please print a house\n"+
        "Number: ");
        int num = Integer.parseInt(scanner.nextLine());
        System.out.println("Area: ");
        double area = Double.parseDouble(scanner.nextLine());
        System.out.println("Floor: ");
        int floor = Integer.parseInt(scanner.nextLine());
        System.out.println("Number of Rooms: ");
        int numberOfRooms = Integer.parseInt(scanner.nextLine());
        System.out.println("Street: ");
        String street = scanner.nextLine();
        return new House(0, num, area,floor,numberOfRooms,street);
    }

    public void printApartmentListOfFloor(List<List<House>> apartmentListOfFloor) {
        for (List<House> apartmentList : apartmentListOfFloor) {
            System.out.println("Floor " + apartmentList.get(0).getFloor() + ":");
            for (House apartment : apartmentList) {
                System.out.println(apartment);
            }
            System.out.println();
        }
    }

}
