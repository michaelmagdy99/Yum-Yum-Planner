    package com.example.yumyumplanner.home.meal_details;
    
    import android.os.Bundle;
    
    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.example.yumyumplanner.R;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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
        private ImageButton favBtn;
        private ImageButton backBtn;

        private Boolean favFalg = false;

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

            List<String> ingredientsList = null;
            List<String> meaurseList =null;
            String mealid = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getIdMeal();

            String mealName = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrMeal();
            String categoryName = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrCategory();
            String country = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrArea();
            String instruction = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrInstructions();
            String image = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrMealThumb();
            String urlVideo = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrYoutube();
            ingredientsList = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getAllIngredients();
            meaurseList = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getAllMeaurse();

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



            layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            ingtrdientsAdapter = new IngrdientsAdapter(getContext(), ingredientsList, meaurseList);
            ingradientRecyclerView.setLayoutManager(layoutManager);
            ingradientRecyclerView.setAdapter(ingtrdientsAdapter);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigateUp();
                }
            });



            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favFalg) {
                        favBtn.setImageDrawable(getResources().getDrawable(R.drawable.faviourte));
                    } else {
                        favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    }
                    favFalg =! favFalg;
                }
            });


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
            backBtn = view.findViewById(R.id.back_btn_deatils);
            favBtn = view.findViewById(R.id.fav_btn_deatils);
        }
    
        public String getId(String link) {
                if (link != null && link.split("\\?v=").length > 1)
                    return link.split("\\?v=")[1];
                else return "";
            }
    
    }