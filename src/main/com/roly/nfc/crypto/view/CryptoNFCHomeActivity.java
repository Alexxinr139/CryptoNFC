package com.roly.nfc.crypto.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roly.nfc.crypto.R;
import com.roly.nfc.crypto.view.nfc.TagWriterActivity;

public class CryptoNFCHomeActivity extends Activity {

	public static final int TAG_ERROR=-2;
	public static final int NOTE_ERROR=-1;
	public static final int NOTE_VALIDATED=1;
	public static final int TAG_VALIDATED=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        View add = findViewById(R.id.menu_add_note);
        View newKey = findViewById(R.id.menu_write_key);
        View listNotes = findViewById(R.id.menu_list_notes);
        View sourceCode = findViewById(R.id.menu_source_code);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        listNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewNotes();
            }
        });
        newKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeTag();
            }
        });
        sourceCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/OlivierGonthier/"));
                startActivity(intent);
            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.cryptonfc_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        case R.id.settings:
            viewPreferences();
            return true;
        default:
            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void viewPreferences(){
		startActivity(new Intent(this,CryptoNFCPreferenceActivity.class));
	}
	
	public void addNote(){
		Intent i = new Intent(this,EditNoteActivity.class);
		startActivityForResult(i, NOTE_VALIDATED);
	}


	public void viewNotes(){
		Intent i = new Intent(this,NoteListActivity.class);
		startActivity(i);
	}


	public void writeTag(){
		Intent i = new Intent(this,TagWriterActivity.class);
		startActivityForResult(i, NOTE_VALIDATED);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case NOTE_VALIDATED:
			Toast.makeText(this,"Note saved", Toast.LENGTH_SHORT).show();
			break;
		case TAG_VALIDATED:
			Toast.makeText(this,"Tag written", Toast.LENGTH_SHORT).show();
			break;
		case TAG_ERROR:
			Toast.makeText(this, "An error occured while writing key on tag", Toast.LENGTH_SHORT).show();
			break;
		case NOTE_ERROR:
			Toast.makeText(this, "An error occured while saving note", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
