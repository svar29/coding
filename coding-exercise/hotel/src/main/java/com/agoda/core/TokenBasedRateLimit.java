package com.agoda.core;

import com.agoda.model.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenBasedRateLimit implements RateLimit{
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenBasedRateLimit.class);

    private Double maxMessagesAllowed;
    private Double perSeconds;
    private Double currentAllowedMessages;
    // epoch time
    private Long lastCheckedAtInSeconds = 0L;
    // epoch time
    private Long blockedTill = 0L;

    public TokenBasedRateLimit(Rate rate) {
        this.maxMessagesAllowed = rate.getMessages();
        this.perSeconds = rate.getSeconds();
        this.currentAllowedMessages = maxMessagesAllowed;
    }

    @Override
    public boolean processMessage(Long nowSeconds){
        Long time_passed = nowSeconds - lastCheckedAtInSeconds;
        LOGGER.debug("time_passed: " + time_passed);
        lastCheckedAtInSeconds = nowSeconds;
        // add more messaged to allowed messages based on time elapsed
        currentAllowedMessages += time_passed * (maxMessagesAllowed / perSeconds);
        // dont exceed maxMessagesAllowed
        if (currentAllowedMessages > maxMessagesAllowed) {
            currentAllowedMessages = maxMessagesAllowed;
        }
        LOGGER.info("currentAllowedMessages:" + currentAllowedMessages);
        // check if there is bandwidth to allow this message
        if (currentAllowedMessages < 1.0) {
            LOGGER.debug("message discarded at " + nowSeconds + "\n");
            return false;
        }
        else {
            LOGGER.debug("message forwarded at "+ nowSeconds + "\n");
            // remove one message from allowed messages
            currentAllowedMessages -= 1.0;
            return true;
        }
    }

    public Long getBlockedTill() {
        return blockedTill;
    }

    public void setBlockedTill(Long blockedTill) {
        this.blockedTill = blockedTill;
    }

}
