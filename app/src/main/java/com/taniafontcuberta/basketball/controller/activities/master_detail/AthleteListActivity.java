package com.taniafontcuberta.basketball.controller.activities.master_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taniafontcuberta.basketball.R;
import com.taniafontcuberta.basketball.controller.activities.add_edit.AddEditActivity;
import com.taniafontcuberta.basketball.controller.activities.add_edit.AddEditTeamActivity;
import com.taniafontcuberta.basketball.controller.activities.login.LoginActivity;
import com.taniafontcuberta.basketball.controller.managers.AthleteCallback;
import com.taniafontcuberta.basketball.controller.managers.AthleteManager;
import com.taniafontcuberta.basketball.model.Athlete;


import java.util.List;

public class AthleteListActivity extends AppCompatActivity implements AthleteCallback{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private List<Athlete> athletes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),AddEditActivity.class);
                intent.putExtra("type","add");
                startActivityForResult(intent, 0);
            }
        });
        FloatingActionButton addTeam = (FloatingActionButton) findViewById(R.id.add);
        addTeam.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(v.getContext(),AddEditTeamActivity.class);
                intent.putExtra("type","add");
                startActivityForResult(intent, 0);
                return false;
            }

        });

        FloatingActionButton searchName = (FloatingActionButton) findViewById(R.id.topName);
        searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PlayerTopActivity.class);
                intent.putExtra("id", "name");
                startActivityForResult(intent, 0);
            }
        });
        FloatingActionButton topBaskets = (FloatingActionButton) findViewById(R.id.topBaskets);
        topBaskets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PlayerTopActivity.class);
                intent.putExtra("id", "baskets");
                startActivityForResult(intent, 0);
            }
        });
        FloatingActionButton topBirthDate = (FloatingActionButton) findViewById(R.id.topFechaNacimiento);
        topBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PlayerTopActivity.class);
                intent.putExtra("id", "birthdate");
                startActivityForResult(intent, 0);
            }
        });

        topBirthDate.setOnLongClickListener(new View.OnLongClickListener() {
                                                @Override
                                                public boolean onLongClick(View view) {
                                                    Intent intent = new Intent(view.getContext(),PlayerTopBetweenActivity.class);
                                                    intent.putExtra("id", "birthdate2");
                                                    startActivityForResult(intent, 0);
                                                    return false;
                                                }
                                            }

        );


        recyclerView = (RecyclerView) findViewById(R.id.player_list);
        assert recyclerView != null;

        if (findViewById(R.id.player_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AthleteManager.getInstance().getAllAthletes(AthleteListActivity.this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.i("setupRecyclerView", "                     " + athletes);
        recyclerView.setAdapter(new AthleteListActivity.SimpleItemRecyclerViewAdapter(athletes));
    }

    @Override
    public void onSuccess(List<Athlete> athletesList) {
        athletes = athletesList;
        setupRecyclerView(recyclerView);
    }

    @Override
    public void onSucces() {

    }


    @Override
    public void onFailure(Throwable t) {
        Intent i = new Intent(AthleteListActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<AthleteListActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Athlete> mValues;

        public SimpleItemRecyclerViewAdapter(List<Athlete> items) {
            mValues = items;
        }

        @Override
        public AthleteListActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.player_list_content, parent, false);
            return new AthleteListActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final AthleteListActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getId().toString());
            holder.mContentView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                        PlayerDetailFragment fragment = new PlayerDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.player_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PlayerDetailActivity.class);
                        intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Athlete mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
