/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.supportv7.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.supportv7.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PopupMenuActivity extends AppCompatActivity {
    private TextView mLog;

    private SimpleDateFormat mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_menu_activity);

        mDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

        final ViewGroup container = (ViewGroup) findViewById(R.id.container);
        mLog = (TextView) container.findViewById(R.id.log);

        final SwitchCompat elevationToggle = (SwitchCompat) container.findViewById(
                R.id.elevation_toggle);
        final Button button = (Button) container.findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do we need to use a custom style that removes elevation?
                boolean useDefaultElevation = elevationToggle.isChecked();

                PopupMenu popupMenu = null;
                if (useDefaultElevation) {
                    popupMenu = new PopupMenu(container.getContext(), button);
                } else {
                    popupMenu = new PopupMenu(container.getContext(), button, Gravity.NO_GRAVITY,
                            0, R.style.CustomPopupNoElevation);
                }
                final MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

                // Register a listener to be notified when a menu item in our popup menu has
                // been clicked.
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        addToLog("Item '"+ item.getTitle() + "' clicked");
                        return true;
                    }
                });

                // Register a listener to be notified when our popup menu is dismissed.
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        addToLog("Popup menu dismissed");
                    }
                });

                // Show the popup menu
                popupMenu.show();
            }
        });
    }

    private void addToLog(String toLog) {
        String toPrepend = mDateFormat.format(new Date()) + " " + toLog + "\n";
        mLog.setText(toPrepend + mLog.getText());
    }
}
