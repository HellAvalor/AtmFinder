package com.andreykaraman.atmfinderukraine;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.andreykaraman.atmfinderukraine.adapters.CategoryAdapter;
import com.andreykaraman.atmfinderukraine.adapters.SubCategoryAdapter;
import com.andreykaraman.atmfinderukraine.model.Category;
import com.andreykaraman.atmfinderukraine.model.SubCategory;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    UpdateButtonClicked mCallback;
    private List<Category> categoriesList = new ArrayList<Category>();
    private List<SubCategory> subCategoriesList = new ArrayList<SubCategory>();
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;
    private SeekBar seekBarRadius;
    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;
    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearLayout;
    private Spinner spinnerCategories;
    private Spinner spinnerSubCategories;
    private Button updateButton;
    //   private ListView mDrawerListView;
    private View mFragmentContainerView;
    //    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
//            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        spinnerCategories = (Spinner) mLinearLayout.findViewById(R.id.spinnerCategories);
        spinnerSubCategories = (Spinner) mLinearLayout.findViewById(R.id.spinnerSubCategories);
        seekBarRadius = (SeekBar) mLinearLayout.findViewById(R.id.seekBarSearchRadius);
        updateButton = (Button) mLinearLayout.findViewById(R.id.buttonUpdateAtm);
        updateButton.setOnClickListener(new UpdateButtonListener());
//        mDrawerListView = (ListView) inflater.inflate(
//                R.layout.fragment_navigation_drawer, container, false);
//        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectItem(position);
//            }
//        });
//        mDrawerListView.setAdapter(new ArrayAdapter<String>(
//                getActionBar().getThemedContext(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                new String[]{
//                        getString(R.string.title_section1),
//                        getString(R.string.title_section2),
//                        getString(R.string.title_section3),
//                }
//        ));
//        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
//        return mDrawerListView;
        return mLinearLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final SimpleAdapter simpleAdapter =
//                new SimpleAdapter(getActivity(), data,
//                        android.R.layout.simple_spinner_item, from, to);
//        simpleAdapter.setDropDownViewResource(
//                android.R.layout.simple_spinner_dropdown_item);
//
//
//        spinnerCategories.setAdapter(simpleAdapter);
        categoriesList = new ArrayList<Category>();
        categoriesList.add(new Category(0, "loading..."));
        categoryAdapter = new CategoryAdapter(getActivity(), categoriesList);
        spinnerCategories.setPrompt("ATM Category");
        spinnerCategories.setAdapter(categoryAdapter);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get the color name out of the Map
//                final Map<String, String> data =
//                        (Map<String, String>) parent.getItemAtPosition(position);
//                final String text = "Selected Color:-  " + data.get("color");
                updateSubCategories(id);
                Toast.makeText(parent.getContext(), categoryAdapter.getItem(position).getCategoryName(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });


        subCategoriesList = new ArrayList<SubCategory>();
        subCategoriesList.add(new SubCategory(0, "loading..."));
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), subCategoriesList);
        spinnerSubCategories.setPrompt("ATM SubCategory");
        spinnerSubCategories.setAdapter(subCategoryAdapter);
        spinnerSubCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get the color name out of the Map
//                final Map<String, String> data =
//                        (Map<String, String>) parent.getItemAtPosition(position);
//                final String text = "Selected Color:-  " + data.get("color");

                Toast.makeText(parent.getContext(), subCategoryAdapter.getItem(position).getSubCategoryName(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });

        updateCategories();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    private Map<String, String> addData(String colorName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("color", colorName);
        return map;
    }

    private void updateCategories() {

        Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "http://notacash.com/api/categories/";
//            String url ="https://www.googleapis.com/customsearch/v1?key=AIzaSyBmSXUzVZBKQv9FJkTpZXn0dObKgEQOIFU&cx=014099860786446192319:t5mr0xnusiy&q=AndroidDev&alt=json&searchType=image";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub
                Log.d("onResponse", "Category Response => " + response.toString());
                Toast.makeText(getActivity(), "onResponce", Toast.LENGTH_SHORT).show();

                if (response != null) {
                    int len = response.length();
                    categoriesList.clear();
                    for (int i = 0; i < len; i++) {
                        JSONObject jsonProductObject = null;
                        try {
                            jsonProductObject = response.getJSONObject(i);
                            Category cat = new Category();
                            cat.set_id(jsonProductObject.getInt("Id"));
                            cat.setCategoryName(jsonProductObject.getString("Name"));
                            categoriesList.add(cat);
                            categoryAdapter = new CategoryAdapter(getActivity(), categoriesList);
                            spinnerCategories.setAdapter(categoryAdapter);
                            Log.d("onResponse", "Id => " + cat.get_id() + " Name=> " + cat.getCategoryName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
//                txtDisplay.setText("Response => "+response.toString());
//                findViewById(R.id.progressBar1).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                categoriesList.clear();
                categoriesList.add(new Category(0, "loading..."));
                categoryAdapter = new CategoryAdapter(getActivity(), categoriesList);
                spinnerCategories.setAdapter(categoryAdapter);
                // TODO Auto-generated method stub
                Log.d("onErrorResponse", "Category error => " + error);
            }
        });

        queue.add(jsObjRequest);
    }

    private void updateSubCategories(long id) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://notacash.com/api/category/" + id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("onResponse", "Subcategory Response => " + response.toString());

                if (response != null) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = response.getJSONArray("Subcategories");
                        subCategoriesList.clear();
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            Log.d("onResponse", "Subcategory len => " + len);
                            JSONObject jsonProductObject = null;
                            try {
                                jsonProductObject = jsonArray.getJSONObject(i);
                                SubCategory cat = new SubCategory();
                                cat.set_id(jsonProductObject.getInt("Id"));
                                cat.setSubCategoryName(jsonProductObject.getString("Name"));
                                subCategoriesList.add(cat);
                                subCategoryAdapter = new SubCategoryAdapter(getActivity(), subCategoriesList);
                                spinnerSubCategories.setAdapter(subCategoryAdapter);
                                Log.d("onResponse", "Id => " + cat.get_id() + " Name=> " + cat.getSubCategoryName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                subCategoriesList.clear();
                subCategoriesList.add(new SubCategory(0, "loading..."));
                subCategoryAdapter = new SubCategoryAdapter(getActivity(), subCategoriesList);
                spinnerSubCategories.setAdapter(subCategoryAdapter);
                // TODO Auto-generated method stub
                Log.d("onErrorResponse", "Subcategory error => " + error);
            }
        });

        queue.add(jsObjRequest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (UpdateButtonClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateButtonClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    public interface UpdateButtonClicked {
        public void sendUpdate(int categoryId, int subcategoryId, int radius);
    }

    public class UpdateButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            mCallback.sendUpdate(((Category) spinnerCategories.getSelectedItem()).get_id(), ((SubCategory) spinnerSubCategories.getSelectedItem()).get_id(), seekBarRadius.getProgress());
            Log.d("UpdateButtonListener", "Data=> " + seekBarRadius.getProgress() + " category " + ((Category) spinnerCategories.getSelectedItem()).getCategoryName() + " subCategory " + ((SubCategory) spinnerSubCategories.getSelectedItem()).getSubCategoryName());
        }
    }

}
