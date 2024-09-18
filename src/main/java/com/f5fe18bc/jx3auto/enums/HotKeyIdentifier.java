package com.f5fe18bc.jx3auto.enums;

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
