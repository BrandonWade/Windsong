package ca.brandonwade.windsong;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class SongListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        SongData s1 = new SongData.Builder("Title1")
                .albumArtist("Artist1")
                .albumName("Name1")
                .build();

        SongData s2 = new SongData.Builder("Title2")
                .albumArtist("Artist2")
                .albumName("Name2")
                .build();

        SongData s3 = new SongData.Builder("Title3")
                .albumArtist("Artist3")
                .albumName("Name3")
                .build();

        SongData s4 = new SongData.Builder("Title4")
                .albumArtist("Artist4")
                .albumName("Name4")
                .build();

        SongData s5 = new SongData.Builder("Title5")
                .albumArtist("Artist5")
                .albumName("Name5")
                .build();

        SongData s6 = new SongData.Builder("Title6")
                .albumArtist("Artist6")
                .albumName("Name6")
                .build();

        SongData s7 = new SongData.Builder("Title7")
                .albumArtist("Artist7")
                .albumName("Name7")
                .build();

        SongData[] songData = { s1, s2, s3, s4, s5, s6, s7 };
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
}
