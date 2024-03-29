    package com.example.yumyumplanner.home.meal_details.view;
    

    import android.app.DatePickerDialog;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AlertDialog;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.LifecycleOwner;
    import androidx.lifecycle.Observer;
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
    import com.example.yumyumplanner.authentication.AuthenticationActivity;
    import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
    import com.example.yumyumplanner.home.HomeActivity;
    import com.example.yumyumplanner.home.home.presenter.HomePresenter;
    import com.example.yumyumplanner.home.meal_details.preseter.MealDetailsPresenterInterface;
    import com.example.yumyumplanner.home.meal_details.preseter.MealDetailsPreserter;
    import com.example.yumyumplanner.model.data.FilterItem;
    import com.example.yumyumplanner.model.data.MealCalendar;
    import com.example.yumyumplanner.model.data.MealDescriptionInGoogle;
    import com.example.yumyumplanner.model.data.MealsItem;
    import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
    import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
    import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
    import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;

    public class MealDetailsFragment extends Fragment implements MealsDetailsView{
    
        View view;
        private MealDetailsPreserter preserter;
        private ProgressDialog progressDialog;

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

        Boolean favFalg = false;
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
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //showProgressBar();
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
                            MealsLocalDataSourceImp.getInstance(getContext())),
                            FilterRepoImp.getInstance(MealsRemoteDataSourceImp.getInstance()),
                            BackUpDataSourceImp.getInstance(getContext())
                    );


            MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
            MealsItem mealsItem = args.getMealDetails();
            MealCalendar mealCalendar = args.getMealCalender();
            FilterItem filterItem = args.getFilterItem();

            if (mealsItem != null) {
                // arga from home
                handleArgumentFromHomeFragment(mealsItem);
            } else if (mealCalendar != null) {
                // args from cal
                handleArgumentFromCalendarFragment(mealCalendar);
                favBtn.setVisibility(View.GONE);
                addToCalender.setVisibility(View.GONE);
            } else if (filterItem != null){
              //args from meal search
                preserter.getMealDetails(filterItem.getIdMeal());
                favBtn.setVisibility(View.GONE);
            } else {
                // No arguments are present
                handleNoArguments();
            }

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigateUp();
                }
            });

            getMealFromId(mealid);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HomeActivity.isGuestMode){
                        showGuestModeMessage();
                    }
                    else{
                        if (favFalg) {
                            favBtn.setImageDrawable(getResources().getDrawable(R.drawable.faviourte));
                            deleteMeals(MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails());
                        } else {
                            favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                            addMealToFav(MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails());
                        }
                        favFalg = !favFalg;
                    }
                }
            });

            addToCalender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (HomeActivity.isGuestMode) {
                        showGuestModeMessage();
                    } else {

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.clear(Calendar.MINUTE);
                            calendar.clear(Calendar.SECOND);
                            calendar.clear(Calendar.MILLISECOND);
                            long minDate = calendar.getTimeInMillis();
                            calendar.add(Calendar.DAY_OF_WEEK, 6);
                            long maxDate = calendar.getTimeInMillis();

                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    view.getContext(),
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                            Toast.makeText(getContext(), String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                                            // Set the selected date to the Calendar instance
                                            calendar.set(Calendar.YEAR, year);
                                            calendar.set(Calendar.MONTH, month);
                                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                            MealCalendar mealCalendar = new MealCalendar();
                                            mealCalendar.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                            mealCalendar.date = String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year;
                                            mealCalendar.setIdMeal(mealid);
                                            mealCalendar.setStrArea(country);
                                            mealCalendar.setStrMeal(mealName);
                                            mealCalendar.setStrCategory(categoryName);
                                            mealCalendar.setStrMealThumb(image);
                                            mealCalendar.setStrYoutube(String.valueOf(youTubePlayerView));
                                            mealCalendar.setStrInstructions(instruction);
                                            mealCalendar.setAllIngredients(ingredientsList);
                                            mealCalendar.setAllMeasures(meaurseList);
                                            // add to room
                                            addMealToCalendar(mealCalendar);

                                            Intent intent = new Intent(Intent.ACTION_INSERT)
                                                    .setData(CalendarContract.Events.CONTENT_URI)
                                                    .putExtra(CalendarContract.Events.TITLE, mealCalendar.getStrMeal())
                                                    .putExtra(CalendarContract.Events.DESCRIPTION, MealDescriptionInGoogle.getMealString(mealCalendar));
                                            startActivity(intent);
                                        }
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_WEEK)
                            );
                            datePickerDialog.getDatePicker().setMinDate(minDate);
                            datePickerDialog.getDatePicker().setMaxDate(maxDate);
                            datePickerDialog.show();
                        }
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


            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait... It is downloading");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
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

        @Override
        public void showEmptyDataMessage() {
            hideProgressBar();
        }

        @Override
        public void showData(List<MealsItem> mealsItems) {
            hideProgressBar();
            mealid = mealsItems.get(0).getIdMeal();
            mealName = mealsItems.get(0).getStrMeal();
            categoryName =mealsItems.get(0).getStrCategory();
            country = mealsItems.get(0).getStrArea();
            instruction = mealsItems.get(0).getStrInstructions();
            image = mealsItems.get(0).getStrMealThumb();
            urlVideo = mealsItems.get(0).getStrYoutube();
            ingredientsList = mealsItems.get(0).getAllIngredients();
            meaurseList = mealsItems.get(0).getAllMeaurse();

            updateUI();
        }

        @Override
        public void showErrorMsg(String error) {
            hideProgressBar();
            Toast.makeText(getContext(),  error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void deleteMeals(MealsItem mealsItem) {
            preserter.removeFromFav(mealsItem);
        }


        private void handleArgumentFromHomeFragment(MealsItem mealsItem) {
            mealid = mealsItem.getIdMeal();
            mealName = mealsItem.getStrMeal();
            categoryName = mealsItem.getStrCategory();
            country = mealsItem.getStrArea();
            instruction = mealsItem.getStrInstructions();
            image = mealsItem.getStrMealThumb();
            urlVideo = mealsItem.getStrYoutube();
            ingredientsList = mealsItem.getAllIngredients();
            meaurseList = mealsItem.getAllMeaurse();

            updateUI();
        }

        private void handleArgumentFromCalendarFragment(MealCalendar mealCalendar) {
            mealid = mealCalendar.getIdMeal();
            mealName = mealCalendar.getStrMeal();
            categoryName = mealCalendar.getStrCategory();
            country = mealCalendar.getStrArea();
            instruction = mealCalendar.getStrInstructions();
            image = mealCalendar.getStrMealThumb();
            urlVideo = mealCalendar.getStrYoutube();
            ingredientsList = mealCalendar.getAllIngredients();
            meaurseList = mealCalendar.getAllMeaurse();

            updateUI();
        }

        private void handleNoArguments() {
            Toast.makeText(getContext(), "There are some woring in data", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
        }

        private void updateUI(){
            Log.i(TAG, "onCreateView: " + urlVideo);
            nameMeal.setText(mealName);
            categoryMeal.setText(categoryName);
            countryMeal.setText(country);
            instrucationText.setText(instruction);

            Glide.with(getContext())
                    .load(image)
                    .centerCrop()
                    .placeholder(R.drawable.cooking)
                    .into(mealImage);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = getId(urlVideo);
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });

            layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            ingtrdientsAdapter = new IngrdientsAdapter(getContext(), ingredientsList, meaurseList);
            ingradientRecyclerView.setLayoutManager(layoutManager);
            ingradientRecyclerView.setAdapter(ingtrdientsAdapter);

        }

        private void showProgressBar() {
            HomeActivity.showLoadingAnimation();
        }

        private void hideProgressBar() {
            HomeActivity.hideLoadingAnimation();
        }

        @Override
        public void onPause() {
            super.onPause();
            youTubePlayerView.release();
        }

        public void showGuestModeMessage() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sign Up For More Features");
            builder.setMessage("Add your food preferences, shop your recipes, plan your meals and more!");

            builder.setPositiveButton("SIGN UP", (dialog, which) -> {
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                //getActivity().finish();
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }

        public void getMealFromId(String id){
            preserter.getMealById(id).observe(getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
                @Override
                public void onChanged(List<MealsItem> mealsItems) {
                    if(mealsItems != null && !mealsItems.isEmpty()){
                        favFalg = true;
                        favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    }else{
                        favFalg = false;
                        favBtn.setImageDrawable(getResources().getDrawable(R.drawable.faviourte));
                    }
                }
            });
        }

    }