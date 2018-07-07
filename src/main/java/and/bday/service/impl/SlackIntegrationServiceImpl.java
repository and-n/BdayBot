package and.bday.service.impl;

import and.bday.service.SlackIntegrationService;
import and.bday.service.model.CongratulationMessage;
import and.bday.service.model.Human;
import com.ullink.slack.simpleslackapi.*;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SlackIntegrationServiceImpl implements SlackIntegrationService {

    private static final Logger log = Logger.getLogger(SlackIntegrationServiceImpl.class);
    @Value("${slack_bot_id}")
    private String key;
    private SlackSession session;
    @Value("${channelName}")
    private String channelName;

    @PostConstruct
    private void init() {
        session = SlackSessionFactory.createWebSocketSlackSession(key);
    }

    @Override
    public void sendCongratulation(Human human, CongratulationMessage congratulationMessage) {
        try {
            if (checkConnection()) {
                final SlackChannel channel = session.findChannelByName(channelName);
                final SlackUser user = findUserForHuman(human);

                SlackMessageHandle<SlackMessageReply> messageHandle = session.sendMessage(channel, "Happy birthday! <@" + user.getId() + ">   ");
                session.addReactionToMessage(channel, messageHandle.getReply().getTimestamp(), "tada");
                session.addReactionToMessage(channel, messageHandle.getReply().getTimestamp(), "yayfox");
                session.addReactionToMessage(channel, messageHandle.getReply().getTimestamp(), "birthday");
            }
        } catch (final Exception e) {
            log.error("Fail send message ", e);
            sendError("fail to send message\n for " + human + "''' \nand '" + e.getMessage() + "'");
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

    @Override
    public void sendError(final String message) {
        try {
            SlackUser user = session.findUserById("U14E136GM");
            session.sendMessageToUser(user, new SlackPreparedMessage.Builder().withMessage(message).build());
            log.info("send error message '" + message + "'.");
        } catch (final Exception e) {
            log.error(e);
        }
    }
}
