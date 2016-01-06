package ca.brandonwade.windsong;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom adapter used to convert SongData objects to display in a ListView.
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

            holder.icon = (ImageView) convertView.findViewById(R.id.row_icon);
            holder.mainText = (TextView) convertView.findViewById(R.id.row_main_text);
            holder.subText = (TextView) convertView.findViewById(R.id.row_sub_text);

            convertView.setTag(holder);
        }
        else
        {
            holder = (SongDataHolder) convertView.getTag();
        }

        SongData song = data[position];
//        holder.icon.setImageResource(R.drawable.icon); // TODO: Fix this to work with album art
        holder.mainText.setText(song.getSongTitle());
        holder.subText.setText(song.getAlbumArtist() + " - " + song.getAlbumName());

        return convertView;
    }

    static class SongDataHolder
    {
        ImageView icon;
        TextView mainText;
        TextView subText;
    }
}
