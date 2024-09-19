package com.eeb5266b.saltedfish.app.enums;

public enum HotKeyIdentifier {

    START(0),
    TEST(999);

    private final int key;

    HotKeyIdentifier(int i) {
        this.key = i;
    }

    public int getKey() {
        return key;
    }
}
