package Service;

import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Exception.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class KweetService {

    @EJB
    private IKweetDao kweetDao;

    @EJB
    private IHashtagDao hashtagDao;

    @EJB
    private IUserDao userDao;

    public KweetService() { }

    public boolean sendKweet(Kweet kweet) throws UserNotFoundException {
        if (kweet.getMessage() == null || kweet.getMessage().equals("") || kweet.getSender() == null) {
            return false;
        } else {
            List<String> hashtagNames = parseNames('#', kweet.getMessage());
            List<String> mentionNames = parseNames('@', kweet.getMessage());
            addHashtags(kweet, hashtagNames);
            addMentions(kweet, mentionNames);
            kweetDao.create(kweet);

            return true;
        }
    }

    private List<String> parseNames(char prefix, String message) {
        Pattern pattern = Pattern.compile("(" + prefix + "\\w+)\\b");
        Matcher matcher = pattern.matcher(message);

        // HashSet to remove duplicates
        Collection<String> names = new HashSet<String>();
        while (matcher.find()) {
            names.add(matcher.group(1));
        }

        return new ArrayList<String>(names);
    }

    private void addHashtags(Kweet kweet, List<String> names) {
        List<Hashtag> hashtags = new ArrayList<Hashtag>();
        for (String name : names) {
            Hashtag hashtag = hashtagDao.findByName(name);
            if (hashtag == null) {
                hashtag = new Hashtag();
                hashtag.setName(name);
            }
            hashtags.add(updateHashtag(kweet, hashtag));
        }
        kweet.setHashtags(hashtags);
    }

    private Hashtag updateHashtag(Kweet kweet, Hashtag hashtag) {
        hashtag.setLastUsed(kweet.getPublicationDate());
        hashtag.setTimesUsed(hashtag.getTimesUsed() + 1);

        return hashtag;
    }

    private void addMentions(Kweet kweet, List<String> names) throws UserNotFoundException {
        List<User> mentions = new ArrayList<User>();
        for (String name : names) {
            User user = userDao.findByUsername(name);
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                mentions.add(user);
            }
        }
        kweet.setMentions(mentions);
    }
}
