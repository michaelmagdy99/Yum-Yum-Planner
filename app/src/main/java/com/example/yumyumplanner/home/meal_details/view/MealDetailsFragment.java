    package com.example.yumyumplanner.home.meal_details.view;
    
    import static android.app.ProgressDialog.show;

    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.os.Bundle;
    
    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.Navigation;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.provider.CalendarContract;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.example.yumyumplanner.R;
    import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
    import com.example.yumyumplanner.home.home.presenter.HomePresenter;
    import com.example.yumyumplanner.home.meal_details.preseter.MealDetailsPresenterInterface;
    import com.example.yumyumplanner.home.meal_details.preseter.MealDetailsPreserter;
    import com.example.yumyumplanner.model.data.MealCalendar;
    import com.example.yumyumplanner.model.data.MealsItem;
    import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
    import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;

    public class MealDetailsFragment extends Fragment implements MealsDetailsView{
    
        View view;
        private MealDetailsPreserter preserter;

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
        private Button addToCalender ;


        List<String> ingredientsList = null;
        List<String> meaurseList =null;
        String mealid , mealName , categoryName , country, instruction , image , urlVideo ;

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

            //preseteer init
            preserter = new MealDetailsPreserter(this,
                            HomeRepositryImp.getInstance(
                            MealsRemoteDataSourceImp.getInstance(),
                            MealsLocalDataSourceImp.getInstance(getContext())));



            mealid = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getIdMeal();
            mealName = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrMeal();
            categoryName = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrCategory();
            country = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrArea();
            instruction = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrInstructions();
            image = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrMealThumb();
            urlVideo = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getStrYoutube();
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
                        addMealToFav(MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails());
                    }
                    favFalg =! favFalg;
                }
            });

            addToCalender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            view.getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Toast.makeText(getContext(),String.valueOf(dayOfMonth)+ "-" + (month+1) + "-" + year, Toast.LENGTH_SHORT).show();
                            // Set the selected date to the Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            long startTime = calendar.getTimeInMillis();

                            Intent intent = new Intent(Intent.ACTION_INSERT);
                            intent.setData(CalendarContract.Events.CONTENT_URI);
                            intent.putExtra(CalendarContract.Events.TITLE, "Your Event Title");
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);

                            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "No app available to handle calendar events", Toast.LENGTH_SHORT).show();
                            }


                            MealCalendar mealCalendar = new MealCalendar();
                            mealCalendar.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            mealCalendar.date = String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year;
                            mealCalendar.setIdMeal(mealid);
                            mealCalendar.setStrArea(country);
                            mealCalendar.setStrMeal(mealName);
                            mealCalendar.setStrCategory(categoryName);
                            mealCalendar.setStrMealThumb(image);
                            mealCalendar.setStrInstructions(instruction);

                            mealCalendar.setAllIngredients(ingredientsList);
                            mealCalendar.setAllMeasures(meaurseList);

                            // add to room
                            addMealToCalendar(mealCalendar);
                        }
                    },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.show();
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
            addToCalender = view.findViewById(R.id.add_to_calender);
        }
    
        public String getId(String link) {
                if (link != null && link.split("\\?v=").length > 1)
                    return link.split("\\?v=")[1];
                else return "";
            }

        @Override
        public void addMealToFav(MealsItem mealsItem) {
            preserter.addToFav(mealsItem);

        }

        @Override
        public void addMealToCalendar(MealCalendar mealCalendar) {
            preserter.addToCalender(mealCalendar);
        }
    }