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

/**
 * Activity for handling the dispay of the SongList.
 */
public class SongListActivity extends Activity {

    private Cursor mediaCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        // Read external audio on device & filter all non-music audio (e.g. Notification sounds)
        ContentResolver resolver = getContentResolver();
        Uri externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String queryFilter = MediaStore.Audio.Media.IS_MUSIC + "=1";
        String orderBy = MediaStore.Audio.Media.TITLE + " ASC";
        mediaCursor = resolver.query(externalUri, null, queryFilter, null, orderBy);

        // TODO: See if there's a more efficient way than converting to an Array
        ArrayList<SongData> songs = processDeviceSongs(mediaCursor);
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

    public ArrayList<SongData> processDeviceSongs(Cursor cursor) {
        ArrayList<SongData> songList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                String songTitle = cursor.getString(titleColumn);
                String albumArtist = cursor.getString(artistColumn);
                String albumName = cursor.getString(albumColumn);

                SongData song = new SongData.Builder(songTitle)
                        .albumArtist(albumArtist)
                        .albumName(albumName)
                        .build();

                songList.add(song);
            } while (cursor.moveToNext());
        }
        return songList;
    }
}
