package com.example.mstdnApi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;


public class MSTDNRestfulCallback extends UrlRequest.Callback {
    private final int BUFFER = 102400;
    private Message message = Message.obtain();
    private Handler handler;

    public MSTDNRestfulCallback(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) throws Exception {
        Log.d("request", "redirect");
        request.followRedirect();
    }

    @Override
    public void onResponseStarted(UrlRequest request, UrlResponseInfo info) throws Exception {
        Log.d("request", "start");
        request.read(ByteBuffer.allocateDirect(BUFFER));
    }

    @Override
    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) throws Exception {
        Log.d("request", "complete");
        request.read(byteBuffer);
        Bundle bundle = new Bundle();
        boolean hasJson = false;
        int statusCode = info.getHttpStatusCode();
        Map<String, List<String>> headers = info.getAllHeaders();
        for (String key : headers.keySet()) {
            if (key.toLowerCase().equals("content-type")) {
                for (String s : headers.get(key)) {
                    if (s.toLowerCase().contains("application/json")) {
                        hasJson = true;
                        break;
                    }
                }
                break;
            }
        }
        int size = byteBuffer.position();
        byte[] bytes = new byte[size];
        byteBuffer.rewind();
        for (int i=0; i<size; i++) {
            bytes[i] = byteBuffer.get();
        }
        String body = new String(bytes);
        bundle.putInt("statusCode", statusCode);
        bundle.putBoolean("isJson", hasJson);
        bundle.putString("body", body);
        message.setData(bundle);
        this.handler.sendMessage(message);
        Log.d("handler", "message sent");
    }

    @Override
    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
        Log.d("request", "succeed");
    }

    @Override
    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
        Log.d("request", "fail");
    }
}
