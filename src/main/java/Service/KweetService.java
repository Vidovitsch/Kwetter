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

    public boolean send(Kweet kweet) throws UserNotFoundException {
        if (kweet.getMessage() == null || kweet.getMessage().equals("") || kweet.getSender() == null) {
            return false;
        } else {
            addHashtags(kweet, parseNames('#', kweet.getMessage()));
            addMentions(kweet, parseNames('@', kweet.getMessage()));
            kweetDao.create(kweet);

            return true;
        }
    }

    public Kweet edit(Kweet kweet) throws UserNotFoundException {
        addHashtags(kweet, parseNames('#', kweet.getMessage()));
        addMentions(kweet, parseNames('@', kweet.getMessage()));

        return kweetDao.update(kweet);
    }

    public boolean delete(Kweet kweet) {
        return kweetDao.remove(kweet);
    }

    public void giveHeart(Kweet kweet, User user) {
        kweet.getHearts().add(user);
        kweetDao.update(kweet);
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
