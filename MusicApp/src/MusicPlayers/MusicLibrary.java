package MusicPlayers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.Collections;
import javax.sound.midi.*;


public class MusicLibrary {
    private ArrayList<Song> songList;
    private Queue<Song> recentlyPlayed;
    private static final int RECENT_LIMIT = 5;
    private ArrayList<Playlist> playlists;
    private Synthesizer synth;
    private MidiChannel channel;

    public MusicLibrary() {
        songList = new ArrayList<>();
        recentlyPlayed = new LinkedList<>();
        playlists = new ArrayList<>();
        
        // creates the MIDI
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channel = channels[0];
        } catch (MidiUnavailableException e) {
            System.out.println("Error initializing MIDI system");
        }
    }

    // basic method for addings a song 
    public void addSong(Song song) {
        songList.add(song);
        System.out.println("Song '" + song.getSongTitle() + "' added successfully.");
    }

    public void removeSong(String title) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongTitle().equalsIgnoreCase(title)) {
                Song removedSong = songList.remove(i);
                System.out.println("Song '" + removedSong.getSongTitle() + "' removed successfully.");
                return;
            }
        }
        System.out.println("Song '" + title + "' not found.");
    }

    // method for displaying text depending on user input
    public void displayAllSongs() {
        if (songList.isEmpty()) {
            System.out.println("No songs in the library.");
            return;
        }
        
        System.out.println("\nAll Songs in Library:");
        System.out.println("---------------------");
        for (Song song : songList) {
            System.out.println(song.toString());
        }
    }

    public void displaySongsOverPlayCount(int minimumPlays) {
        boolean found = false;
        System.out.println("\nSongs with over " + minimumPlays + " plays:");
        System.out.println("---------------------");
        
        for (Song song : songList) {
            if (song.getPlayCount() > minimumPlays) {
                System.out.println(song.toString());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No songs found with over " + minimumPlays + " plays.");
        }
    }

    // function for searching songs
    public void searchSongs(String keyword) {
        boolean found = false;
        System.out.println("\nSearch results for: '" + keyword + "'");
        System.out.println("-------------------------");
        for (Song song : songList) {
            if (song.getSongTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                song.getArtistName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(song.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matches found.");
        }
    }

    //  Methods for sorting 
    public void sortByPlayCount() {
        Collections.sort(songList, (s1, s2) -> s2.getPlayCount() - s1.getPlayCount());
        System.out.println("\nSongs sorted by play count (highest to lowest):");
        displayAllSongs();
    }

    public void sortByTitle() {
        Collections.sort(songList, (s1, s2) -> s1.getSongTitle().compareToIgnoreCase(s2.getSongTitle()));
        System.out.println("\nSongs sorted alphabetically by title:");
        displayAllSongs();
    }

    public void sortByArtist() {
        Collections.sort(songList, (s1, s2) -> s1.getArtistName().compareToIgnoreCase(s2.getArtistName()));
        System.out.println("\nSongs sorted by artist name:");
        displayAllSongs();
    }

    // Genre Related Methods
    public void displayByGenre(String genre) {
        boolean found = false;
        System.out.println("\nSongs in " + genre + " genre:");
        System.out.println("-------------------------");
        for (Song song : songList) {
            if (song.getGenre() != null && song.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(song.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No songs found in " + genre + " genre.");
        }
    }

    // Recently Played Feature
    public void playSong(String title) {
        for (Song song : songList) {
            if (song.getSongTitle().equalsIgnoreCase(title)) {
                try {
                    System.out.println("Now playing: " + song.toString());
                    
                    // Plays a melody
                    int[] notes = {60, 64, 67, 72, 67, 64, 60}; 
                    int[] durations = {500, 500, 500, 1000, 500, 500, 1000}; 
                    
                    for (int i = 0; i < notes.length; i++) {
                        channel.noteOn(notes[i], 100);  
                        Thread.sleep(durations[i]);     
                        channel.noteOff(notes[i]);      
                        Thread.sleep(50);               
                    }
                    
                    // Update play count and recently played
                    song.incrementPlayCount();
                    recentlyPlayed.add(song);
                    if (recentlyPlayed.size() > RECENT_LIMIT) {
                        recentlyPlayed.poll();
                    }
                    
                    System.out.println("✓ Song played successfully!");
                    return;
                } catch (InterruptedException e) {
                    System.out.println("Playback interrupted");
                }
                return;
            }
        }
        System.out.println("Song not found.");
    }

    public void cleanup() {
        if (synth != null && synth.isOpen()) {
            synth.close();
        }
    }

    public void showRecentlyPlayed() {
        if (recentlyPlayed.isEmpty()) {
            System.out.println("No recently played songs.");
            return;
        }
        System.out.println("\nRecently Played Songs:");
        System.out.println("---------------------");
        recentlyPlayed.forEach(System.out::println);
    }

    // Playlist Managemen
    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
        System.out.println("Playlist '" + name + "' created successfully.");
    }

    public void addToPlaylist(String playlistName, String songTitle) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(playlistName)) {
                for (Song song : songList) {
                    if (song.getSongTitle().equalsIgnoreCase(songTitle)) {
                        playlist.addSong(song);
                        System.out.println("Song added to playlist successfully.");
                        return;
                    }
                }
                System.out.println("Song not found in library.");
                return;
            }
        }
        System.out.println("Playlist not found.");
    }

    public void showPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("No playlists created.");
            return;
        }
        System.out.println("\nAvailable Playlists:");
        for (Playlist playlist : playlists) {
            playlist.displayPlaylist();
        }
    }

    // File Operations
    public void exportToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Song song : songList) {
                writer.println(String.format("%s,%s,%d,%s",
                    song.getArtistName(),
                    song.getSongTitle(),
                    song.getPlayCount(),
                    song.getGenre() != null ? song.getGenre() : "Unknown"
                ));
            }
            System.out.println("Library exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting library: " + e.getMessage());
        }
    }

    public void importFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    addSong(new Song(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
                }
            }
            System.out.println("Library imported successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error importing library: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid file format");
        }
    }

    // Utility Methods
    public int getSize() {
        return songList.size();
    }

    public Song getSong(String title) {
        for (Song song : songList) {
            if (song.getSongTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }
}