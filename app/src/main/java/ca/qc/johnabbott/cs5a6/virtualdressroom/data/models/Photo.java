package ca.qc.johnabbott.cs5a6.virtualdressroom.data.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Identifiable;

public class Photo implements Identifiable<Long> {

    public enum OutfitType {
        UPPER_BODY(0, "Upper Body"),
        LOWER_BODY(1, "Lower Body");

        private final int code;
        private final String name;

        OutfitType(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() { return code; }
        public String getName() { return name; }

        public static OutfitType getOutfitTypeByCode(int code) {
            switch(code) {
                case 0:
                    return UPPER_BODY;
                case 1:
                    return LOWER_BODY;
                default:
                    return null;
            }
        }
    }

    private Long id;
    private byte[] bytes;
    private OutfitType outfitType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Identifiable<Long> setId(Long id) {
        this.id = id;
        return this;
    }

    public OutfitType getOutfitType() {
        return outfitType;
    }

    public Photo setOutfitType(OutfitType outfitType) {
        this.outfitType = outfitType;
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
