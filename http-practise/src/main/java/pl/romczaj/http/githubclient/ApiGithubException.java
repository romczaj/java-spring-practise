package pl.romczaj.http.githubclient;

public class ApiGithubException extends RuntimeException{

    public ApiGithubException(String message) {
        super(message);
    }
}
