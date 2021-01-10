package com.example.choice;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VoteRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://ec2-15-164-169-103.ap-northeast-2.compute.amazonaws.com/vote.php";
    private Map<String, String> map;

    public VoteRequest(String user_email, int board_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("user_email", user_email);
        map.put("board_id", String.valueOf(board_id));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}