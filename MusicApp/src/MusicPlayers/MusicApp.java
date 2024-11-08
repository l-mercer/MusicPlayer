package MusicPlayers;

import java.util.Scanner;
import java.util.InputMismatchException;

public class MusicApp {
    private MusicLibrary library;
    private Scanner scanner;

    // Constructor
    public MusicApp() {
        library = new MusicLibrary();
        scanner = new Scanner(System.in);
    }

    // Start the application
    public void start() {
        loadSampleData();
        boolean running = true;

        while (running) {
            displayMenu();
            running = handleUserChoice();
        }
        library.cleanup(); 
        scanner.close();
    }

    // Display the main menu
    private void displayMenu() {
        System.out.println("\n=== Music Library System ===");
        System.out.println("1.  View All Songs");
        System.out.println("2.  Add New Song");
        System.out.println("3.  Remove Song");
        System.out.println("4.  View Songs by Play Count");
        System.out.println("5.  Search Songs");
        System.out.println("6.  Sort Menu");
        System.out.println("7.  Genre Options");
        System.out.println("8.  Playlist Menu");
        System.out.println("9.  Recently Played");
        System.out.println("10. Play a Song");
        System.out.println("11. Rate a Song");
        System.out.println("12. Import/Export Menu");
        System.out.println("13. Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice (1-13): ");
    }

