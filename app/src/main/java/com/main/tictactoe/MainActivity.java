package com.main.tictactoe;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button [][] buttons=new Button[3][3];

    private boolean player1Turn=true;

    private  int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView tv_player1;
    private TextView tv_player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_player1=findViewById(R.id.tv_p1);
        tv_player2=findViewById(R.id.tv_p2);

        //array for two dimensional array
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                //getting the button ids
                String buttonId="btn_"+i+j;
                //creating actual resource file
                int resId=getResources().getIdentifier(buttonId,"id",getPackageName());
                buttons[i][j]=findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button btn_reset=findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetGame();

            }
        });



    }

    @Override
    public void onClick(View v) {

        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button)v).setText("X");
        }else{
            //player 2s turn
            ((Button)v).setText("O");
        }

        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount==9){
            draw();
        }else{
            //switching turns if not even a draw
            player1Turn=!player1Turn;
        }

    }

    private boolean checkForWin(){

        String [][] field=new String[3][3];

        //getting the data from the fields
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        //checking the columns
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    &&field[i][0].equals(field[i][2])
                    &&!field[i][0].equals("")){
                return true;
            }
        }

        //checking the rows
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    &&field[i][0].equals(field[i][2])
                    &&!field[i][0].equals("")){
                return true;
            }

            if(field[0][i].equals(field[1][i])
                    &&field[0][i].equals(field[2][i])
                    &&!field[0][i].equals("")){
                return true;
            }

            //left diagonal check
            if(field[0][0].equals(field[1][1])
                    &&field[0][0].equals(field[2][2])
                    &&!field[0][0].equals("")){
                return true;
            }

            //right diagonal check
            if(field[0][2].equals(field[1][1])
                    &&field[0][2].equals(field[2][0])
                    &&!field[0][2].equals("")){
                return true;
            }

        }

        return false;

    }

    private void player1Wins(){

        player1Points++;
        Toast.makeText(this, "*****PLAYER 1 WINS*****", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void player2Wins(){

        player1Points++;
        Toast.makeText(this, "*****PLAYER 2 WINS*****", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void draw(){

        Toast.makeText(this, "---draw!---", Toast.LENGTH_SHORT).show();
        resetBoard();

    }

    private void updatePointsText(){

        tv_player1.setText("Player 1: "+ player1Points);
        tv_player2.setText("Player 2: "+player2Points);

    }

    private void resetBoard(){

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

        roundCount=0;
        player1Turn=true;

    }

    //if you want to reset the game
    private void resetGame(){
        player1Points=0;
        player2Points=0;
        updatePointsText();
        resetBoard();
    }


    //to check how many rounds,no of points and the player
    //when screen is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);

    }

    //get them to our app
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");


    }






}