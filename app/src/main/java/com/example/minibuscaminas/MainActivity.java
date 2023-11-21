package com.example.minibuscaminas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button[][] tablero;
    private TextView lblPuntos;
    private int puntos;
    private int casillasDescubiertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablero = new Button[5][5];
        tablero[0][0] = findViewById(R.id.btn00);
        tablero[0][1] = findViewById(R.id.btn01);
        tablero[0][2] = findViewById(R.id.btn02);
        tablero[0][3] = findViewById(R.id.btn03);
        tablero[0][4] = findViewById(R.id.btn04);
        tablero[1][0] = findViewById(R.id.btn10);
        tablero[1][1] = findViewById(R.id.btn11);
        tablero[1][2] = findViewById(R.id.btn12);
        tablero[1][3] = findViewById(R.id.btn13);
        tablero[1][4] = findViewById(R.id.btn14);
        tablero[2][0] = findViewById(R.id.btn20);
        tablero[2][1] = findViewById(R.id.btn21);
        tablero[2][2] = findViewById(R.id.btn22);
        tablero[2][3] = findViewById(R.id.btn23);
        tablero[2][4] = findViewById(R.id.btn24);
        tablero[3][0] = findViewById(R.id.btn30);
        tablero[3][1] = findViewById(R.id.btn31);
        tablero[3][2] = findViewById(R.id.btn32);
        tablero[3][3] = findViewById(R.id.btn33);
        tablero[3][4] = findViewById(R.id.btn34);
        tablero[4][0] = findViewById(R.id.btn40);
        tablero[4][1] = findViewById(R.id.btn41);
        tablero[4][2] = findViewById(R.id.btn42);
        tablero[4][3] = findViewById(R.id.btn43);
        tablero[4][4] = findViewById(R.id.btn44);
        lblPuntos = findViewById(R.id.lblPuntos);

        nuevoJuego();

        // Botones del tablero
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero[0].length; c++) {
                tablero[f][c].setOnClickListener(view -> clickCelda((Button) view));
            }
        }

        // Botón nuevo
        Button btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(view -> nuevoJuego());

        // Botón resolver
        Button btnResolver = findViewById(R.id.btnResolver);
        btnResolver.setOnClickListener(view -> resolver());


    }

    private void nuevoJuego() {
        // Todos el texto de las casillas transparentes y vacíos
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero[0].length; c++) {
                tablero[f][c].setTextColor(Color.TRANSPARENT);
                tablero[f][c].setText("");
                tablero[f][c].setBackgroundColor(getColor(R.color.blue));
                tablero[f][c].setVisibility(View.VISIBLE);
            }
        }

        // Texto: ¡Jugando!
        lblPuntos.setText(getText(R.string.msgJugando));

        // Puntos a 0
        puntos = 0;

        // Casillas descubiertas 0
        casillasDescubiertas = 0;

        // Generar minas
        generarMinas();

        // Generar números
        generarNumeros();

        // Activar tablero
        activarTablero(true);

    }

    private void generarMinas() {
        int cont = 0;
        Random r = new Random();

        while (cont < 3) {
            int f = r.nextInt(tablero.length);
            int c = r.nextInt(tablero[0].length);
            if (!tablero[f][c].getText().toString().equals("M")) {
                tablero[f][c].setText("M");
                cont++;
            }
        }
    }

    private void generarNumeros() {
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero[0].length; c++) {

                int n = 0;

                if (!tablero[f][c].getText().toString().equals("M")) {
                    // Arriba
                    if (f - 1 >= 0) {
                        if (tablero[f - 1][c].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Abajo
                    if (f + 1 < tablero.length) {
                        if (tablero[f + 1][c].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Derecha
                    if (c + 1 < tablero[0].length) {
                        if (tablero[f][c + 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Izquierda
                    if (c - 1 >= 0) {
                        if (tablero[f][c - 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Esquina superior izquierda
                    if (f - 1 >= 0 && c - 1 >= 0) {
                        if (tablero[f - 1][c - 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Esquina superior derecha
                    if (f - 1 >= 0 && c + 1 < tablero[0].length) {
                        if (tablero[f - 1][c + 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Esquina inferior izquierda
                    if (f + 1 < tablero.length && c - 1 >= 0) {
                        if (tablero[f + 1][c - 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Esquina inferior derecha
                    if (f + 1 < tablero.length && c + 1 < tablero[0].length) {
                        if (tablero[f + 1][c + 1].getText().toString().equals("M")) {
                            n++;
                        }
                    }

                    // Asignar número
                    if (n > 0) {
                        tablero[f][c].setText(n + "");
                    }
                }
            }
        }
    }

    private void clickCelda(Button b) {
        String texto;

        if (b.getText().toString().equals("M")) {   // Mina
            b.setBackgroundColor(getColor(R.color.red));
            texto = getText(R.string.msgGameOver).toString();
            activarTablero(false);
        } else if (!b.getText().toString().isEmpty()) {   // Número
            b.setTextColor(getColor(R.color.white));
            int n = Integer.parseInt(b.getText().toString());
            puntos += n * 10;
            texto = getText(R.string.lblPuntos).toString() + puntos;
            casillasDescubiertas++;
            if (casillasDescubiertas == 22) {
                activarTablero(false);
                texto = getText(R.string.msgResuelto).toString();
            }
        } else {   // Vacío
            b.setVisibility(View.INVISIBLE);
            puntos += 100;
            texto = getText(R.string.lblPuntos).toString() + puntos;
        }

        lblPuntos.setText(texto);
    }

    private void resolver() {
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero[0].length; c++) {
                if (tablero[f][c].getText().toString().equals("M")) {
                    tablero[f][c].setBackgroundColor(getColor(R.color.red));
                }
            }
        }
        activarTablero(false);
    }

    private void activarTablero(boolean b) {
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero[0].length; c++) {
                tablero[f][c].setEnabled(b);
            }
        }
    }

}