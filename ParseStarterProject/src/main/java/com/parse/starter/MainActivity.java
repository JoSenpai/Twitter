/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import static com.parse.starter.R.id.login;


public class MainActivity extends AppCompatActivity {

  EditText username;
  EditText password;

  public void redirectUser()
  {
    if(ParseUser.getCurrentUser() != null)
    {
      Intent intent = new Intent(getApplicationContext(), Users.class);
      startActivity(intent);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void init()
  {
    username = (EditText)findViewById(login);
    password = (EditText)findViewById(R.id.password);
  }

  public void loginSignUp(View view)
  {
    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(e == null)
        {
          Log.i("Log In", "Success");
          redirectUser();
        }else
        {
          ParseUser parseUser = new ParseUser();
          parseUser.setUsername(username.getText().toString());
          parseUser.setPassword(password.getText().toString());
          parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
              if(e == null)
              {
                Log.i("Info", "Sign Up Success");
              }else
              {
                Log.i("Info", "Sign up Failed");
                Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
              }
            }
          });
        }
      }
    });

  }

}