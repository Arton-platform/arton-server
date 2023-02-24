package com.arton.backend.infra.excel;

import java.awt.*;

public enum ColumnType {
    TEXT(Color.BLACK),
    NUMBER(Color.CYAN);

    public Color color;

    ColumnType(Color color) {
        this.color = color;
    }
}
