package com.example.procalendar.Calendar_Note;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.procalendar.MyDataBase;
import com.example.procalendar.NoteActivity;
import com.example.procalendar.R;

import java.util.ArrayList;

public class MyNote extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LoginDatabase myLoginDatabase;

    public static MyNote newInstance(String param1, String param2)
    {
        MyNote fragment = new MyNote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_note, container, false);

        myLoginDatabase = new LoginDatabase(getContext());
        final EditText txtPassWord = view.findViewById(R.id.txtPassWord);
        final TextView txtMessage = view.findViewById(R.id.txtMessage);
        final Button btnCreate = view.findViewById(R.id.btnCreate);
        final Button btnChangePass = view.findViewById(R.id.btnChangePass);
        final TextView txtForgot = view.findViewById(R.id.txtForgot);
        if (myLoginDatabase.getNotesCount() > 0) btnCreate.setEnabled(false);
        else btnChangePass.setEnabled(false);
        final Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = txtPassWord.getText().toString();
                if (myLoginDatabase.getNotesCount() <= 0)
                {
                    txtMessage.setText("Chưa tạo mật khẩu!");
                }
                else {
                    if (pass.compareTo(myLoginDatabase.getData(0).getPassWord()) != 0) {
                        txtMessage.setText("Mật khẩu không chính xác!");
                    } else {
                        txtMessage.setText("");
                        Intent intent = new Intent(getContext(), NoteActivity.class);
                        startActivity(intent);
                        txtPassWord.setText("");
                    }
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog inputCreate = new Dialog(getContext());
                inputCreate.setContentView(R.layout.layout_login);
                inputCreate.setTitle("Tạo mật khẩu");

                final Spinner spnQuestion1 = inputCreate.findViewById(R.id.spnQuestion1);
                final Spinner spnQuestion2 = inputCreate.findViewById(R.id.spnQuestion2);
                final Spinner spnQuestion3 = inputCreate.findViewById(R.id.spnQuestion3);

                ArrayList<String> listForQuestion1 = new ArrayList<String>();
                listForQuestion1.add("Tên lúc còn nhỏ của bạn?");
                listForQuestion1.add("Thành phố bạn sinh ra?");
                listForQuestion1.add("Thú cưng của bạn loại gì?");
                listForQuestion1.add("Tên người yêu quý nhất?");
                listForQuestion1.add("Nơi nào xa nhất từng đi?");
                ArrayAdapter<String> adapterForQuestion1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion1);
                spnQuestion1.setAdapter(adapterForQuestion1);

                ArrayList<String> listForQuestion2 = new ArrayList<String>();
                listForQuestion2.add("Tên trường đại học?");
                listForQuestion2.add("Bạn có bao nhiêu anh chị?");
                listForQuestion2.add("Tên thú cưng của bạn?");
                listForQuestion2.add("Game yêu thích nhất?");
                listForQuestion2.add("Sở thích của bạn?");
                ArrayAdapter<String> adapterForQuestion2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion2);
                spnQuestion2.setAdapter(adapterForQuestion2);

                ArrayList<String> listForQuestion3 = new ArrayList<String>();
                listForQuestion3.add("Môn học yêu thích?");
                listForQuestion3.add("Từng có mấy người yêu cũ?");
                listForQuestion3.add("Thích ăn gì nhất?");
                listForQuestion3.add("Tên sách tâm đắc nhất?");
                listForQuestion3.add("Nghề từng mơ ước nhất?");
                ArrayAdapter<String> adapterForQuestion3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion3);
                spnQuestion3.setAdapter(adapterForQuestion3);

                final EditText txtCreatePass = inputCreate.findViewById(R.id.txtNewPass);
                final EditText txtRetype = inputCreate.findViewById(R.id.txtRetype);
                final EditText txtQuestion1 = inputCreate.findViewById(R.id.txtQuestion1);
                final EditText txtQuestion2 = inputCreate.findViewById(R.id.txtQuestion2);
                final EditText txtQuestion3 = inputCreate.findViewById(R.id.txtQuestion3);
                Button btnConfirm = inputCreate.findViewById(R.id.btnConfirm);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txtCreatePass.getText().toString().compareTo("") != 0)
                        {
                            if (txtRetype.getText().toString().compareTo(txtCreatePass.getText().toString()) == 0)
                            {
                                if (txtQuestion1.getText() != null && txtQuestion2.getText() != null && txtQuestion3.getText() != null)
                                {
                                    int myID = myLoginDatabase.getNotesCount();
                                    String myPassWord = txtCreatePass.getText().toString();
                                    String myQuestion1 = spnQuestion1.getSelectedItem().toString();
                                    String myAnswer1 = txtQuestion1.getText().toString();
                                    String myQuestion2 = spnQuestion2.getSelectedItem().toString();
                                    String myAnswer2 = txtQuestion2.getText().toString();
                                    String myQuestion3 = spnQuestion3.getSelectedItem().toString();
                                    String myAnswer3 = txtQuestion3.getText().toString();
                                    MyAccount myAccount = new MyAccount(myID, myPassWord, myQuestion1, myAnswer1, myQuestion2, myAnswer2, myQuestion3, myAnswer3);
                                    myLoginDatabase.addAccout(myAccount);
                                    inputCreate.dismiss();
                                }
                            }
                        }
                    }
                });
                inputCreate.show();
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog forgotPass = new Dialog(getContext());
                forgotPass.setContentView(R.layout.layout_forgot_password);
                forgotPass.setTitle("Quên mật khẩu");

                final Spinner fspnQuestion1 = forgotPass.findViewById(R.id.fspnQuestion1);
                final Spinner fspnQuestion2 = forgotPass.findViewById(R.id.fspnQuestion2);
                final Spinner fspnQuestion3 = forgotPass.findViewById(R.id.fspnQuestion3);

                ArrayList<String> listForQuestion1 = new ArrayList<String>();
                listForQuestion1.add("Tên lúc còn nhỏ của bạn?");
                listForQuestion1.add("Thành phố bạn sinh ra?");
                listForQuestion1.add("Thú cưng của bạn loại gì?");
                listForQuestion1.add("Tên người yêu quý nhất?");
                listForQuestion1.add("Nơi nào xa nhất từng đi?");
                ArrayAdapter<String> adapterForQuestion1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion1);
                fspnQuestion1.setAdapter(adapterForQuestion1);

                ArrayList<String> listForQuestion2 = new ArrayList<String>();
                listForQuestion2.add("Tên trường đại học?");
                listForQuestion2.add("Bạn có bao nhiêu anh chị?");
                listForQuestion2.add("Tên thú cưng của bạn?");
                listForQuestion2.add("Game yêu thích nhất?");
                listForQuestion2.add("Sở thích của bạn?");
                ArrayAdapter<String> adapterForQuestion2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion2);
                fspnQuestion2.setAdapter(adapterForQuestion2);

                ArrayList<String> listForQuestion3 = new ArrayList<String>();
                listForQuestion3.add("Môn học yêu thích?");
                listForQuestion3.add("Từng có mấy người yêu cũ?");
                listForQuestion3.add("Thích ăn gì nhất?");
                listForQuestion3.add("Tên sách tâm đắc nhất?");
                listForQuestion3.add("Nghề từng mơ ước nhất?");
                ArrayAdapter<String> adapterForQuestion3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listForQuestion3);
                fspnQuestion3.setAdapter(adapterForQuestion3);

                final Button btnGetPass = forgotPass.findViewById(R.id.btnGetPass);
                final EditText ftxtQuestion1 = forgotPass.findViewById(R.id.ftxtQuestion1);
                final EditText ftxtQuestion2 = forgotPass.findViewById(R.id.ftxtQuestion2);
                final EditText ftxtQuestion3 = forgotPass.findViewById(R.id.ftxtQuestion3);
                final TextView txtWrong = forgotPass.findViewById(R.id.txtWrong);

                btnGetPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question1 = fspnQuestion1.getSelectedItem().toString();
                        String answer1 = ftxtQuestion1.getText().toString();
                        String question2 = fspnQuestion2.getSelectedItem().toString();
                        String answer2 = ftxtQuestion2.getText().toString();
                        String question3 = fspnQuestion3.getSelectedItem().toString();
                        String answer3 = ftxtQuestion3.getText().toString();

                        if (myLoginDatabase.getData(0).getQuestion1().compareTo(question1) == 0
                        && myLoginDatabase.getData(0).getAnswer1().compareTo(answer1) == 0
                        && myLoginDatabase.getData(0).getQuestion2().compareTo(question2) == 0
                        && myLoginDatabase.getData(0).getAnswer2().compareTo(answer2) == 0
                        && myLoginDatabase.getData(0).getQuestion3().compareTo(question3) == 0
                        && myLoginDatabase.getData(0).getAnswer3().compareTo(answer3) == 0)
                        {
                            txtWrong.setText("Mật khẩu của bạn: " + myLoginDatabase.getData(0).getPassWord());
                        }
                        else txtWrong.setText("Thông tin chưa chính xác!");
                    }
                });

                forgotPass.show();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog changePass = new Dialog(getContext());
                changePass.setContentView(R.layout.layout_change_password);
                changePass.setTitle("Đổi mật khẩu");
                final EditText txtOldPass = changePass.findViewById(R.id.txtOldPass);
                final EditText txtNewPass = changePass.findViewById(R.id.txtNewPass);
                final EditText txtNewPassConfirm = changePass.findViewById(R.id.txtNewPassConfirm);
                final TextView txtWarn = changePass.findViewById(R.id.txtWarn);
                Button btnChange = changePass.findViewById(R.id.btnChange);
                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txtOldPass.getText().toString().compareTo("") != 0)
                        {
                            if (txtNewPass.getText().toString().compareTo("") != 0)
                            {
                                if (txtNewPassConfirm.getText().toString().compareTo(txtNewPass.getText().toString()) == 0)
                                {
                                    if (txtOldPass.getText().toString().compareTo(myLoginDatabase.getData(0).getPassWord()) != 0)
                                    {
                                        txtWarn.setText("Mật khẩu cũ chưa chính xác!");
                                    }
                                    else {
                                        txtWarn.setText("");
                                        myLoginDatabase.updatePassword(0, txtNewPass.getText().toString());
                                        changePass.dismiss();
                                    }
                                }
                            }
                        }
                    }
                });
                changePass.show();
            }
        });

        return view;
    }
}
