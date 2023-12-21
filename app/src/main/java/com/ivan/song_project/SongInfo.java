package com.ivan.song_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SongInfo extends Fragment {

    TextView songTitle, songAuthor, songLyrics;

    DatabaseHelper db;

    private static final String SONG_ID = "song_id";

    private int songId;

    public SongInfo() {
    }

    public static SongInfo newInstance(int songId) {
        SongInfo fragment = new SongInfo();
        Bundle args = new Bundle();
        args.putInt(SONG_ID, songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songId = getArguments().getInt(SONG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);
        db = new DatabaseHelper(getContext());

        songTitle = view.findViewById(R.id.output_songTitle);
        songAuthor = view.findViewById(R.id.output_authorSong);
        songLyrics = view.findViewById(R.id.output_lyrics);

        Cursor cursor = db.readOneData(songId);

        if (cursor != null && cursor.moveToFirst()) {
            songTitle.setText(cursor.getString(cursor.getColumnIndex("song_title")));
            songAuthor.setText(cursor.getString(cursor.getColumnIndex("song_author")));
            songLyrics.setText(cursor.getString(cursor.getColumnIndex("song_lyrics")));
        }

        return view;
    }
}