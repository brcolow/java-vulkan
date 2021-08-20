package com.brcolow.game;

import com.brcolow.winapi.Windows_h;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;

import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_LONG_LONG;
import static jdk.incubator.foreign.CLinker.C_POINTER;

public class WindowProc {
    public static final FunctionDescriptor WindowProc$FUNC = FunctionDescriptor.of(C_LONG_LONG,
            C_POINTER,
            C_INT,
            C_LONG_LONG,
            C_LONG_LONG
    );
    static long WindowProcFunc(MemoryAddress hWnd, int msg, long wParam, long lParam) {
        // System.out.println("hWnd: " + hWnd);
        // System.out.println("msg: " + msg);
        // System.out.println("wParam: " + wParam);
        // System.out.println("lParam: " + lParam);
        return Windows_h.DefWindowProcW(hWnd, msg, wParam, lParam);
    }
}