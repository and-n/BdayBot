package and.bday.service.impl;

import and.bday.service.SlackIntegrationService;
import and.bday.service.model.CongratulationMessage;
import and.bday.service.model.Human;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackIntegrationServiceImpl implements SlackIntegrationService {

    private static final Logger log = Logger.getLogger(SlackIntegrationServiceImpl.class);
    private static final SlackSession session = SlackSessionFactory.createWebSocketSlackSession("");

    @Override
    public void sendCongratulation(Human human, CongratulationMessage congratulationMessage) {
        try {
            if (checkConnection()) {
                final SlackChannel channel = session.findChannelByName("bdaytest");
                final SlackUser user = findUserForHuman(human);

                session.sendMessage(channel, "Happy birthday! <@" + user.getId() + ">   ");
            }

        } catch (final Exception e) {
            log.error("Fail send message ", e);
            sendError("fail to send message for " + human + "  and " + e.getMessage());
        }
    }

    private boolean checkConnection() {
        if (!session.isConnected()) {
            try {
                session.connect();
            } catch (IOException e) {
                log.error("Fail create connection!", e);
                return false;
            }
        }
        return true;
    }

    private SlackUser findUserForHuman(final Human human) {
        SlackUser slackUser = null;
        if (checkConnection()) {
            slackUser = session.getUsers().stream()
                    .filter(slackUser1 -> slackUser1 != null && slackUser1.getRealName() != null && slackUser1.getRealName().toLowerCase().equals(human.getName().toLowerCase() + " " + human.getSurname().toLowerCase()))
                    .findFirst().orElse(null);
        }
        if (slackUser == null) {
            final String username = human.getName() + "." + human.getSurname().toLowerCase();
            slackUser = session.findUserByUserName(human.getName() + "." + human.getSurname());
            if (slackUser == null) {
                slackUser = session.findUserByEmail(username + "@team.wrike.com");
            }
        }
        return slackUser;
    }

    public static void sendError(final String message) {
        SlackUser user = session.findUserById("U14E136GM");
        session.sendMessageToUser(user, new SlackPreparedMessage.Builder().withMessage(message).build());
        log.info("send error message '" + message + "'.");
    }
}