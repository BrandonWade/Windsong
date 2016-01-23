package ca.brandonwade.windsong;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity for handling the dispay of the SongList.
 */
public class SongListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        ContentResolver resolver = getContentResolver();
        Cursor mediaCursor;
        Cursor artCursor;
        HashMap<String, String> albumArtTable;

        // Read external audio on device & filter all non-music audio (e.g. Notification sounds)
        Uri externalSongUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String songQueryFilter = MediaStore.Audio.Media.IS_MUSIC + "=1";
        String orderBy = MediaStore.Audio.Media.TITLE + " ASC";
        mediaCursor = resolver.query(externalSongUri, null, songQueryFilter, null, orderBy);

        // Read album art from device
        Uri externalArtUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String artQueryFilter = "";
        String[] artProjection = { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART };
        artCursor = resolver.query(externalArtUri, artProjection, artQueryFilter, null, null);
        albumArtTable = buildAlbumArtMap(artCursor);

        // TODO: See if there's a more efficient way than converting to an Array
        ArrayList<SongData> songs = processDeviceSongs(mediaCursor, albumArtTable);
        SongData[] songData = new SongData[songs.size()];
        songData = songs.toArray(songData);

        SongDataAdapter adapter = new SongDataAdapter(this, R.layout.song_list_row, songData);
        ListView list = (ListView) findViewById(R.id.song_list);
        View header = getLayoutInflater().inflate(R.layout.song_list_header, null);
        list.addHeaderView(header);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a hashmap using the album ID as the key and the album art as the value.
     *
     * @param artCursor Cursor containing album art from the MediaStore.
     * @return  A hashmap for finding the album art for a given album using it's ID.
     */
    public HashMap<String, String> buildAlbumArtMap(Cursor artCursor) {
        HashMap<String, String> albumArtMap = new HashMap<>();

        if (artCursor != null && artCursor.moveToFirst()) {
            int idColumn = artCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int artColumn = artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

            do {
                String songId = artCursor.getString(idColumn);
                String albumArt = artCursor.getString(artColumn);

                albumArtMap.put(songId, albumArt);
            } while (artCursor.moveToNext());
        }

        return albumArtMap;
    }

    /**
     * Creates a list of SongData objects based on the data from the MediaStore.
     *
     * @param cursor The Cursor containing audio information.
     * @param albumArtMap The hashmap for finding the album art for a song.
     * @return An ArrayList containing objects representing songs on the device.
     */
    public ArrayList<SongData> processDeviceSongs(Cursor cursor, HashMap<String, String> albumArtMap) {
        ArrayList<SongData> songList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do {
                String songTitle = cursor.getString(titleColumn);
                String albumArtist = cursor.getString(artistColumn);
                String albumName = cursor.getString(albumColumn);
                String albumArt = albumArtMap.get(cursor.getString(artColumn));

                SongData song = new SongData.Builder(songTitle)
                        .albumArtist(albumArtist)
                        .albumName(albumName)
                        .albumArt(albumArt)
                        .build();

                songList.add(song);
            } while (cursor.moveToNext());
        }
        return songList;
    }
}
