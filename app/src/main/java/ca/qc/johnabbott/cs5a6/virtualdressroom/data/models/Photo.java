package ca.qc.johnabbott.cs5a6.virtualdressroom.data.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Identifiable;

public class Photo implements Identifiable<Long> {

    private Long id;
    private byte[] bytes;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Identifiable<Long> setId(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Photo setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
}
