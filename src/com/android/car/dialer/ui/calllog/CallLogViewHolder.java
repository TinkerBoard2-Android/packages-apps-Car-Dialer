/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.car.dialer.ui.calllog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.dialer.R;
import com.android.car.dialer.entity.PhoneCallLog;
import com.android.car.dialer.telecom.TelecomUtils;
import com.android.car.dialer.telecom.UiCallManager;
import com.android.car.dialer.ui.common.entity.UiCallLog;
import com.android.car.dialer.widget.CallTypeIconsView;

/**
 * {@link RecyclerView.ViewHolder} for call history list item, responsible for presenting and
 * resetting the UI on recycle.
 */
public class CallLogViewHolder extends RecyclerView.ViewHolder {
    private ImageView mAvatarView;
    private TextView mTitleView;
    private TextView mTextView;
    private CallTypeIconsView mCallTypeIconsView;

    public CallLogViewHolder(@NonNull View itemView) {
        super(itemView);
        mAvatarView = itemView.findViewById(R.id.icon);
        mTitleView = itemView.findViewById(R.id.title);
        mTextView = itemView.findViewById(R.id.text);
        mCallTypeIconsView = itemView.findViewById(R.id.call_type_icons);
    }

    public void onBind(UiCallLog uiCallLog) {
        TelecomUtils.setContactBitmapAsync(
                mAvatarView.getContext(),
                mAvatarView,
                uiCallLog.getAvatarUri(),
                uiCallLog.getTitle());
        mTitleView.setText(uiCallLog.getTitle());
        mTextView.setText(uiCallLog.getText());
        for (PhoneCallLog.Record record : uiCallLog.getCallRecords()) {
            mCallTypeIconsView.add(record.getCallType());
        }

        super.itemView.setOnClickListener(
                view -> UiCallManager.get().placeCall(uiCallLog.getNumber()));
    }

    public void onRecycle() {
        mCallTypeIconsView.clear();
    }
}