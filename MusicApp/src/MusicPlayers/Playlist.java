package MusicPlayers;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;
    
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addSong(Song song) {
        songs.add(song);
    }
    
    public void removeSong(Song song) {
        songs.remove(song);
    }
    
    public void displayPlaylist() {
        System.out.println("\nPlaylist: " + name);
        System.out.println("---------------------");
        if (songs.isEmpty()) {
            System.out.println("No songs in playlist.");
        } else {
            for (Song song : songs) {
                System.out.println(song.toString());
            }
        }
    }
}