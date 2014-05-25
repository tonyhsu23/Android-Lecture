package main.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.two, container, false);
		
		TextView text = (TextView) view.findViewById(R.id.textView);
		text.setText("This page is the second fragment, tab 2");
		
		return view;	
	}

}
