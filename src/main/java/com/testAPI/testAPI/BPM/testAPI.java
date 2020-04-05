package com.testAPI.testAPI.BPM;

import okhttp3.*;

import java.io.IOException;

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo(String name) {
        threadName = name;
    }

    public void run() {
        System.out.println("Running " + threadName);
        try {
            for (int i = 1; i < 251; i++) {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n  \"context\": {\r\n    \"__name\": \"java " + threadName + " " + i + "\"\r\n  }\r\n}");
                Request request = new Request.Builder()
                        .url("https://yelgesunw3swu.s-quickbpm.ru/pub/v1/app/workspace/bpmapitest/create")
                        .method("POST", body)
                        .addHeader("X-Token", "3152dc2b-390d-4907-a5c9-e5e9e52bf152")
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();

                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
