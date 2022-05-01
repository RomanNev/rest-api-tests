package models;

public class GenerateTokenResponse {

    /*
    {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImFsZXgiLCJwYXNzd29yZCI6ImFzZHNhZCNmcmV3X0RGUzIiLCJpYXQiOjE2NTE0MjEzMjV9.N41NlkOCAmcDnkYJKlpLvWUMOJ8xwKrx_p2D6bz-cSw",
    "expires": "2022-05-08T16:08:45.183Z",
    "status": "Success",
    "result": "User authorized successfully."
     }
         */

    String token;
    String expires;
    String status;
    String result;

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}
