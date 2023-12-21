package com.ivan.song_project;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Console;
import java.util.ArrayList;

public class SongList extends Fragment {

    DatabaseHelper db;
    ArrayList<String> songId, songTitle, songAuthor;

    FloatingActionButton deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        LinearLayout mainLinearLayout = view.findViewById(R.id.main_linear_song_list);

        db = new DatabaseHelper(view.getContext());

        songId = new ArrayList<>();
        songTitle = new ArrayList<>();
        songAuthor = new ArrayList<>();

        storeDataInArrays();

        for (int i = 0; i < songId.size(); i++) {
            createLayout(view, mainLinearLayout, songTitle.get(i), songAuthor.get(i), Integer.parseInt(songId.get(i)));
        }

        return view;
    }

    private void createLayout(View view, LinearLayout mainLinearLayout, String songTitle, String authorSong, int id_number) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(id_number);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (getResources().getDisplayMetrics().density * 60)
        );

        layoutParams.setMargins(20, 20, 20, 20);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView songTitleTextView = createTextView(songTitle, id_number);
        TextView authorSongTextView = createTextView(authorSong, id_number);

        FloatingActionButton floatingActionButton = new FloatingActionButton(getContext());
        floatingActionButton.setLayoutParams(new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().density * 60),
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        floatingActionButton.setImageResource(R.drawable.delete_icon);
        floatingActionButton.setClickable(true);

        LinearLayout parentContainer = view.findViewById(R.id.main_linear_song_list);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOneRow(id_number);
                parentContainer.removeView(linearLayout);
            }
        });

        linearLayout.addView(songTitleTextView);
        linearLayout.addView(authorSongTextView);
        linearLayout.addView(floatingActionButton);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setShowSongInfo(id_number);
            }
        });

        linearLayout.setBackgroundResource(R.drawable.main_background);
        linearLayout.setLayoutParams(layoutParams);
        mainLinearLayout.addView(linearLayout);
    }

    private TextView createTextView(String text, int id_number) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().density * 160),
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setId(id_number);
        return textView;
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                songId.add(cursor.getString(0));
                songTitle.add(cursor.getString(1));
                songAuthor.add(cursor.getString(2));
            }
        }
    }
}