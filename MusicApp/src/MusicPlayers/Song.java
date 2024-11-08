package MusicPlayers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner; 

/*
 * 
 * Aurther Leon Mercer
 * Version 0.1.0.1
 */

 public class Song {     
     private String artistName;     
     private String songTitle;     
     private int playCount;
     private String genre;
     private double rating;
     private int numberOfRatings;
     
     // Default constructor
     public Song() {
         this.rating = 0.0;
         this.numberOfRatings = 0;
     }          
 
     // Constructor with basic parameters
     public Song(String artistName, String songTitle, int playCount) {         
         this.artistName = artistName;         
         this.songTitle = songTitle;        
         this.playCount = playCount;
         this.rating = 0.0;
         this.numberOfRatings = 0;     
     }
 
     // Constructor with all parameters
     public Song(String artistName, String songTitle, int playCount, String genre) {         
         this.artistName = artistName;         
         this.songTitle = songTitle;        
         this.playCount = playCount;
         this.genre = genre;
         this.rating = 0.0;
         this.numberOfRatings = 0;     
     }
 
     // Getters
     public String getArtistName() {
         return artistName;
     }
 
     public String getSongTitle() {
         return songTitle;
     }
 
     public int getPlayCount() {
         return playCount;
     }
 
     public String getGenre() {
         return genre;
     }
 
     public double getRating() {
         return rating;
     }
 
     // Setters
     public void setArtistName(String artistName) {
         this.artistName = artistName;
     }
 
     public void setSongTitle(String songTitle) {
         this.songTitle = songTitle;
     }
 
     public void setPlayCount(int playCount) {
         this.playCount = playCount;
     }
 
     public void setGenre(String genre) {
         this.genre = genre;
     }
 
     // Utility methods
     public void incrementPlayCount() {
         this.playCount++;
     }
 
     public void addRating(double newRating) {
         if (newRating >= 0 && newRating <= 5) {
             rating = ((rating * numberOfRatings) + newRating) / (numberOfRatings + 1);
             numberOfRatings++;
         }
     }
 
     public String getAverageRating() {
         return String.format("%.1f/5.0 (%d ratings)", rating, numberOfRatings);
     }
 
     // toString method for easy printing
     @Override
     public String toString() {
         String ratingInfo = numberOfRatings > 0 ? " | Rating: " + getAverageRating() : " | No ratings yet";
         String genreInfo = genre != null ? " | Genre: " + genre : "";
         return "Song: '" + songTitle + "' by " + artistName + 
                " (Plays: " + playCount + ")" + 
                genreInfo + ratingInfo;
     }
 }