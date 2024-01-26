    package com.example.yumyumplanner.home.meal_details;
    
    import android.os.Bundle;
    
    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.example.yumyumplanner.R;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

    import java.util.ArrayList;
    import java.util.List;

    public class MealDetailsFragment extends Fragment {
    
        View view;
        public static final String TAG = "meal deatils Fragment";
        private TextView nameMeal;
        private TextView countryMeal;
        private ImageView mealImage;
        private TextView categoryMeal;
    
        private TextView instrucationText;
        private YouTubePlayerView youTubePlayerView;
        private IngrdientsAdapter ingtrdientsAdapter;
        private RecyclerView ingradientRecyclerView;
        LinearLayoutManager layoutManager;

        public MealDetailsFragment() {
            // Required empty public constructor
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
        }
    
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_meal_details, container, false);
            intitUI(view);

            Bundle args = getArguments();
            List<String> ingredientsList = null;
            List<String> meaurseList =null;
            if (args != null) {

                String mealName = args.getString("mealName", "unknown");
                String categoryName = args.getString("categoryName", "UnKnown");
                String country = args.getString("countryMeal", "Unknown");
                String instruction = args.getString("instructionMeal", "UnKnown");
                String image = args.getString("imageMeal", "unknown");
                String urlVideo = args.getString("videoMeal", "unKnown");
                ingredientsList = args.getStringArrayList("ingredientsList");
                meaurseList = args.getStringArrayList("measureList");

                Log.i(TAG, "onCreateView: " + urlVideo);
                nameMeal.setText(mealName);
                categoryMeal.setText(categoryName);
                countryMeal.setText(country);
                instrucationText.setText(instruction);

                Glide.with(getContext())
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(mealImage);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = getId(urlVideo);
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }


            layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            ingtrdientsAdapter = new IngrdientsAdapter(getContext(), ingredientsList, meaurseList);
            ingradientRecyclerView.setLayoutManager(layoutManager);
            ingradientRecyclerView.setAdapter(ingtrdientsAdapter);

            return view;
        }
    
        private void intitUI(View view){
            mealImage = view.findViewById(R.id.image_meal_deatils);
            nameMeal = view.findViewById(R.id.name_meal_deatils);
            countryMeal = view.findViewById(R.id.country_meal_deatils);
            categoryMeal = view.findViewById(R.id.category_meal_deatils);
            youTubePlayerView = view.findViewById(R.id.youtube_player);
            instrucationText = view.findViewById(R.id.meal_instructions_view_deatils);
            ingradientRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        }
    
        public String getId(String link) {
                if (link != null && link.split("\\?v=").length > 1)
                    return link.split("\\?v=")[1];
                else return "";
            }
    
    }