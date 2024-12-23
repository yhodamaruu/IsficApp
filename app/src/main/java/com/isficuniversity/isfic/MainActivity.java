package com.isficuniversity.isfic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.isficuniversity.isfic.adaptaters.Articles2Adaptater;
import com.isficuniversity.isfic.adaptaters.ArticlesAdapter;
import com.isficuniversity.isfic.admin.LoginAdminActivity;
import com.isficuniversity.isfic.carousels.DepCarouselActivity;
import com.isficuniversity.isfic.carousels.FilCarouselActivity;
import com.isficuniversity.isfic.carousels.WhatsIsficCarouselActivity;
import com.isficuniversity.isfic.modules.ApiClient;
import com.isficuniversity.isfic.modules.ApiResponse;
import com.isficuniversity.isfic.modules.ApiService;
import com.isficuniversity.isfic.modules.Article;
import com.isficuniversity.isfic.modules.FranceInfoScraper;
import com.isficuniversity.isfic.modules.MaliwebScraper;
import com.isficuniversity.isfic.notifs.NotificationsActivity;
import com.isficuniversity.isfic.utils.cv.CreateCVActivity;
import com.isficuniversity.isfic.utils.cv.NotesActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ImageButton menubtn;
    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private boolean sayedHello = false;
    private boolean isListening = false;

    private BottomNavigationView bottomNavigationView;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.main);
        navView = findViewById(R.id.nav_view);
        menubtn = findViewById(R.id.menu_btn);
        CircleImageView circleFil = findViewById(R.id.filieres);
        CircleImageView circleDep = findViewById(R.id.depliant);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.son);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int langResult = textToSpeech.setLanguage(Locale.FRENCH);
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Langue non supportée");
                } else {
                    setupgreet();
                }
            } else {
                Log.e("TTS", "Erreur lors de l'initialisation");
            }
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("SpeechRecognition", "Prêt pour la parole");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("SpeechRecognition", "Début de la parole");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                if (isListening) {
                }
            }

            @Override
            public void onBufferReceived(byte[] buffer) {}
            @Override
            public void onEndOfSpeech() {
                listenToSpeech();
            }

            @Override
            public void onError(int error) {
                Log.e("SpeechRecognizer", "Erreur: " + error);
            }

            @Override
            public void onResults(Bundle results) {
                List<String> recognizedWords = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (recognizedWords != null && !recognizedWords.isEmpty()) {
                    String command = recognizedWords.get(0);
                    handleVoiceCommand(command);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}
            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        circleFil.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, view, "circleTransition");

            Intent intent = new Intent(MainActivity.this, FilCarouselActivity.class);
            startActivity(intent, options.toBundle());
        });
        circleDep.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, view, "circleTransition");

            Intent intent = new Intent(MainActivity.this, DepCarouselActivity.class);
            startActivity(intent, options.toBundle());
        });
        findViewById(R.id.fincompt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.bottomcompt).setVisibility(View.VISIBLE);
                Animation animation;
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                findViewById(R.id.bottomcompt).startAnimation(animation);
            }
        });
        findViewById(R.id.network).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.bottomnetwork).setVisibility(View.VISIBLE);
                Animation animation;
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                findViewById(R.id.bottomnetwork).startAnimation(animation);
            }
        });
        findViewById(R.id.secret_btn).setOnClickListener(v -> {
            findViewById(R.id.normallayout).setVisibility(View.GONE);
            findViewById(R.id.admin).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.tiktok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.tiktok.com/@isfic_officiel";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/isfic_/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        findViewById(R.id.linkedin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ml.linkedin.com/in/isfic-boulkassoumbougou-7431931a8";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/isficboulkassoumbougou/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        findViewById(R.id.commerce_i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.bottomcommerce).setVisibility(View.VISIBLE);
                Animation animation;
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                findViewById(R.id.bottomcommerce).startAnimation(animation);
            }
        });
        findViewById(R.id.whatsisfic).setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, view, "circleTransition");

            Intent intent = new Intent(MainActivity.this, WhatsIsficCarouselActivity.class);
            startActivity(intent, options.toBundle());
        });
        findViewById(R.id.notif_btn).setOnClickListener(v -> {
            Intent notif_i = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(notif_i);
        });
        findViewById(R.id.settings_btn).setOnClickListener(v -> {
            Intent settings_i = new Intent(MainActivity.this, ActualStudentsList.class);
            startActivity(settings_i);
        });
        findViewById(R.id.notesbook).setOnClickListener(v -> {
            Intent settings_i = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(settings_i);
        });

        findViewById(R.id.fonctions_utl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        findViewById(R.id.login_admin).setOnClickListener(v -> {
            Intent admin_i = new Intent(MainActivity.this, LoginAdminActivity.class);
            startActivity(admin_i);
        });
        findViewById(R.id.student_collect).setOnClickListener(v -> {
            showAdminAccessDialog();
        });
        findViewById(R.id.about_btn).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            BottomSheetDialog builder = new BottomSheetDialog(MainActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_social, null);
            builder.setContentView(dialogView);

            dialogView.findViewById(R.id.call_num_orange).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "76 78 83 83"));
                    startActivity(intent);
                }
            });
            dialogView.findViewById(R.id.call_num_malitel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "66 43 50 50"));
                    startActivity(intent);
                }
            });
            builder.show();
        });
        menubtn.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(this::handleNavigationItemSelected);
        // - Malijet
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        new Thread(() -> {
            List<Article> articles1 = MaliwebScraper.getArticles();
            runOnUiThread(() -> {
                ArticlesAdapter adapter1 = new ArticlesAdapter(articles1);
                recyclerView1.setAdapter(adapter1);
            });
        }).start();

