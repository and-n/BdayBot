package and.bday.service.model;

public class CongratulationMessage {

    private final String messageFirstPart;
    private final String messageSecondPart;

    public CongratulationMessage(final String messageFirstPart, final String messageSecondPart) {
        this.messageFirstPart = messageFirstPart;
        this.messageSecondPart = messageSecondPart;
    }

    public String getMessageFirstPart() {
        return messageFirstPart;
    }

    public String getMessageSecondPart() {
        return messageSecondPart;
    }
}
