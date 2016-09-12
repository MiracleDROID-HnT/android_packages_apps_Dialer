/*
 * Copyright (C) 2014 Xiao-Long Chen <chillermillerlong@hotmail.com>
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

package com.android.dialer.lookup.whitepages;

import com.android.dialer.phonenumbercache.ContactInfo;
import com.android.dialer.lookup.ReverseLookup;

import android.content.Context;

import java.io.IOException;

public class WhitePagesReverseLookup extends ReverseLookup {
    private static final String TAG = WhitePagesReverseLookup.class.getSimpleName();

    public WhitePagesReverseLookup(Context context) {
    }

    @Override
    public ContactInfo lookupNumber(Context context,
            String normalizedNumber, String formattedNumber) throws IOException {
        return WhitePagesApi.reverseLookup(context, normalizedNumber, formattedNumber);
    }
}