// - Fance Info
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        new Thread(() -> {

            List<Article> articles2 = FranceInfoScraper.getArticles();
            runOnUiThread(() -> {

                Articles2Adaptater adapter2 = new Articles2Adaptater(articles2);
                recyclerView2.setAdapter(adapter2);
            });
        }).start();


    }

    private void setupgreet() {
        findViewById(R.id.playsong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIADialog();
            }
        });
    }

    private void speak(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "greetingId");
    }
    private String getGreetingBasedOnTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Random random = new Random();

        // Salutations en fonction de l'heure
        if (hour >= 4 && hour < 6) {
            return randomGreeting("Fajr");
        } else if (hour >= 6 && hour < 12) {
            return randomGreeting("matin");
        } else if (hour >= 12 && hour < 18) {
            return randomGreeting("après-midi");
        } else {
            return randomGreeting("soirée");
        }
    }

    private String randomGreeting(String timeOfDay) {
        String[] greetings;
        switch (timeOfDay) {
            case "Fajr":
                greetings = new String[]{"Salut, l'heure du Fajr est là.", "Bonjour Aboubacar, c'est l'heure du Fajr."};
                break;
            case "matin":
                greetings = new String[]{"Bonjour, prêt pour la journée ?", "Salut, comment tu vas ce matin ?"};
                break;
            case "après-midi":
                greetings = new String[]{"Bonjour, comment puis-je vous aider ?", "Salut, prêt à attaquer l'après-midi ?"};
                break;
            case "soirée":
                greetings = new String[]{"Bonsoir, comment s'est passée ta journée ?", "Salut, alors comment ça s'est passé aujourd'hui ?"};
                break;
            default:
                greetings = new String[]{"Salut, comment ça va ?", "Bonjour, comment puis-je t'aider ?" };
                break;
        }
        return greetings[new Random().nextInt(greetings.length)];
    }

    private void greetUser() {
        if (!sayedHello) {
            String greeting = getGreetingBasedOnTime();
            speak(greeting);
            sayedHello = true;
        }
        else{
            startListening();
        }
    }

    private void connectToWifi(String ssid) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = "\"" + ssid + "\""; // Ajouter des guillemets
        wifiConfig.preSharedKey = "\"votre_mot_de_passe_wifi\""; // Mot de passe (remplacez par une entrée utilisateur)

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        int netId = wifiManager.addNetwork(wifiConfig);
        if (netId != -1) {
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();
            speak("Connexion au réseau " + ssid + " en cours.");
        } else {
            speak("Je n'ai pas pu me connecter au réseau " + ssid + ". Veuillez vérifier les paramètres.");
        }
    }

    private void startListening() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            // Créez un Intent configuré pour la reconnaissance vocale
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            // Passez cet Intent à la méthode startListening
            speechRecognizer.startListening(intent);
        } else {
            Log.e("SpeechRecognition", "La reconnaissance vocale n'est pas disponible.");
        }

    }
    public int getBatteryLevel(Context context) {
        // Crée un Intent pour récupérer les informations de la batterie
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        // Récupère le niveau de la batterie (en pourcentage)
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Calcule le pourcentage
        if (level != -1 && scale != -1) {
            return (int) ((level / (float) scale) * 100);  // Retourne le pourcentage
        }

        return -1; // Si le niveau ne peut pas être récupéré, retourne -1
    }
    private void stopListening() {
        isListening = false;
        speechRecognizer.stopListening();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }
    private void handleVoiceCommand(String command) {
        command = command.toLowerCase();

        // Commandes vocales avec multiples variantes
        if (command.contains("comment ça va")) {
            String[] responses = {
                    "Ça va bien, merci ! Et vous ?",
                    "Je vais très bien, et vous ?",
                    "Je suis en pleine forme, merci de demander !",
                    "Tout va bien, un peu fatiguée mais ça va !",
                    "Super bien, merci, et vous ?",
                    "Je vais bien, merci de t'en soucier !",
                    "Ça va, je gère. Et vous ?",
                    "Pas mal du tout, et vous ?",
                    "Je vais bien, mais je pourrais être encore mieux !",
                    "Un peu fatiguée, mais ça va !",
                    "Tout roule, et vous ?",
                    "Impeccable, comme toujours !",
                    "Je suis dans une forme olympique !",
                    "C'est plutôt cool en ce moment !",
                    "Un peu fatiguée, mais rien de grave !"
            };
            int randomIndex = new Random().nextInt(responses.length);
            speak(responses[randomIndex]);
        } else if (command.contains("quel temps fait-il")) {
            speak("Je vais vérifier cela pour vous !");
            // Tu peux appeler une API météo ici pour obtenir la réponse en temps réel
        } else if (command.contains("arrête")) {
            speak("Je vais arrêter de parler.");
        } else if (command.contains("hello") ||
                command.contains("salut") ||
                command.contains("bonjour") ||
                command.contains("bonsoir") ||
                command.contains("yo") ||
                command.contains("coucou") ||
                command.contains("comment ça va") ||
                command.contains("bien le bonjour") ||
                command.contains("ça va") ||
                command.contains("salut toi") ||
                command.contains("hé") ||
                command.contains("quoi de neuf") ||
                command.contains("hey") ||
                command.contains("yo tout va bien") ||
                command.contains("ça roule") ||
                command.contains("comment tu vas")
        ) {
            int batteryLevel = getBatteryLevel(getApplicationContext());  // Récupérer l'état de la batterie

            String[] greetings = {
                    "Salut L'ISFICIEN ?",
                    "Bonjour, comment puis-je t'aider ?",
                    "Hey L'ISFICIEN, quoi de neuf ?",
                    "Hello, comment ça va l'isficien ?",
                    "Salut !",
                    "Bien le bonjour !",
                    "Yo, comment vous allez L'ISFICIEN ?",
                    "Bonjour, mon ami !",
                    "Coucou !",
                    "Hello, tu veux discuter ?"
            };
            int randomIndex = new Random().nextInt(greetings.length);

            // Si la batterie est faible
            if (batteryLevel < 20) {
                speak(greetings[randomIndex] + " Mais attention, ma batterie est faible ! Je ne vais pas tenir longtemps.");
            }
            // Si la batterie est moyenne
            else if (batteryLevel < 50) {
                speak(greetings[randomIndex] + " Je suis à moitié chargé, mais je suis toujours là !");
            }
            // Si la batterie est encore élevée
            else {
                speak(greetings[randomIndex] + " Ma batterie est pleine et je suis prêt à discuter !");
            }
        }
        else if (command.contains("batterie") ||
                command.contains("niveau de batterie") ||
                command.contains("pourcentage de batterie") ||
                command.contains("combien de batterie") ||
                command.contains("batterie restante") ||
                command.contains("quand est-ce que tu te charges") ||
                command.contains("combien de pourcentage de batterie") ||
                command.contains("est-ce que tu as assez de batterie") ||
                command.contains("ta batterie va tenir") ||
                command.contains("t'as combien de batterie") ||
                command.contains("quel est le niveau de batterie") ||
                command.contains("batterie à quel niveau") ||
                command.contains("combien il te reste de batterie") ||
                command.contains("batterie faible") ||
                command.contains("quel est ton pourcentage de batterie") ||
                command.contains("batterie en pourcentage") ||
                command.contains("est-ce que tu as de la batterie") ||
                command.contains("ta batterie va tenir combien de temps") ||
                command.contains("tu peux encore tenir avec ta batterie") ||
                command.contains("combien de temps avant la batterie vide") ||
                command.contains("ta batterie est chargée") ||
                command.contains("quel est l'état de ta batterie") ||
                command.contains("comment va ta batterie") ||
                command.contains("ta batterie est en forme") ||
                command.contains("est-ce que ta batterie est faible") ||
                command.contains("ta batterie est faible ?")
        )
        {
            int batteryLevel = getBatteryLevel(getApplicationContext());  // Utilise getApplicationContext() pour obtenir le contexte
            if (batteryLevel > 50) {
                int randomResponse = new Random().nextInt(3);
                if (randomResponse == 0) {
                    speak("Ma batterie est encore à " + batteryLevel + "%, je suis au top pour le moment !");
                } else if (randomResponse == 1) {
                    speak("Tout va bien, ma batterie est encore à " + batteryLevel + "% !");
                } else {
                    speak("Je suis à " + batteryLevel + "%, donc tout roule pour l'instant !");
                }
            } else if (batteryLevel > 20) {
                int randomResponse = new Random().nextInt(3);
                if (randomResponse == 0) {
                    speak("Je suis à " + batteryLevel + "%, ça commence à descendre, mais ça va encore !");
                } else if (randomResponse == 1) {
                    speak("Il me reste " + batteryLevel + "% de batterie, ça va encore, mais je dois garder un œil dessus.");
                } else {
                    speak("Je suis à " + batteryLevel + "%, mais je vais devoir penser à me recharger bientôt.");
                }
            } else {
                int randomResponse = new Random().nextInt(3);
                if (randomResponse == 0) {
                    speak("Ma batterie est faible, il ne me reste plus que " + batteryLevel + "%, je devrais me brancher.");
                } else if (randomResponse == 1) {
                    speak("Je suis à " + batteryLevel + "%, je risque de m'arrêter bientôt si je ne me recharge pas.");
                } else {
                    speak("Oups, il ne me reste que " + batteryLevel + "%, il serait temps de me brancher !");
                }
            }

        } else if (command.contains("quel est ton nom") || command.contains("ton nom")|| command.contains("c'est quoi ton nom")) {
            speak("Je suis ISFICIA");
        } else if (command.contains("fais-moi rire")  ||
                command.contains("une blague") ||
                command.contains("raconte une blague") ||
                command.contains("dis-moi une blague") ||
                command.contains("dis une blague") ||
                command.contains("raconte-moi une blague") ||
                command.contains("fais une blague") ||
                command.contains("donne-moi une blague") ||
                command.contains("fais-moi une blague") ||
                command.contains("un peu d'humour") ||
                command.contains("je veux rire") ||
                command.contains("fais-moi rigoler") ||
                command.contains("dis quelque chose de drôle") ||
                command.contains("raconte-moi quelque chose de drôle") ||
                command.contains("je veux entendre une blague") ||
                command.contains("donne-moi du fun") ||
                command.contains("donne-moi une bonne blague") ||
                command.contains("tu connais une bonne blague") ||
                command.contains("raconte une histoire drôle") ||
                command.contains("humour, s'il te plaît") ||
                command.contains("tu peux me faire rire") ||
                command.contains("je suis prêt pour une blague") ||
                command.contains("à toi de me faire rire") ||
                command.contains("je suis en manque de rire") ||
                command.contains("improvise une blague") ||
                command.contains("fais-moi sourire") ||
                command.contains("un truc drôle, s'il te plaît") ||
                command.contains("tu sais une blague") ||
                command.contains("fais une petite blague") ||
                command.contains("tu as une blague à raconter") ||
                command.contains("raconte une blague qui tue") ||
                command.contains("fais-moi mourir de rire") ||
                command.contains("dis-moi un truc rigolo") ||
                command.contains("je veux entendre quelque chose de drôle") ||
                command.contains("humour à gogo") ||
                command.contains("donne-moi quelque chose de drôle") ||
                command.contains("je suis prêt pour l'humour") ||
                command.contains("humour rapide") ||
                command.contains("raconte un truc marrant") ||
                command.contains("tu connais un truc marrant") ||
                command.contains("je veux rigoler un peu") ||
                command.contains("mets-moi de bonne humeur avec une blague") ||
                command.contains("dis-moi une bonne histoire drôle") ||
                command.contains("fais un peu de magie comique") ||
                command.contains("délivre-moi une blague") ||
                command.contains("humour à la demande") ||
                command.contains("fais-moi une blague express") ||
                command.contains("tu as une blague de chef") ||
                command.contains("je suis dans l'ambiance pour une blague") ||
                command.contains("raconte-moi un sketch drôle") ||
                command.contains("donne-moi ton meilleur rire") ||
                command.contains("fais-moi exploser de rire") ||
                command.contains("tu veux me faire rire ?") ||
                command.contains("une histoire drôle à raconter ?") ||
                command.contains("tu as une bonne vanne") ||
                command.contains("fais-moi un sketch") ||
                command.contains("fais-moi une blague courte") ||
                command.contains("dis-moi quelque chose de marrant") ||
                command.contains("fais-moi marrer") ||
                command.contains("je veux rire, alors raconte-moi une blague") ||
                command.contains("tu veux une blague ?") ||
                command.contains("t'es prêt pour une blague ?") ||
                command.contains("je suis prêt à rire, alors balance la blague") ||
                command.contains("j'ai besoin de rire") ||
                command.contains("raconte-moi un gag") ||
                command.contains("j'espère que tu as une blague en stock") ||
                command.contains("allez, fais-moi une blague") ||
                command.contains("t'as une blague dans les tiroirs ?") ||
                command.contains("c'est le moment de me faire rire") ||
                command.contains("tu as une blague dans ta manche") ||
                command.contains("balance une vanne") ||
                command.contains("je veux un bon rire") ||
                command.contains("je veux une bonne rigolade") ||
                command.contains("fais-moi un rire éclatant") ||
                command.contains("mets-moi de bonne humeur avec une vanne") ||
                command.contains("tu connais une vanne qui tue") ||
                command.contains("je suis en mode blague") ||
                command.contains("on rigole un peu") ||
                command.contains("dis-moi quelque chose de comique") ||
                command.contains("fais une vanne de folie") ||
                command.contains("j'ai besoin de rire, raconte une blague") ||
                command.contains("va me chercher une blague") ||
                command.contains("fais-nous rigoler tous les deux") ||
                command.contains("prêt pour une blague ?") ||
                command.contains("je suis prêt à rire") ||
                command.contains("quelque chose de marrant") ||
                command.contains("allez, fais-moi rire") ||
                command.contains("je veux un éclat de rire") ||
                command.contains("fais une blague bien marrant") ||
                command.contains("on rigole un peu ?") ||
                command.contains("je veux rire, je veux une blague") ||
                command.contains("donne-moi un bon fou rire") ||
                command.contains("je suis en mode humour") ||
                command.contains("allez, fais-moi sourire un peu") ||
                command.contains("fais-moi une blague pas trop nulle") ||
                command.contains("raconte-moi un truc drôle pour rigoler") ||
                command.contains("raconte-moi une petite blague rigolote") ||
                command.contains("je suis prêt pour le comique") ||
                command.contains("allez, balance la blague") ||
                command.contains("tu as une bonne rigolade en stock ?") ||
                command.contains("tu peux faire une petite blague ?") ||
                command.contains("tu connais une petite vanne ?") ||
                command.contains("j'ai besoin d'une bonne blague") ||
                command.contains("je veux entendre une blague qui déchire") ||
                command.contains("raconte-moi une blague qui pète") ||
                command.contains("tu peux faire un petit sketch") ||
                command.contains("je veux rigoler fort") ||
                command.contains("balance une histoire drôle") ||
                command.contains("tu sais une blague de ouf") ||
                command.contains("fais-moi une vanne de malade") ||
                command.contains("tu veux me faire pleurer de rire ?") ||
                command.contains("fais une bonne blague de malade") ||
                command.contains("je veux bien rigoler un peu") ||
                command.contains("je suis chaud pour une blague") ||
                command.contains("fais-moi une vanne qui déchire") ||
                command.contains("je veux une rigolade maintenant") ||
                command.contains("allez, fais-moi rigoler") ||
                command.contains("je veux entendre une vanne de malade") ||
                command.contains("prêt à éclater de rire") ||
                command.contains("fais-moi une blague géniale") ||
                command.contains("je veux mourir de rire") ||
                command.contains("je veux entendre un bon fou rire") ||
                command.contains("dis-moi une blague qui tue") ||
                command.contains("dis-moi une bonne blague qui me fait rire") ||
                command.contains("fais-moi une blague qui déchire") ||
                command.contains("vas-y fais moi rire")
                ||
                command.contains("vas-y fais une blague")||
                command.contains("vas-y une blague ")
                ||
                command.contains("dis-moi un truc rigolo, s'il te plaît")
        ) {
            String[] jokes = {
                    "Pourquoi les plongeurs plongent toujours en arrière et jamais en avant ? Parce que sinon ils tombent toujours dans le bateau !",
                    "Quel est le comble pour un électricien ? De ne pas être au courant.",
                    "Que fait un poisson dans une bibliothèque ? Il fait des vagues !",
                    "Pourquoi les poissons détestent l'ordinateur ? Parce qu'ils ont peur du net !",
                    "Pourquoi les plongeurs plongent-ils toujours en arrière ? Parce qu'en avant, ils tombent dans le bateau !",
                    "Quel est le comble pour un électricien ? De ne pas être au courant !"
            };
            int randomIndex = new Random().nextInt(jokes.length);
            speak(jokes[randomIndex]);
            startListening();

        }else if (command.contains("wifi") || command.contains("réseau wifi") || command.contains("réseau disponible") ||
                command.contains("réseaux") || command.contains("connexion wifi")) {

            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> wifiList = wifiManager.getScanResults();

            if (wifiList != null && !wifiList.isEmpty()) {
                speak("J'ai trouvé plusieurs réseaux Wi-Fi disponibles. Voulez-vous que je les liste pour vous ?");

                // Après une confirmation vocale (ex. : "oui"), affiche les réseaux dans un AlertDialog
                new Handler(Looper.getMainLooper()).post(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Réseaux Wi-Fi disponibles");

                    // Crée une liste avec les noms des réseaux
                    String[] wifiNetworks = new String[wifiList.size()];
                    for (int i = 0; i < wifiList.size(); i++) {
                        wifiNetworks[i] = (i + 1) + ". " + wifiList.get(i).SSID;
                    }

                    builder.setItems(wifiNetworks, (dialog, which) -> {
                        // L'utilisateur a sélectionné un réseau
                        String selectedNetwork = wifiList.get(which).SSID;
                        speak("Vous avez sélectionné le réseau numéro " + (which + 1) + ", nommé " + selectedNetwork + ". Je vais tenter de m'y connecter.");

                        // Ajouter ici la logique pour se connecter au réseau Wi-Fi sélectionné
                        connectToWifi(selectedNetwork);
                    });

                    builder.setNegativeButton("Annuler", (dialog, which) -> {
                        speak("D'accord, je ne vais pas me connecter à un réseau Wi-Fi pour le moment.");
                        dialog.dismiss();
                    });

                    builder.show();
                });

                // Vérifier si déjà connecté
                WifiInfo currentConnection = wifiManager.getConnectionInfo();
                if (currentConnection != null && currentConnection.getSSID() != null && !currentConnection.getSSID().equals("<unknown ssid>")) {
                    String currentSSID = currentConnection.getSSID().replace("\"", ""); // Supprimer les guillemets
                    speak("Vous êtes déjà connecté au réseau " + currentSSID + ". Voulez-vous changer de réseau ?");
                } else {
                    speak("Vous n'êtes actuellement connecté à aucun réseau.");
                }

            }
            else {
                speak("Désolé, je n'ai pas trouvé de réseaux Wi-Fi disponibles.");
            }
        }
        else {
            speak("Désolé, je n'ai pas compris cette commande. Peux-tu la reformuler ?");
        }
    }

    private void listenToSpeech() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        } else {
            Log.e("SpeechRecognition", "La reconnaissance vocale n'est pas disponible.");
        }
    }
    private void showIADialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.layout_ia, null);
        builder.setView(dialogView);
        if(!sayedHello){
            greetUser();
        }else{
            startListening();
        }
        dialogView.findViewById(R.id.iabtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sayedHello){
                    greetUser();
                }else{
                    startListening();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }


    private void showAdminAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_admin_acces, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

        EditText passwordField = dialogView.findViewById(R.id.password_field);
        Button confirmButton = dialogView.findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
            String password = passwordField.getText().toString().trim();
            if ("12345678".equals(password)) {
                dialog.dismiss();
                Intent adminIntent = new Intent(MainActivity.this, StudentCollect.class);
                startActivity(adminIntent);
            } else {
                dialog.dismiss();
                showErrorDialog();
            }
        });
    }

    private void showErrorDialog() {
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View errorView = inflater.inflate(R.layout.dialog_error, null);
        errorBuilder.setView(errorView);

        AlertDialog errorDialog = errorBuilder.create();
        errorDialog.show();

        ImageView errorImage = errorView.findViewById(R.id.error_image);
        errorImage.setBackgroundResource(R.drawable.baseline_report_gmailerrorred_24);

        Button closeButton = errorView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> errorDialog.dismiss());
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Générer un CV", "Générer une lettre de motivation", "Optiontest3"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(MainActivity.this, CreateCVActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
        builder.create().show();
    }
    private boolean handleNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_main) {
            showMainContent();
            return true;
        } else if (item.getItemId() == R.id.nav_lean) {
            showLearnContent();
            return true;
        } else if (item.getItemId() == R.id.nav_messages) {
            showChatContent();
            return true;
        }else {
            return false;
        }
    }
  
    private void showMainContent() {
        findViewById(R.id.home_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.profil_layout).setVisibility(View.GONE);
        findViewById(R.id.chat_layout).setVisibility(View.GONE);
        findViewById(R.id.lean_layout).setVisibility(View.GONE);

    }
    private void showLearnContent() {
        findViewById(R.id.home_layout).setVisibility(View.GONE);
        findViewById(R.id.profil_layout).setVisibility(View.GONE);
        findViewById(R.id.chat_layout).setVisibility(View.GONE);
        findViewById(R.id.lean_layout).setVisibility(View.VISIBLE);

    }
    private void showChatContent() {
        findViewById(R.id.home_layout).setVisibility(View.GONE);
        findViewById(R.id.profil_layout).setVisibility(View.GONE);
        findViewById(R.id.chat_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.lean_layout).setVisibility(View.GONE);
    }
    private void showProfilContent() {
        findViewById(R.id.home_layout).setVisibility(View.GONE);
        findViewById(R.id.profil_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.chat_layout).setVisibility(View.GONE);
        findViewById(R.id.lean_layout).setVisibility(View.GONE);
    }


}