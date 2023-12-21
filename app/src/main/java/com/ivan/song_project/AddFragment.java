package com.ivan.song_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddFragment extends Fragment {

    EditText songName, authorName, lyrics;
    Button add_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        songName = view.findViewById(R.id.songTitle);
        authorName = view.findViewById(R.id.authorTitle);
        lyrics = view.findViewById(R.id.lyrics);
        add_button = view.findViewById(R.id.save_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(getContext());
                myDB.addBook(songName.getText().toString().trim(),
                        authorName.getText().toString().trim(),
                        lyrics.getText().toString().trim());
                ((MainActivity) getActivity()).setFragment(new SongList());
            }
        });
        return view;
    }
}