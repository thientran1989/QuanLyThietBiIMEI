/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package quanlythietbi.material.com.quanlythietbi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import quanlythietbi.material.com.quanlythietbi.R;
import quanlythietbi.material.com.quanlythietbi.activity.FlashScreenAc;
import quanlythietbi.material.com.quanlythietbi.adapter.Adapter_MBA;


public class BaiHat_Fragment extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

	SwipeRefreshLayout mSwipeRefreshLayout;
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mReAdapter;
	Adapter_MBA mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	RecyclerView recList;

	public static BaiHat_Fragment newInstance(String text){
		BaiHat_Fragment mFragment = new BaiHat_Fragment();
//		Bundle mBundle = new Bundle();
//		mBundle.putString(TEXT_FRAGMENT, text);
//		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_baihat, container, false);

//        TextView mTxtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
//        mTxtTitle.setText(getArguments().getString(TEXT_FRAGMENT));
		try{
			mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
			recList = (RecyclerView) rootView.findViewById(R.id.cardList);
			recList.setHasFixedSize(true);
			LinearLayoutManager llm = new LinearLayoutManager(getActivity());
			llm.setOrientation(LinearLayoutManager.VERTICAL);
			recList.setLayoutManager(llm);

			try {
				if(FlashScreenAc.my_list!=null){
					mAdapter = new Adapter_MBA(getActivity(), FlashScreenAc.my_list);
					recList.setAdapter(mAdapter);
				}
		} catch (Exception e) {
//			ThtShow.show_crouton_toast(this,"loi mo database", Style.ALERT);
		}
		}catch(Exception e){
			Toast.makeText(getActivity().getApplicationContext(), "loi giao dien :\n"+e.toString(), Toast.LENGTH_LONG).show();
		}

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);
        
        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		menu.findItem(R.id.menu_add).setVisible(true);

		mSearchCheck = false;	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case R.id.menu_add:
            Toast.makeText(getActivity(), R.string.add, Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_search:
			mSearchCheck = true;
			break;
		}
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
			   if(mAdapter!=null){
				   mAdapter.get_search(s);
			   }
           }
           return false;
       }
   };

}
