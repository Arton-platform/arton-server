package com.arton.backend.test.skeypad.core;

import com.arton.backend.test.core.exception.DSHexException;
import com.arton.backend.test.core.exception.DSKeyPadException;
import com.arton.backend.test.core.util.DSHexUtil;
import com.arton.backend.test.core.util.DSRandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SpringBootTest
class SKeyPadCoreTest {

    @Test
    void generateSeedTest() {
        DSRandomUtil dsRandomUtil = new DSRandomUtil();
        byte[] r1 = dsRandomUtil.generateSeed(64);
        System.out.println("Random seed for Java ");
        System.out.print("[");
        for (byte b : r1) {
            System.out.print(b+", ");
        }
        System.out.print("]");
        System.out.println("");
        byte[] r2 = dsRandomUtil.generateSeed(64);
        System.out.print("[");
        for (byte b : r2) {
            System.out.print(b+", ");
        }
        System.out.print("]");

        System.out.println("");

        System.out.println("Random seed for Rust");
        System.out.print("[");
        for (byte b : r1) {
            System.out.print((b & 0xff)+", ");
        }
        System.out.print("]");
        System.out.println("");
        System.out.print("[");
        for (byte b : r2) {
            System.out.print((b & 0xff)+", ");
        }
        System.out.print("]");


        // 한번 돌리고 나서 이 값을 고정 시키고 테스트 해보면 될듯 랜덤시드는 변하면 안되니까.
    }

    @Test
    void keypadTest() throws DSKeyPadException, DSHexException {
        byte[] r1 = {20, 79, -111, 76, -91, -98, -48, -102, -88, 106, -98, -38, 120, -80, -84, -123, -24, 54, 23, 62, 60, -45, -48, 10, 107, 105, -32, 105, -32, -125, -104, 45, -86, -96, -5, 114, 25, 127, -53, -50, 117, -116, -39, -58, 122, 98, -48, -79, 69, -103, -122, -32, -32, -37, 61, 23, 13, -115, -41, 114, 90, -51, -123, -59};
        byte[] r2 = {-83, 7, -31, -57, 117, 5, -118, -4, -20, -37, -30, -98, -122, 17, -2, 78, -67, -103, 40, -115, -88, 15, 16, -102, 44, -56, 104, 126, -13, 100, -101, -34, 11, -123, 92, -119, -120, 56, 17, -63, 33, -104, 25, -50, 74, 68, -111, 112, 115, -115, -23, 36, 9, 60, 13, -116, 124, -120, 78, 53, 126, 78, 127, 3};

        SKeyPadCore sKeyPadCore = new SKeyPadCore(r1, r2);

        byte[][] sPad_dic = sKeyPadCore.getSPad_DIC();
        System.out.println("sPad_dic row= " + (!ObjectUtils.isEmpty(sPad_dic) ? sPad_dic.length : 0));
        System.out.println("sPad_dic col= " + (!ObjectUtils.isEmpty(sPad_dic) ? sPad_dic[0].length : 0));
        if (!ObjectUtils.isEmpty(sPad_dic)){
            System.out.println("spad dic encoded value");
            for (byte[] bytes1 : sPad_dic) {
                // to hex
                String hexEncoded = DSHexUtil.byteArrToHexString(bytes1, false);
                System.out.println("hexEncoded = " + hexEncoded);
            }
        }

        // decode
        // ["ed434f8a", "8daf2e02", "498cae2f", "d02ab9a7", "98d5c8d6"]
        String[] toDecode = {"ed434f8a", "8daf2e02", "498cae2f", "d02ab9a7", "98d5c8d6", "4c00d8e7", "34184e02", "804046e3"};
        List<String> upper = Arrays.stream(toDecode).map(value -> value.toUpperCase(Locale.ROOT)).collect(Collectors.toList());
        for (String s : upper) {
            // to byte
            byte[] toDecodeBytes = DSHexUtil.hexStrToByteArr(s);
            byte[] decode = sKeyPadCore.decode(toDecodeBytes);
            for (byte b : decode) {
                System.out.println("char: " + (char)b + " number: " + (b & 0xff));
            }
        }

    }
}