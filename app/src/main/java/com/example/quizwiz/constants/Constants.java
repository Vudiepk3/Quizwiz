package com.example.quizwiz.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Pair;

import androidx.core.text.HtmlCompat;

import com.example.quizwiz.R;
import com.example.quizwiz.models.CategoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static final List<String> difficultyList = Collections.unmodifiableList(
            List.of("Any", "Easy", "Medium", "Hard"));
    public static final List<String> typeList = Collections.unmodifiableList(
            List.of("Any", "Multiple Choice", "True/false"));
    public static final String user = "USER";
    public static final String weeklyScore = "weeklyScore";
    public static final String monthlyScore = "monthlyScore";
    public static final String lastGameScore = "lastGameScore";
    public static final String ALL_TIME_SCORE ="allTimeScore" ;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true;
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true;
                } else return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        }
        return false;
    }

    public static ArrayList<CategoryModel> getCategoryItemList() {
        ArrayList<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel("9", R.drawable.image_general_knowledge, "General Knowledge"));
        list.add(new CategoryModel("10", R.drawable.image_books, "Books"));
        list.add(new CategoryModel("11", R.drawable.image_movies, "Film"));
        list.add(new CategoryModel("12", R.drawable.image_music, "Music"));
        list.add(new CategoryModel("13", R.drawable.image_musicals, "Musicals & Theatres"));
        list.add(new CategoryModel("14", R.drawable.image_television, "Television"));
        list.add(new CategoryModel("15", R.drawable.image_video_games, "Video Games"));
        list.add(new CategoryModel("16", R.drawable.image_board_game, "Board Games"));
        list.add(new CategoryModel("17", R.drawable.image_science, "Science & Nature"));
        list.add(new CategoryModel("18", R.drawable.image_computer, "Computer"));
        list.add(new CategoryModel("19", R.drawable.image_maths, "Mathematics"));
        list.add(new CategoryModel("20", R.drawable.image_mythology, "Mythology"));
        list.add(new CategoryModel("21", R.drawable.image_sport, "Sports"));
        list.add(new CategoryModel("22", R.drawable.image_geography, "Geography"));
        list.add(new CategoryModel("23", R.drawable.image_history, "History"));
        list.add(new CategoryModel("24", R.drawable.image_politics, "Politics"));
        list.add(new CategoryModel("25", R.drawable.image_art, "Art"));
        list.add(new CategoryModel("26", R.drawable.image_celebrity, "Celebrities"));
        list.add(new CategoryModel("27", R.drawable.image_animals, "Animals"));
        list.add(new CategoryModel("28", R.drawable.image_vehicles, "Vehicles"));
        list.add(new CategoryModel("29", R.drawable.image_comic, "Comics"));
        list.add(new CategoryModel("30", R.drawable.image_gadgets, "Gadgets"));
        list.add(new CategoryModel("31", R.drawable.image_anime, "Anime & Manga"));
        list.add(new CategoryModel("32", R.drawable.image_cartoon, "Cartoons & Animations"));
        return list;
    }

    public static Pair<String, List<String>> getRandomOptions(String correctAnswer, List<String> incorrectAnswer) {
        List<String> list = new ArrayList<>();
        String decodedCorrectAnswer = decodeHtmlString(correctAnswer);
        list.add(decodedCorrectAnswer);
        for (String s : incorrectAnswer) {
            list.add(decodeHtmlString(s));
        }
        Collections.shuffle(list);
        return new Pair<>(decodedCorrectAnswer, list);
    }

    public static String decodeHtmlString(String htmlEncoded) {
        return HtmlCompat.fromHtml(htmlEncoded, HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
    }

    public static List<String> getCategoryStringArray() {
        List<CategoryModel> list = getCategoryItemList();
        List<String> result = new ArrayList<>();
        result.add("Any");
        for (CategoryModel category : list) {
            result.add(category.getName());
        }
        return result;
    }
}
