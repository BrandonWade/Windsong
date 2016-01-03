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


public class SongListActivity extends Activity {

    private Cursor externalCursor;
    private Cursor internalCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

//        String[] columns = { MediaStore.Audio.Albums._ID,
//                             MediaStore.Audio.Albums.ALBUM };
//
//        // Read all songs on device (internal & external storage)
//        externalCursor = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null, null, null);
//        internalCursor = managedQuery(MediaStore.Audio.Albums.INTERNAL_CONTENT_URI, columns, null, null, null);

        ContentResolver resolver = getContentResolver();
        Uri externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri internalUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

        externalCursor = resolver.query(externalUri, null, null, null, null);
        internalCursor = resolver.query(internalUri, null, null, null, null);

        ArrayList<SongData> songs = new ArrayList<>();
        songs.addAll(processDeviceSongs(externalCursor));
        songs.addAll(processDeviceSongs(internalCursor));

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
