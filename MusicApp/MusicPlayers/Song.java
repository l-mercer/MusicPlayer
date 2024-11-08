package MusicPlayers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner; 

/*
 * 
 * Aurther Leon Mercer
 * Version 0.0.0.1
 */


 public class Song {     
     private String ArtistName;     
     private String SongTitle;     
     private int PlayBack;      
 
     public Song() {}          
 
     // Constructor with parameters
     public Song(String ArtistName, String SongTitle, int PlayBack) {         
         this.ArtistName = ArtistName;         
         this.SongTitle = SongTitle;       
         this.PlayBack = PlayBack;     
     }
 
     // Getters
     public String getArtistName() {
         return ArtistName;
     }
 
     public String getSongTitle() {
         return SongTitle;
     }
 
     public int getPlayBack() {
         return PlayBack;
     }
 
     // Setters
     public void setArtistName(String ArtistName) {
         this.ArtistName = ArtistName;
     }
 
     public void setSongTitle(String SongTitle) {
         this.SongTitle = SongTitle;
     }
 
     public void setPlayBack(int PlayBack) {
         this.PlayBack = PlayBack;
     }
 
     // toString method for easy printing
     @Override
     public String toString() {
         return "Song: '" + SongTitle + "' by " + ArtistName + " (Plays: " + PlayBack + ")";
     }
 }
