package TokenManagement;

import messaging.Event;
//Author Adin s164432
public interface ITokenService {

    String TOKEN_GENERATION_REQUESTED = "TokenGenerationRequested";
    String TOKEN_GENERATION_COMPLETED = "TokenGenerationCompleted";
    String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
    String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";


    void handleTokenGenerationRequested(Event e);
    void handleTokenValidationRequested(Event e);
}
