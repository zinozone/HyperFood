package com.project.hyperfood.common.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UriAdapter extends TypeAdapter<Uri> {

    @Override
    public void write(JsonWriter out, Uri uri) throws IOException {
        if (uri != null) {
            out.value(uri.toString());
        }
    }

    @Override
    public Uri read(JsonReader in) throws IOException {
        String s = in.nextString();
        if (!TextUtils.isEmpty(s)) {
            return Uri.parse(s);
        } else {
            return null;
        }
    }
}
