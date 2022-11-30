package com.brcolow.game;

import com.brcolow.winapi.MSG;
import com.brcolow.winapi.Windows_h;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.brcolow.winapi.Windows_h.C_INT;
import static com.brcolow.winapi.Windows_h.C_LONG_LONG;
import static com.brcolow.winapi.Windows_h.C_POINTER;

public class WindowProc {
    private static final AtomicBoolean exitRequested = new AtomicBoolean(false);
    public static final FunctionDescriptor WindowProc$FUNC = FunctionDescriptor.of(C_LONG_LONG,
            C_POINTER,
            C_INT,
            C_LONG_LONG,
            C_LONG_LONG
    );

    static long WindowProcFunc(MemoryAddress hWnd, int message, long wParam, long lParam) {
        // System.out.println("wParam: " + wParam);
        // System.out.println("lParam: " + lParam);
        System.out.println("message: " + message);
        if (message == Windows_h.WM_KEYDOWN() ||
                message == Windows_h.WM_SYSKEYDOWN() ||
                message == Windows_h.WM_KEYUP() ||
                message == Windows_h.WM_SYSKEYUP()) {
            if ((lParam & (1L << 31)) == 0) {
                // Key down
                System.out.println("virtual key code: " + wParam + " DOWN");
            } else {
                // Key up
                System.out.println("virtual key code: " + wParam + " UP");
            }
        } else if (message == Windows_h.WM_MOUSEMOVE() ||
                message == Windows_h.WM_LBUTTONDOWN() ||
                message == Windows_h.WM_LBUTTONUP() ||
                message == Windows_h.WM_LBUTTONDBLCLK() ||
                message == Windows_h.WM_RBUTTONDOWN() ||
                message == Windows_h.WM_RBUTTONUP() ||
                message == Windows_h.WM_RBUTTONDBLCLK() ||
                message == Windows_h.WM_MBUTTONDOWN() ||
                message == Windows_h.WM_MBUTTONUP() ||
                message == Windows_h.WM_MBUTTONDBLCLK() ||
                message == Windows_h.WM_XBUTTONDOWN() ||
                message == Windows_h.WM_XBUTTONUP() ||
                message == Windows_h.WM_XBUTTONDBLCLK() ||
                message == Windows_h.WM_MOUSEWHEEL() ||
                message == Windows_h.WM_MOUSEHWHEEL() ||
                message == Windows_h.WM_MOUSELEAVE()) {
            // These are from Windowsx.h - we could use jextract to generate but for now...
            int xCoord = WindowsUtils.GET_X_LPARAM(lParam);
            int yCoord = WindowsUtils.GET_Y_LPARAM(lParam);
            System.out.println("Mouse action at x = " + xCoord + ", y = " + yCoord);
        } else if (message == Windows_h.WM_SIZE()) {
            System.out.println("WM_SIZE fired");
        } else if (message == Windows_h.WM_QUIT()) {
            System.out.println("WM_QUIT fired");
        } else if (message == Windows_h.WM_CLOSE()) {
            exitRequested.set(true);
            System.out.println("WM_CLOSE fired");
        } else if (message == Windows_h.WM_DESTROY()) {
            System.out.println("WM_DESTROY fired");
            Windows_h.PostQuitMessage(0);
        }
        return Windows_h.DefWindowProcW(hWnd, message, wParam, lParam);
    }

    public static AtomicBoolean getExitRequested() {
        return exitRequested;
    }
}