package com.juanborges.theguardiannews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StoryAdapter extends ArrayAdapter<Story> {

    public StoryAdapter(@NonNull Context context, List<Story> stories) {
        super(context, 0, stories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.story_list_item, parent, false);
        }

        Story currentStory = getItem(position);

        TextView title = convertView.findViewById(R.id.title);
        title.setText(currentStory.getTitle());

        TextView date = convertView.findViewById(R.id.date);
        date.setText(currentStory.getDate());

        TextView pillar = convertView.findViewById(R.id.pillar);
        pillar.setText(currentStory.getPillar());

        TextView section = convertView.findViewById(R.id.section);
        section.setText(currentStory.getSection());

        return  convertView;
    }
}
