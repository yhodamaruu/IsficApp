package com.isficuniversity.isfic.modules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class GlowingRGBRingView extends View {

    private Paint ringPaint, fluidPaint;
    private float centerX, centerY;
    private float ringRadius, ringThickness;
    private float fluidAnimationStep; // Étape d'animation pour le fluide
    private Handler handler;
    private Bitmap fluidBitmap; // Pour créer un effet de flou dynamique
    private Canvas fluidCanvas;

    public GlowingRGBRingView(Context context) {
        super(context);
        init();
    }

    public GlowingRGBRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Peinture pour l'anneau extérieur
        ringPaint = new Paint();
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setAntiAlias(true);
        ringPaint.setStrokeWidth(40); // Épaisseur de l'anneau

        // Peinture pour les fluides internes
        fluidPaint = new Paint();
        fluidPaint.setStyle(Paint.Style.FILL);
        fluidPaint.setAntiAlias(true);

        ringRadius = 200; // Rayon de l'anneau
        ringThickness = 40; // Épaisseur de l'anneau
        fluidAnimationStep = 0; // Étape d'animation

        handler = new Handler();
        setLayerType(LAYER_TYPE_HARDWARE, null); // Optimise pour des animations complexes

        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        centerX = width / 2f;
        centerY = height / 2f;

        // Dessine les fluides animés
        drawAnimatedFluids(canvas);

        // Dessine l'anneau extérieur
        drawRing(canvas);
    }

    private void drawRing(Canvas canvas) {
        // Dégradé radial RGB pour l'anneau
        Shader ringShader = new RadialGradient(
                centerX, centerY, ringRadius + ringThickness / 2,
                new int[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY}, // Cycle RGB
                null,
                Shader.TileMode.MIRROR
        );
        ringPaint.setShader(ringShader);

        canvas.drawCircle(centerX, centerY, ringRadius, ringPaint);
    }

    private void drawAnimatedFluids(Canvas canvas) {
        // Initialisation d'un bitmap pour les fluides (avec effet de flou)
        if (fluidBitmap == null) {
            fluidBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            fluidCanvas = new Canvas(fluidBitmap);
        }

        // Réinitialise le canvas interne
        fluidCanvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);

        // Anime les "fluides" avec des formes dynamiques
        for (int i = 0; i < 5; i++) { // Crée plusieurs "fluides"
            float offset = (fluidAnimationStep + i * 72) % 360;
            float x = centerX + (float) (Math.cos(Math.toRadians(offset)) * (ringRadius / 1.5));
            float y = centerY + (float) (Math.sin(Math.toRadians(offset)) * (ringRadius / 1.5));
            int color = Color.HSVToColor(new float[]{offset, 1f, 1f}); // Couleurs RGB fluides

            fluidPaint.setColor(color);
            fluidCanvas.drawCircle(x, y, 30, fluidPaint); // Dessine les "gouttes" animées
        }

        // Applique un flou gaussien (effet simulé ici)
        canvas.drawBitmap(fluidBitmap, 0, 0, null);
    }

    private void startAnimation() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                fluidAnimationStep += 5; // Animation plus rapide
                if (fluidAnimationStep > 360) {
                    fluidAnimationStep -= 360; // Réinitialise le cycle
                }

                invalidate(); // Redessine la vue
                handler.postDelayed(this, 16); // Animation fluide (60 FPS)
            }
        });
    }
}
