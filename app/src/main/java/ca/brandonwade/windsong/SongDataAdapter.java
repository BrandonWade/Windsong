package ca.brandonwade.windsong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Brandon W on 2016-01-01.
 */
public class SongDataAdapter extends ArrayAdapter<SongData> {

    private LayoutInflater inflater;
    private SongData[] data;

    public SongDataAdapter(Context context, int layoutResourceId, SongData[] data) {
        super(context, layoutResourceId, data);

        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongDataHolder holder;

        if (convertView == null) {
            holder = new SongDataHolder();
            convertView = inflater.inflate(R.layout.song_list_row, null);

            holder.songTitle = (TextView) convertView.findViewById(R.id.row_main_text);
            holder.albumArt = (ImageView) convertView.findViewById(R.id.row_icon);
            holder.albumArtist = (TextView) convertView.findViewById(R.id.row_sub_text1);
            holder.albumName = (TextView) convertView.findViewById(R.id.row_sub_text2);

            convertView.setTag(holder);
        }
        else
        {
            holder = (SongDataHolder) convertView.getTag();
        }

        SongData song = data[position];
        holder.songTitle.setText(song.getSongTitle());
        holder.albumArt.setImageResource(0); // TODO: Fix this to work with album art
        holder.albumArtist.setText(song.getAlbumArtist());
        holder.albumName.setText(song.getAlbumName());

        return convertView;
    }

    static class SongDataHolder
    {
        TextView songTitle;
        ImageView albumArt;
        TextView albumArtist;
        TextView albumName;
    }
}
