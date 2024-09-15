package com.f5fe18bc.jx3.auto.enums;

import lombok.Getter;

@Getter
public enum HotKeyIdentifier {

    START(0),
    TEST(999);

    private final int key;

    HotKeyIdentifier(int i) {
        this.key = i;
    }
}
