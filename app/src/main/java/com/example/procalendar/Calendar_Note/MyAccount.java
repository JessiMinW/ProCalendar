package com.example.procalendar.Calendar_Note;

public class MyAccount
{
    private int ID;
    private String passWord, question1, question2, question3, answer1, answer2, answer3;
    public MyAccount(int ID, String passWord, String question1, String answer1, String question2, String answer2, String question3, String answer3)
    {
        this.ID = ID;
        this.passWord = passWord;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }
    public int getID() {return ID;}
    public String getPassWord() {return passWord;}
    public String getQuestion1() {return question1;}
    public String getQuestion2() {return question2;}
    public String getQuestion3() {return question3;}
    public String getAnswer1() {return answer1;}
    public String getAnswer2(){return answer2;}
    public String getAnswer3() {return answer3;}
}