    // Handle user input and menu choices
    private boolean handleUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    library.displayAllSongs();
                    break;
                case 2:
                    addNewSong();
                    break;
                case 3:
                    removeSong();
                    break;
                case 4:
                    displaySongsByPlayCount();
                    break;
                case 5:
                    searchSongs();
                    break;
                case 6:
                    handleSortMenu();
                    break;
                case 7:
                    handleGenreMenu();
                    break;
                case 8:
                    handlePlaylistMenu();
                    break;
                case 9:
                    library.showRecentlyPlayed();
                    break;
                case 10:
                    playSong();
                    break;
                case 11:
                    rateSong();
                    break;
                case 12:
                    handleFileMenu();
                    break;
                case 13:
                    System.out.println("\nThank you for using the Music Library System!");
                    return false;
                default:
                    System.out.println("\nInvalid choice! Please enter a number between 1 and 13.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nError: Please enter a valid number!");
            scanner.nextLine(); // Clear the invalid input
        }
        return true;
    }

    // Method to add a new song
    private void addNewSong() {
        try {
            System.out.println("\n=== Add New Song ===");
            System.out.print("Enter artist name: ");
            String artist = scanner.nextLine().trim();

            System.out.print("Enter song title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter play count: ");
            int plays = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            System.out.print("Enter genre (or press enter to skip): ");
            String genre = scanner.nextLine().trim();

            if (artist.isEmpty() || title.isEmpty() || plays < 0) {
                System.out.println("Error: Invalid input! Please ensure all fields are filled correctly.");
                return;
            }

            Song newSong = genre.isEmpty() ? 
                          new Song(artist, title, plays) : 
                          new Song(artist, title, plays, genre);
            library.addSong(newSong);

        } catch (InputMismatchException e) {
            System.out.println("Error: Play count must be a valid number!");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    // Method to remove a song
    private void removeSong() {
        System.out.println("\n=== Remove Song ===");
        System.out.print("Enter the title of the song to remove: ");
        String title = scanner.nextLine().trim();
        
        if (!title.isEmpty()) {
            library.removeSong(title);
        } else {
            System.out.println("Error: Song title cannot be empty!");
        }
    }

    // Method to display songs by play count
    private void displaySongsByPlayCount() {
        try {
            System.out.println("\n=== View Songs by Play Count ===");
            System.out.print("Enter minimum play count: ");
            int minPlays = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (minPlays < 0) {
                System.out.println("Error: Play count cannot be negative!");
                return;
            }

            library.displaySongsOverPlayCount(minPlays);

        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Method to search songs
    private void searchSongs() {
        System.out.println("\n=== Search Songs ===");
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().trim();
        
        if (!searchTerm.isEmpty()) {
            library.searchSongs(searchTerm);
        } else {
            System.out.println("Error: Search term cannot be empty!");
        }
    }

    // Method to handle sort menu
    private void handleSortMenu() {
        System.out.println("\n=== Sort Menu ===");
        System.out.println("1. Sort by Play Count");
        System.out.println("2. Sort by Title");
        System.out.println("3. Sort by Artist");
        System.out.print("Enter your choice (1-3): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.sortByPlayCount();
                    break;
                case 2:
                    library.sortByTitle();
                    break;
                case 3:
                    library.sortByArtist();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Method to handle genre menu
    private void handleGenreMenu() {
        System.out.println("\n=== Genre Menu ===");
        System.out.println("1. View songs by genre");
        System.out.print("Enter your choice (1): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine().trim();
                if (!genre.isEmpty()) {
                    library.displayByGenre(genre);
                } else {
                    System.out.println("Error: Genre cannot be empty!");
                }
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Method to handle playlist menu
    private void handlePlaylistMenu() {
        System.out.println("\n=== Playlist Menu ===");
        System.out.println("1. Create new playlist");
        System.out.println("2. Add song to playlist");
        System.out.println("3. View all playlists");
        System.out.print("Enter your choice (1-3): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter playlist name: ");
                    String name = scanner.nextLine().trim();
                    if (!name.isEmpty()) {
                        library.createPlaylist(name);
                    }
                    break;
                case 2:
                    System.out.print("Enter playlist name: ");
                    String playlistName = scanner.nextLine().trim();
                    System.out.print("Enter song title: ");
                    String songTitle = scanner.nextLine().trim();
                    if (!playlistName.isEmpty() && !songTitle.isEmpty()) {
                        library.addToPlaylist(playlistName, songTitle);
                    }
                    break;
                case 3:
                    library.showPlaylists();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Method to play a song
    private void playSong() {
        System.out.println("\n=== Play Song ===");
        System.out.print("Enter song title to play: ");
        String title = scanner.nextLine().trim();
        
        if (!title.isEmpty()) {
            library.playSong(title);
        } else {
            System.out.println("Error: Song title cannot be empty!");
        }
    }

    // Method to rate a song
    private void rateSong() {
        System.out.println("\n=== Rate Song ===");
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Error: Song title cannot be empty!");
            return;
        }

        Song song = library.getSong(title);
        if (song == null) {
            System.out.println("Song not found!");
            return;
        }

        try {
            System.out.print("Enter rating (0-5): ");
            double rating = scanner.nextDouble();
            scanner.nextLine();

            if (rating < 0 || rating > 5) {
                System.out.println("Error: Rating must be between 0 and 5!");
                return;
            }

            song.addRating(rating);
            System.out.println("Rating added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Method to handle file menu
    private void handleFileMenu() {
        System.out.println("\n=== File Menu ===");
        System.out.println("1. Export library");
        System.out.println("2. Import library");
        System.out.print("Enter your choice (1-2): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter filename to export to: ");
                    String exportFile = scanner.nextLine().trim();
                    if (!exportFile.isEmpty()) {
                        library.exportToFile(exportFile);
                    }
                    break;
                case 2:
                    System.out.print("Enter filename to import from: ");
                    String importFile = scanner.nextLine().trim();
                    if (!importFile.isEmpty()) {
                        library.importFromFile(importFile);
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }
    }

    // Load sample data
    private void loadSampleData() {
        library.addSong(new Song("Drake", "Rich Flex", 1500000, "Hip Hop"));
        library.addSong(new Song("Arctic Monkeys", "R U Mine?", 2500000, "Rock"));
        library.addSong(new Song("The Weeknd", "Starboy", 3000000, "Pop"));
        library.addSong(new Song("Doja Cat", "Vegas", 1000000, "Hip Hop"));
        library.addSong(new Song("Harry Styles", "As It Was", 4000000, "Pop"));
        library.addSong(new Song("Taylor Swift", "Anti-Hero", 2800000, "Pop"));
        library.addSong(new Song("Ed Sheeran", "Shape of You", 3500000, "Pop"));
        library.addSong(new Song("Post Malone", "Circles", 2200000, "Pop"));
        library.addSong(new Song("Billie Eilish", "Bad Guy", 2900000, "Pop"));
        library.addSong(new Song("The Kid LAROI", "Stay", 1800000, "Pop"));
    }

    // Main method
    public static void main(String[] args) {
        MusicApp app = new MusicApp();
        app.start();
    }
}