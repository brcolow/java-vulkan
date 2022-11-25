package com.brcolow.game;

import com.brcolow.winapi.Windows_h;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryAddress;

import static com.brcolow.winapi.Windows_h.C_INT;
import static com.brcolow.winapi.Windows_h.C_LONG_LONG;
import static com.brcolow.winapi.Windows_h.C_POINTER;

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