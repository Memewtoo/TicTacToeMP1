package com.example.tictactoemp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayerActivity extends AppCompatActivity {
    String playerturn = "Player 1 turn.";
    TextView textView_winner, textView_turn;
    boolean computerturn = true;

    private Button[][] buttons = new Button[3][3];
    private boolean player1_turn = true;
    private int round_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        textView_winner = findViewById(R.id.textview_winner);
        textView_turn = findViewById(R.id.textview_turn);

        textView_turn.setText(playerturn);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!((Button) v).getText().toString().equals("")){
                            return;
                        }

                        player1_turn = true;
                        computerturn = true;
                        ((Button) v).setText("X");
                        textView_turn.setText(R.string.computerturn);

                        round_count++;

                        if(checkWin()){
                            if(player1_turn){
                                textView_winner.setText(R.string.player1win);
                                computerturn = false;
                                resetBoard();
                            }
                            else{
                                textView_winner.setText(R.string.computerwin);
                                computerturn = false;
                                resetBoard();
                            }
                        }

                        else if(round_count == 9){
                            textView_winner.setText(R.string.draw);
                            computerturn = false;
                            resetBoard();
                        }

                        if(computerturn) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    computerMove();
                                    player1_turn = false;
                                }}, 250);
                        }
                    }
                });
            }
        }
    }

    private boolean checkWin(){
        String[][] field = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //Horizontal check win
        for(int i = 0; i < 3; i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }

        //Vertical check win
        for(int i = 0; i < 3; i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }

        //Diagonal check win at 0,0
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }

        //Diagonal check win 0,2
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void resetBoard(){
        for(int i = 0; i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        round_count = 0;
        player1_turn = true;
        textView_turn.setText(playerturn);
    }

    public void randomPlay(){
        player1_turn = false;

        int random = (int)(Math.random()*9);
        int i=random/3;
        int j=random%3;

        if(!checkFull()) {
            if (!checkEmpty(i, j)) {
                while (!checkEmpty(i, j)) {
                    random = (int) (Math.random() * 9);
                    i = random / 3;
                    j = random % 3;
                }

            }

            buttons[i][j].setText("O");
        }

        else{
            return;
        }
    }

    public boolean checkEmpty(int i, int j){
        if(buttons[i][j].getText().toString().equals("")){
            return true;
        }

        return false;
    }

    public boolean checkFull(){
        int counter = 0;
        for(int i = 0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!buttons[i][j].getText().toString().equals("")){
                    counter++;
                }
            }
        }
        if(counter == 9){
            return true;
        }

        return false;
    }

    public void computerMove(){
        randomPlay();
        textView_turn.setText(R.string.player1turn);

        round_count++;

        if(checkWin()){
            if(player1_turn){
                textView_winner.setText(R.string.player1win);
                resetBoard();
            }
            else{
                textView_winner.setText(R.string.computerwin);
                resetBoard();
            }
        }

        else if(round_count >= 9) {
            textView_winner.setText(R.string.draw);
            resetBoard();
        }
    }

}
