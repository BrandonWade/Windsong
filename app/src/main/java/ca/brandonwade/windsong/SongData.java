package ca.brandonwade.windsong;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Object to contain information about an audio file.
 */
public class SongData {
    // Required
    private final String songTitle;

    // Optional
    private final String albumArtLocation;
    private final String albumArtist;
    private final String albumName;

    public static class Builder {
        // Required
        private final String songTitle;

        // Optional
        private String albumArt;
        private String albumArtist;
        private String albumName;

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
        albumArtLocation = builder.albumArt;
        albumArtist = builder.albumArtist;
        albumName   = builder.albumName;
    }

    public String getSongTitle() {
        return this.songTitle;
    }

    public String getAlbumArtLocation() {
        return this.albumArtLocation;
    }

    public Bitmap getAlbumArtBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(albumArtLocation, options);
        int imgHeight = options.outHeight;
        int imgWidth = options.outWidth;
        int inSampleSize = 1;
        int iconHeight = 100;
        int iconWidth = 100;

        // Determine a sample size for scaling down the image (if necessary)
        if (imgHeight > iconHeight || imgWidth > iconWidth) {
            final int halfHeight = imgHeight / 2;
            final int halfWidth = imgWidth / 2;

            while ((halfHeight / inSampleSize) > iconHeight && (halfWidth / inSampleSize) > iconWidth) {
                inSampleSize *= 2;
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(albumArtLocation, options);
    }

    public String getAlbumArtist() {
        return this.albumArtist;
    }

    public String getAlbumName() {
        return this.albumName;
    }
}
