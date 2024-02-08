package com.example.yumyumplanner.remote.firebase.backup;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.yumyumplanner.database.MealDAO;
import com.example.yumyumplanner.database.MealDatabase;
import com.example.yumyumplanner.database.MealsLocalDataSource;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.HomeActivity;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
import com.example.yumyumplanner.remote.firebase.authentication.UserFirebaseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackUpDataSourceImp implements BackUpFirebaseDataSource {

    private UserFirebaseModel userFirebaseModel;

    private FirebaseAuth auth;
    private  FirebaseFirestore firestore;
    private StorageReference storageReference;

    private static BackUpDataSourceImp backUpRepositoryImp;
    private Context context;
    private  FirebaseUser currentUser;

    public static synchronized BackUpDataSourceImp getInstance(Context context) {
        if (backUpRepositoryImp == null) {
            backUpRepositoryImp = new BackUpDataSourceImp(context);
        }
        return backUpRepositoryImp;
    }

    private BackUpDataSourceImp(Context context) {
        this.context = context;
        this.userFirebaseModel = UserFirebaseModel.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void logOut() {
        auth.signOut();
    }

    @Override
    public void getUserData(UserDataCallback callback) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String userName = documentSnapshot.getString("name");
                            String userEmail = documentSnapshot.getString("email");
                            String userProfileImage = documentSnapshot.getString("profileImage");
                            UserProfile user = new UserProfile(userName, userEmail, userProfileImage);
                            callback.onSuccess(user);
                            Log.e("TAG", "getUserData: Done for getting user data");
                        } else {
                            Log.e("TAG", "getUserData: Done by no current for getting user data");
                            callback.onFailure("No Data Founded");
                        }
                    }).addOnFailureListener(
                            e -> {
                                callback.onFailure(e.getMessage());
                                Log.e("TAG", "getUserData: Error getting user data", e);
                            }

                    );
        } else {
            callback.onFailure("Must Login First to can get Data");
        }

    }

    @Override
    public void saveUserData(UserProfile userProfile, Uri imageUri) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            userProfile.setUserId(userId);

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", userProfile.getName());
            userData.put("email", userProfile.getEmail());

            if (imageUri != null) {
                StorageReference fileReference = storageReference
                        .child("profile_images")
                        .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                userProfile.setProfileImageURL(imageUrl);
                                userData.put("profileImage", imageUrl);
                                saveUserDataToFirestore(userId, userData);
                                Log.i("TAG", "saveUserData: " + userData);
                                Log.i("TAG", "saveUserData: image " + imageUrl);
                                Log.i("TAG", "saveUserData: clss " + userProfile.getProfileImageURL());
                            });
                        })
                        .addOnFailureListener(e -> {
                            Log.i("TAG", "saveUserData: ");
                        });
            } else {
                saveUserDataToFirestore(userId, userData);
            }
        } else {

        }
    }

    private void saveUserDataToFirestore(String userId, Map<String, Object> userData) {
        firestore.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {

                    Log.i("TAG", "saveUserDataToFirestore: success");
                })
                .addOnFailureListener(e -> {
                    Log.i("TAG", "saveUserDataToFirestore: failed");
                });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

        public void uploadMeals(MealsItem meals, String userId) {
            CollectionReference userMealsCollection = firestore.collection("users")
                    .document(userId).collection("meals");

            Map<String, Object> mealMap = new HashMap<>();
            mealMap.put("idMeal", meals.getIdMeal());
            mealMap.put("strMeal", meals.getStrMeal());
            mealMap.put("strCategory", meals.getStrCategory());
            mealMap.put("strInstructions", meals.getStrInstructions());
            mealMap.put("strMealThumb", meals.getStrMealThumb());
            mealMap.put("strYoutube", meals.getStrYoutube());
            mealMap.put("strArea", meals.getStrArea());
            mealMap.put("mealId", meals.getIdMeal());
            mealMap.put("ingredients", meals.getAllIngredients());
            mealMap.put("measures", meals.getAllMeaurse());

            userMealsCollection.add(mealMap)
                    .addOnSuccessListener(documentReference -> {
                        String mealId = documentReference.getId();
                        Log.i("TAG", "uploadMeals: " + documentReference.getId());
                        meals.setMealIdInFirabse(mealId);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", "uploadMeals: failed", e);
                    });
        }

        public void retrieveMeals(String userId, MealsBackUpCallBack callBack) {
            CollectionReference userMealsCollection = firestore.collection("users")
                    .document(userId).collection("meals");

            userMealsCollection.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealsItem> mealsList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            MealsItem meal = documentSnapshot.toObject(MealsItem.class);
                            meal.setMealIdInFirabse(documentSnapshot.getId());
                            mealsList.add(meal);
                            Log.i("TAG", "retrieveMeals: " + documentSnapshot.getId());
                        }
                        callBack.onSuccess(mealsList);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", "retrieveMeals: failed", e);
                        callBack.onFailure(e.getMessage());
                    });
        }

        public void deleteMeal(String userId, String mealId, DeleteMealCallback callback) {
            if (userId == null || mealId == null) {
                callback.onFailure("User ID or meal ID is null");
                return;
            }
            DocumentReference mealDocumentRef = firestore.collection("users").document(userId)
                    .collection("meals").document(mealId);

            Query query = firestore.collection("users").document(userId)
                    .collection("meals")
                    .whereEqualTo("idMeal", mealId);

            query.get()
                    .addOnSuccessListener(queryDocumentSnapshots  -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots ) {
                            // Delete the document(s) found by the query
                            documentSnapshot.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Log.i("TAG", "deleteMeal: success");
                                        callback.onSuccess();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("TAG", "deleteMeal: failed", e);
                                        callback.onFailure(e.getMessage());
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", "Error checking document existence", e);
                        callback.onFailure(e.getMessage());
                    });

        }

        //backup calendar data
        public void uploadPlanMeals(MealCalendar meals, String userId) {
            CollectionReference userMealsCollection = firestore.collection("users")
                    .document(userId).collection("meals_of_plan");

            Map<String, Object> mealMap = new HashMap<>();
            mealMap.put("idMeal", meals.getIdMeal());
            mealMap.put("strMeal", meals.getStrMeal());
            mealMap.put("strCategory", meals.getStrCategory());
            mealMap.put("strInstructions", meals.getStrInstructions());
            mealMap.put("strMealThumb", meals.getStrMealThumb());
            mealMap.put("strYoutube", meals.getStrYoutube());
            mealMap.put("strArea", meals.getStrArea());
            mealMap.put("mealId", meals.getIdMeal());
            mealMap.put("ingredients", meals.getAllIngredients());
            mealMap.put("measures", meals.getAllMeaurse());
            mealMap.put("date", meals.date);
            mealMap.put("dateOfWeek", meals.dayOfWeek);

            userMealsCollection.add(mealMap)
                    .addOnSuccessListener(documentReference -> {
                        Log.i("TAG", "uploadMeals: ");
                       // meals.setMealIdInFirabse(documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", "uploadMeals: failed", e);
                    });
        }


    public  void retrievePlanMeals(String userId, MealPlanCallBack callBack) {
        CollectionReference userMealsCollection = firestore.collection("users")
                .document(userId).collection("meals_of_plan");

//        Query query = userMealsCollection.whereEqualTo("date", date);

        userMealsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<MealCalendar> mealsList = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        MealCalendar meal = documentSnapshot.toObject(MealCalendar.class);
                        meal.setMealIdInFirabse(documentSnapshot.getId());
                        mealsList.add(meal);
                    }
                    callBack.onSuccess(mealsList);
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "retrieveMeals: failed", e);
                    callBack.onFailure(e.getMessage());
                });
    }


    public void deleteMealOfPlan(String userId, String mealId, DeleteMealCallback callback) {
        if (userId == null || mealId == null) {
            callback.onFailure("User ID or meal ID is null");
            return;
        }
        DocumentReference mealDocumentRef = firestore.collection("users").document(userId)
                .collection("meals_of_plan").document(mealId);

        Query query = firestore.collection("users").document(userId)
                .collection("meals_of_plan")
                .whereEqualTo("idMeal", mealId);

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots ) {
                        // Delete the document(s) found by the query
                        documentSnapshot.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    Log.i("TAG", "deleteMeal: success");
                                    callback.onSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("TAG", "deleteMeal: failed", e);
                                    callback.onFailure(e.getMessage());
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error checking document existence", e);
                    callback.onFailure(e.getMessage());
                });

    }

    //refresh room after login
    public void setBackUpFavInRoom(Context context) {
        this.context = context;
        retrieveMeals(UserProfile.getCurrentUserId(), new MealsBackUpCallBack() {
            @Override
            public void onSuccess(List<MealsItem> mealsItemsList) {
                if (mealsItemsList != null && !mealsItemsList.isEmpty()) {
                    Log.i("TAG", "onSuccess in backUp: " + mealsItemsList.get(0).getIdMeal());
                    Log.i("TAG", "onSuccess: size back up "+ mealsItemsList.size());

                    List<MealsItem> refreshmealsItems = mealsItemsList;

                    new Thread(() -> {

                        MealDatabase.getInstance(context).getMealDAO().insertAllMeals(refreshmealsItems);
                        Log.i("TAG", "onSuccess: " + refreshmealsItems.get(0).getIdMeal());

                    }).start();
                    Log.i("TAG", "onSuccess: " + refreshmealsItems.get(0).getStrCategory());
                } else {
                    Log.i("TAG", "No Data retrieved from Firestore");
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("TAG", "Failed to retrieve data from Firestore: " + error);
            }
        });

    }


    public void setBackUpCalInRoom(Context context){

        this.context = context;
        retrievePlanMeals(UserProfile.getCurrentUserId(), new MealPlanCallBack() {
            @Override
            public void onSuccess(List<MealCalendar> mealCalendars) {
                if (mealCalendars != null && !mealCalendars.isEmpty()) {
                    Log.i("TAG", "onSuccess in backUp: " + mealCalendars.get(0).getIdMeal());
                    Log.i("TAG", "onSuccess: size back up "+ mealCalendars.size());

                    List<MealCalendar> refreshmealsItems = mealCalendars;

                    new Thread(() -> {
                        MealDatabase.getInstance(context).getMealDAO().insertAllPlan(refreshmealsItems);
                        Log.i("TAG", "onSuccess: " + refreshmealsItems.get(0).getIdMeal());
                    }).start();
                    Log.i("TAG", "onSuccess: " + refreshmealsItems.get(0).getStrCategory());
                }
                else {
                    Log.i("TAG", "No Data retrieved from Firestore");
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });

    }


}
