package com.company.entities;

import com.company.Kind;
import com.company.Value;

public class Card {
    private Value value;
    private Kind kind;

    public Card() {}

    public Card(Value value, Kind kind) {
        this.value = value;
        this.kind = kind;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}
