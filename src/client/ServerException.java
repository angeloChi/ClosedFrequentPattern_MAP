package client;

/**
 * 
 * Gestione eccezione riguardante il server
 * @author Angelo,Simone,Antonio
 *
 */

public class ServerException extends RuntimeException implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
    }
  }