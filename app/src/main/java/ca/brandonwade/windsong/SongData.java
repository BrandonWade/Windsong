package ca.brandonwade.windsong;

import android.graphics.Bitmap;

/**
 * Object to contain information about an audio file.
 */
public class SongData {
    // Require
    private final String songTitle;

    // Optional
    private final Bitmap albumArt;
    private final String albumArtist;
    private final String albumName;

    public static class Builder {
        // Required
        private final String songTitle;

        // Optional
        private Bitmap albumArt;
        private String albumArtist;
        private String albumName;

        public Builder(String songTitle) {
            this.songTitle = songTitle;
        }

        public Builder albumArt(Bitmap art) {
            albumArt = art;
            return this;
        }

        public Builder albumArtist(String artist) {
            albumArtist = artist;
            return this;
        }

        public Builder albumName(String name) {
            albumName = name;
            return this;
        }

        public SongData build() {
            return new SongData(this);
        }
    }

    private SongData(Builder builder) {
        // Required
        songTitle   = builder.songTitle;

        // Optional
        albumArt    = builder.albumArt;
        albumArtist = builder.albumArtist;
        albumName   = builder.albumName;
    }

    public String getSongTitle() {
        return this.songTitle;
    }

    public Bitmap getAlbumArt() {
        return this.albumArt;
    }

    public String getAlbumArtist() {
        return this.albumArtist;
    }

    public String getAlbumName() {
        return this.albumName;
    }
}
