package ro.ubb.movie_catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.movie_catalog.core.domain.entities.Client;
import ro.ubb.movie_catalog.core.domain.entities.Movie;
import ro.ubb.movie_catalog.core.domain.entities.Rentals;
import ro.ubb.movie_catalog.web.dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Console {
    private static final String ClientURL = "http://localhost:8080/api/clients";
    private static final String MovieURL = "http://localhost:8080/api/movies";
    private static final String RentalURL = "http://localhost:8080/api/rentals";
    private RestTemplate restTemplate;
    public Console(RestTemplate rt) {
        restTemplate = rt;
    }

    public void runConsole() {

        String menu = "Choose an option: \n" +
                "1.Add client\n" +
                "2.Print clients\n" +
                "3.Add movie\n" +
                "4.Print movies\n" +
                "5.Filter clients by name\n" +
                "6.Filter movies by director\n" +
                "7.Remove movie\n" +
                "8.Remove client\n" +
                "9.Update movie\n"+
                "10.Add rental\n" +
                "11.Delete rental\n" +
                "12.Update rental\n" +
                "13.Print rentals\n" +
                "14.Print Most Rented Movie\n"+
                "15.Update Client\n" +
                "16.Get the client how rented the most movies\n" +
                "17.Exit";
        try(Scanner console = new Scanner(System.in)) {
            while (true) {
                System.out.println(menu);
                int option = console.nextInt();
                if (option == 1)
                    addClient();
                else if (option == 2)
                    printAllClients();
                else if (option == 3)
                    addMovie();
                else if (option == 4)
                    printAllMovies();
                else if (option == 5)
                    filterClients();
                else if (option == 6)
                    filterMoviesByDirector();
                else if (option == 7)
                    deleteMovie();
                else if (option == 8)
                    deleteClient();
                else if (option == 9)
                    updateMovie();
                else if (option == 10)
                    addRental();
                else if (option == 11)
                    deleteRental();
                else if (option == 12)
                    updateRental();
                else if (option == 13)
                    printAllRentals();
                else if (option == 14)
                    mostRentedMovie();
                else if (option == 15)
                    updateClient();
                else if (option == 16)
                    mostFrequentClient();
                else if (option == 17)
                    break;
            }
        }

    }
    private void addRental() {
        Rentals rental = this.readRental();
        long cid = rental.getClientID();
        long mid = rental.getMovieID();
        int days = rental.getNumberOfDays();
        RentalDto rentalDto = restTemplate.postForObject(
                RentalURL,
                new RentalDto(cid, mid, days),
                RentalDto.class
        );
    }

    private void deleteRental() {
        System.out.println("Insert the id of the rental you want to delete: ");
        Scanner console = new Scanner(System.in);
        long id = console.nextLong();
        restTemplate.delete(RentalURL + "/{id}", id);

    }

    private void updateRental() {
        Rentals rental = this.readRental();
        RentalDto rentalDto = new RentalDto(rental.getClientID(), rental.getMovieID(), rental.getNumberOfDays());
        restTemplate.put(RentalURL + "/{id}", rentalDto, rental.getId());

    }

    private void mostFrequentClient() {

    }

    private void mostRentedMovie() {

    }

    private void printAllRentals() {
        RentalsDto allRentals = restTemplate.getForObject(RentalURL, RentalsDto.class);
        System.out.println(allRentals);

    }

    private void deleteClient() {
        System.out.println("Insert the id of the cleint you want to delete: ");
        Scanner console = new Scanner(System.in);
        long id = console.nextLong();
        restTemplate.delete(ClientURL + "/{id}", id);


    }

    private void updateClient() {
        Client client = this.readClient();
        ClientDto clientDto = new ClientDto(client.getName(), client.getAge());
        restTemplate.put(ClientURL + "/{id}", client, client.getId());
    }

    private void updateMovie(){
        Movie movie = this.readMovie();
        MovieDto movieDto = new MovieDto(movie.getName(), movie.getDirector());
        restTemplate.put(MovieURL + "/{id}", movieDto, movie.getId());

    }

    private void deleteMovie(){
        System.out.println("Insert the id of the movie you want to delete: ");
        Scanner console = new Scanner(System.in);
        long id = console.nextLong();
        restTemplate.delete(MovieURL + "/{id}", id);

    }

    private void filterMoviesByDirector(){
        Scanner console = new Scanner(System.in);

        System.out.println("Enter the director you want to filter by:\n");
        String name = console.nextLine();
        MoviesDto moviesDto = restTemplate.getForObject(MovieURL, MoviesDto.class, name);
        System.out.println(moviesDto);

    }

    private void filterClients() {
        Scanner console = new Scanner(System.in);

        System.out.println("Enter the name you want to filter by:\n");
        String name = console.nextLine();
        ClientsDto allClients = restTemplate.getForObject(ClientURL, ClientsDto.class, name);
        System.out.println(allClients);
    }


    private void printAllClients() {
        ClientsDto allClients = restTemplate.getForObject(ClientURL, ClientsDto.class);
        System.out.println(allClients);

    }

    private void printAllMovies() {

        MoviesDto allMovies = restTemplate.getForObject(MovieURL, MoviesDto.class);
        System.out.println(allMovies);

    }

    private void addClient() {
        Client client = readClient();
        String name = client.getName();
        int age = client.getAge();
        ClientDto clientDto = restTemplate.postForObject(
                ClientURL,
                new ClientDto(name, age),
                ClientDto.class
        );
    }

    private void addMovie() {

        Movie movie = this.readMovie();
        String name = movie.getName();
        String director = movie.getDirector();
        MovieDto movieDto = restTemplate.postForObject(
                MovieURL,
                new MovieDto(name, director),
                MovieDto.class
        );

    }

    private Client readClient() {
        System.out.println("Read client {id, name, age}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long ID = Long.valueOf(bufferedReader.readLine());
            String name = bufferedReader.readLine();
            int age = Integer.parseInt(bufferedReader.readLine());
            Client client = new Client(name, age);
            client.setId(ID);
            return client;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Movie readMovie() {
        System.out.println("Read movie {id, name, director}");
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long ID = Long.valueOf(bufferedReader.readLine());
            String name = bufferedReader.readLine();
            String director = bufferedReader.readLine();
            Movie movie = new Movie(name, director);
            movie.setId(ID);
            return movie;
        }catch(IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    private Rentals readRental() {
        System.out.println("Read rental {id, clientID, movieID, Number of days}");
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long ID = Long.valueOf(bufferedReader.readLine());
            Long clientID = Long.valueOf(bufferedReader.readLine());
            Long MovieID = Long.valueOf(bufferedReader.readLine());
            int days = Integer.parseInt(bufferedReader.readLine());
            Rentals rental = new Rentals(clientID, MovieID, days);
            rental.setId(ID);
            return rental;

        }catch(IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

}
