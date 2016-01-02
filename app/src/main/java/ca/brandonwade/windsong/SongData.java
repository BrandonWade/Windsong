package ca.brandonwade.windsong;

/**
 * Created by Brandon W on 2015-12-31.
 */
public class SongData {
    // Require
    private final String songTitle;

    // Optional
    private final String albumArt;
    private final String albumArtist;
    private final String albumName;

    public static class Builder {
        // Required
        private final String songTitle;

        // TODO: Move these to constants
        // Optional
        private String albumArt    = "";
        private String albumArtist = "Unknown Artist";
        private String albumName   = "Unknown Album";

        public Builder(String songTitle) {
            this.songTitle = songTitle;
        }

        public Builder albumArt(String art) {
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

    public String getAlbumArt() {
        return this.albumArt;
    }

    public String getAlbumArtist() {
        return this.albumArtist;
    }

    public String getAlbumName() {
        return this.albumName;
    }
}
